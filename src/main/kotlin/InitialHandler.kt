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
        log.info("InitialSpan: {}",initialSpan)
        ctx.next()
    }
}