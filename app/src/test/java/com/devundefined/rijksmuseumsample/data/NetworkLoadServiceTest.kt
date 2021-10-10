package com.devundefined.rijksmuseumsample.data

import com.devundefined.rijksmuseumsample.Mother
import com.devundefined.rijksmuseumsample.data.dto.CollectionDto
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
internal class NetworkLoadServiceTest {

    private lateinit var sut: NetworkLoadService

    @Mock
    lateinit var api: RijksmuseumApi

    @BeforeEach
    fun before() {
        sut = NetworkLoadService(api)
    }

    @Test
    fun `Given page with not correct page When load page Should return failed result`() {
        runBlockingTest {
            val result = sut.loadPage(-2)

            Assertions.assertTrue(result.isFailure)
        }
    }

    @Test
    fun `Given network error When load page Should return failed result`() {
        runBlockingTest {
            whenever(
                api.getCollection(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            ) doThrow IllegalStateException("error!!!")

            val result = sut.loadPage(2)

            Assertions.assertTrue(result.isFailure)
            Assertions.assertInstanceOf(
                IllegalStateException::class.java,
                result.exceptionOrNull()!!
            )
        }
    }

    @Test
    fun `Given network result When load page Should return success result with according data`() {
        runBlockingTest {
            whenever(api.getCollection(any(), any(), any(), any(), any())) doReturn CollectionDto(
                count = 2, artObjects = listOf(
                    Mother.randomArtObjectDto(id = "id1"),
                    Mother.randomArtObjectDto(id = "id2")
                )
            )

            val result = sut.loadPage(2)

            Assertions.assertTrue(result.isSuccess)
            result.getOrThrow().pageData.run {
                Assertions.assertTrue(size == 2)
                Assertions.assertTrue(any { it.id == "id1" })
                Assertions.assertTrue(any { it.id == "id2" })
            }
        }
    }
}