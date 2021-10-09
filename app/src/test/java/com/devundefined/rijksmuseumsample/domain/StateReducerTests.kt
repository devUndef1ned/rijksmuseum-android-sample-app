package com.devundefined.rijksmuseumsample.domain

import com.devundefined.rijksmuseumsample.domain.model.CollectionState
import com.devundefined.rijksmuseumsample.domain.model.PageData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import kotlin.IllegalStateException

internal class StateReducerTests {
    private val sut = StateReducer()

    @Test
    fun `Given Initial loading current state And failure result When reduce state Should return Initial Failure`() {
        baseTest<CollectionState.InitialFailure>(
            CollectionState.InitialLoading,
            Result.failure(IllegalStateException("123"))
        )
    }

    @Test
    fun `Given Initial loading current state And empty network result When reduce state Should return Empty result`() {
        baseTest<CollectionState.EmptyResult>(
            CollectionState.InitialLoading,
            Result.success(
                PageData(emptyList(), 0)
            )
        )
    }

    @Test
    fun `Given Initial loading current state And some network result When reduce state Should return Collection Data`() {
        baseTest<CollectionState.CollectionData>(
            CollectionState.InitialLoading,
            Result.success(
                PageData(listOf(mock(), mock()), 0)
            )
        )
    }

    @Test
    fun `Given Initial failure current state And some network result When reduce state Should return Collection Data`() {
        baseTest<CollectionState.CollectionData>(
            CollectionState.InitialFailure(IllegalStateException("error")),
            Result.success(
                PageData(listOf(mock(), mock()), 0)
            )
        )
    }

    @Test
    fun `Given CollectionData current state And failed network result When reduce state Should return data with failure`() {
        baseTest<CollectionState.DataWithFailure>(
            CollectionState.CollectionData(mapOf(), true),
            Result.failure(IllegalStateException("error"))
        )
    }

    @Test
    fun `Given CollectionData current state And some network result When reduce state Should return Collection data`() {
        baseTest<CollectionState.CollectionData>(
            CollectionState.CollectionData(mapOf(), true),
            Result.success(PageData(listOf(mock(), mock()), 1))
        )
    }

    private inline fun <reified T : CollectionState> baseTest(
        currentState: CollectionState,
        networkResult: Result<PageData>
    ) {
        val newState = sut.reduceState(currentState, networkResult)

        Assertions.assertInstanceOf(T::class.java, newState)
    }
}