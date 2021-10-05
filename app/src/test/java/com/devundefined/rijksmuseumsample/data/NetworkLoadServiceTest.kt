package com.devundefined.rijksmuseumsample.data

import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

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
}