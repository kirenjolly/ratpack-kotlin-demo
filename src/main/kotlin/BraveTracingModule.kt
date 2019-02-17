import brave.sampler.Sampler
import com.google.inject.AbstractModule
import com.google.inject.Provides
import ratpack.server.ServerConfig
import zipkin2.reporter.AsyncReporter
import zipkin2.reporter.okhttp3.OkHttpSender
import javax.inject.Singleton

class BraveTracingModule(val serverConfig: ServerConfig): AbstractModule(){
    override fun configure() {
        install(zipkinModule())
    }

    @Provides
    @Singleton
    fun zipkinModule(): BraveModule{
        val tracingModule = BraveModule()
        tracingModule.configure {
            it.spanReporterV2(AsyncReporter.create(OkHttpSender.create("http://localhost:9411/api/v2/spans")))
            it.sampler(Sampler.ALWAYS_SAMPLE)
            it.serviceName("test-tracing")
        }
        return tracingModule
    }
}