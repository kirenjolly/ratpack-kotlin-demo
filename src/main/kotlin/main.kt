import ratpack.kotlin.handling.ratpack

fun main(args: Array<String>){
    app()
}

fun app(bindings: List<Any> = listOf()) = ratpack {
    bindings{
        for(b in bindings){
            bindInstance(b)
        }
        module(ApplicationModule(serverConfig))
    }
    handlers {
        prefix("main",RouterChain::class.java)
    }
}