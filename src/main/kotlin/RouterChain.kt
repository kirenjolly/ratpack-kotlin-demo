import brave.http.HttpTracing
import org.slf4j.LoggerFactory
import ratpack.exec.Execution
import ratpack.kotlin.handling.KChainAction
import javax.inject.Inject

open class RouterChain  @Inject constructor(val httpTracing: HttpTracing): KChainAction(){
    override fun execute() {
        all(InitialHandler::class.java)
        path(""){
            byMethod {
                post {
                    byContent {
                        json {
                            val log = LoggerFactory.getLogger(InitialHandler::class.java)
                            val initialSpan = httpTracing.tracing().tracer().currentSpan()
                            log.info("initialSpanInChain: {}",initialSpan)
                            parseJson(BraveRequest::class).then { req ->
                                val idFromRequestBody = req.traceid
                                val spanContext = httpTracing.tracing().currentTraceContext().get() != null
                                log.info("idFromRequestBody: {}",idFromRequestBody)
                                log.info("is Span Context present in tracer: {}",spanContext)
                                render("Traced the request")
                            }
                        }
                    }
                }
            }
        }
    }
}