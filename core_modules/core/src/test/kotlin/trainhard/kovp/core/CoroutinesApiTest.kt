package trainhard.kovp.core

import kotlinx.coroutines.delay
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import trainhard.kovp.core.coroutines.coroutine
import kotlin.reflect.KClass

class CoroutinesApiTest {
    @Test
    fun `should fetch string`() = runTest {
        val data = coroutine {
            delay(100)
            TEST_STRING
        }
            .await()
            .getOrThrow()

        assertEquals(TEST_STRING, data)
    }

    @Test
    fun `should throw error while fetching`() = runTest {
        assertThrows<TestException> {
            coroutine {
                delay(100)
                throwException()
                TEST_STRING
            }
                .await()
                .getOrThrow()
        }
    }

    @Test
    fun `should handle exception`() = runTest {
        var exception: KClass<out Throwable>? = null

        coroutine {
            delay(100)
            throwException()
        }
            .onFailure { exception = it::class }
            .await()

        assertEquals(TestException::class, exception)
    }

    @Test
    fun `should skip onFailureBlock`() = runTest {
        var exception: Throwable? = null

        coroutine {
            delay(100)
        }
            .onFailure { exception = it }
            .await()

        assertEquals(null, exception)
    }

    @Test
    fun `should throw with default onError block`() = runTest {
        assertThrows<TestException> {
            coroutine {
                delay(100)
                throwException()
            }
                .await()
                .getOrThrow()
        }
    }

    @Test
    fun `should fail`() = runTest {
            coroutine {
                coroutine { delay(100) }.await()
                coroutine { throwException() }.await()
            }
                .await()
    }

    @Test
    fun `should return string and handle exception`() = runTest {
        val result = coroutine {
            supervisorScope {
                val string = coroutine {
                    delay(100)
                    TEST_STRING
                }
                    .await()
                    .getOrThrow()

                assertThrows<TestException> {
                    coroutine { throwException() }.await().getOrThrow()
                }

                string
            }
        }
            .await()
            .getOrThrow()

        assertEquals(TEST_STRING, result)
    }

    @Test
    fun `run onSuccess block`() = runTest {
        var result: String? = null

        coroutine {
            delay(100)
            TEST_STRING
        }
            .onSuccess { result = it }
            .await()

        assertEquals(TEST_STRING, result)
    }

    @Test
    fun `should run onFailure and skip onSuccess`() = runTest {
        var result: String? = null
        var throwable: KClass<out Throwable>? = null

        coroutine {
            delay(100)
            throwException()
            TEST_STRING
        }
            .onSuccess { result = it }
            .onFailure { throwable = it::class }
            .await()

        assertEquals(null, result)
        assertEquals(TestException::class, throwable)
    }

    private fun throwException() {
        throw TestException()
    }

    companion object {
        private const val TEST_STRING = "TEST_STRING"
    }
}
