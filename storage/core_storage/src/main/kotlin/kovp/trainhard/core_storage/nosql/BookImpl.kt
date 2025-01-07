package kovp.trainhard.core_storage.nosql

import io.paperdb.Paper
import kovp.trainhard.domain_storage.Book

internal class BookImpl(
    bookName: String,
) : Book {
    private val bookImpl = Paper.book(bookName)

    override fun <T: Any> read(key: String): T? {
        return bookImpl.read<T>(key)
    }

    override fun <T : Any> write(key: String, data: T) {
        bookImpl.write(key, data)
    }
}
