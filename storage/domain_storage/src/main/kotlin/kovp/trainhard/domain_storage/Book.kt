package kovp.trainhard.domain_storage

interface Book {
    fun <T: Any> read(key: String): T?
    fun <T: Any> write(key: String, data: T)
}
