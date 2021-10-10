package com.devundefined.rijksmuseumsample.domain

import com.devundefined.rijksmuseumsample.data.NetworkLoadService
import com.devundefined.rijksmuseumsample.domain.model.CollectionState
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyZeroInteractions

@ExtendWith(MockitoExtension::class)
internal class CollectionDataProviderTests {

    @Mock
    lateinit var loadService: NetworkLoadService

    @Mock
    lateinit var stateReducer: StateReducer
    private lateinit var mutableState: CollectionDataMutableState
    private lateinit var sut: CollectionDataProvider

    @BeforeEach
    fun before() {
        mutableState = CollectionDataMutableState()
        sut = CollectionDataProvider(mutableState, loadService, stateReducer)
    }

    @Test
    fun `When initial loading Should make request for page 0`() = runBlockingTest {
        sut.initialLoading()

        verify(loadService).loadPage(0)
    }

    @Test
    fun `Given initial loading state When load more Should not make any request`() =
        runBlockingTest {
            sut.loadMore()

            verifyZeroInteractions(loadService)
        }

    @Test
    fun `Given data loading state When load more Should not make any request`() = runBlockingTest {
        mutableState.state.value = CollectionState.DataState.DataLoadingMore(emptyMap(), 0, 0)

        sut.loadMore()

        verifyZeroInteractions(loadService)
    }

    @Test
    fun `Given data with failure state When load more Should make request for next page`() =
        runBlockingTest {
            mutableState.state.value = CollectionState.DataState.DataWithFailure(
                emptyMap(),
                2,
                5,
                IllegalStateException("error")
            )

            sut.loadMore()

            verify(loadService).loadPage(3)
        }
}