import ratpack.exec.Promise
import ratpack.jackson.Jackson
import ratpack.kotlin.handling.KContext
import kotlin.reflect.KClass

fun <T : Any> KContext.parseJson(type: KClass<T>): Promise<T>{
    return parse(Jackson.fromJson(type.java))
}