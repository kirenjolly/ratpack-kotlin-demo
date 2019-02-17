import com.google.inject.AbstractModule
import com.google.inject.Scopes
import ratpack.server.ServerConfig

class ApplicationModule constructor(val serverConfig: ServerConfig): AbstractModule(){
    override fun configure() {
        install(BraveTracingModule(serverConfig))
        bind(InitialHandler::class.java).`in`(Scopes.SINGLETON)
    }
}