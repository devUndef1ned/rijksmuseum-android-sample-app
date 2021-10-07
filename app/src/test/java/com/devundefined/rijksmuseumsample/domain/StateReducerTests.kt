package com.devundefined.rijksmuseumsample.domain

import com.devundefined.rijksmuseumsample.domain.model.CollectionState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException

internal class StateReducerTests {
    private val sut = StateReducer()

    @Test
    fun `Given Initial loading current state And failure result When reduce state Should return Initial Failure`() {
        val newState = sut.reduceState(
            CollectionState.InitialLoading,
            Result.failure(IllegalStateException("123"))
        )

        Assertions.assertInstanceOf(CollectionState.InitialFailure::class.java, newState)
    }
}