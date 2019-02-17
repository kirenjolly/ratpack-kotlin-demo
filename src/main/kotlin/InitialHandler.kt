import brave.http.HttpTracing
import ratpack.exec.Execution
import ratpack.handling.Context
import javax.inject.Inject
import ratpack.handling.Handler
import ratpack.zipkin.internal.RatpackCurrentTraceContext.TracingPropagationExecInitializer
import java.util.function.Supplier
import org.slf4j.LoggerFactory

open class InitialHandler @Inject constructor(val httpTracing: HttpTracing): Handler{
    override fun handle(ctx: Context) {
        val log = LoggerFactory.getLogger(InitialHandler::class.java)
        val initialSpan = httpTracing.tracing().tracer().currentSpan()
        val registry = Supplier{Execution.current()}
        log.info("InitialSpan: {}",initialSpan)
        val tracingPropagationExecInitializer = TracingPropagationExecInitializer()
        Execution.fork().start {
            tracingPropagationExecInitializer.init(Execution.current())
            val contextHttpTracing = registry.get().maybeGet(HttpTracing::class.java).isPresent
            log.info("HttpTracing class in child registry: {}",contextHttpTracing)
        }
        val contextHttpTracing = registry.get().maybeGet(HttpTracing::class.java).isPresent
        log.info("HttpTracing class in parent registry: {}",contextHttpTracing)
        log.info("CurrentExecution: {}",Execution.current().toString())
        ctx.response.send("Service value: traced")
    }
}