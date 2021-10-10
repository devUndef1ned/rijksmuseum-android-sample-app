package com.devundefined.rijksmuseumsample.ui.collection

import com.devundefined.rijksmuseumsample.domain.CollectionDataProvider
import com.devundefined.rijksmuseumsample.domain.CollectionDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
internal class CollectionViewModelTest {

    @Mock
    lateinit var dataProvider: CollectionDataProvider

    @Mock
    lateinit var collectionState: CollectionDataState
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    lateinit var sut: CollectionViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        sut = CollectionViewModel(dataProvider, collectionState)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `When retry first page Should invoke initial loading`() = runBlockingTest {
        reset(dataProvider)
        sut.retry(0)

        verify(dataProvider).initialLoading()
    }

    @Test
    fun `When retry not first page Should invoke load more`() = runBlockingTest {
        reset(dataProvider)
        sut.retry(2)

        verify(dataProvider).loadMore()
    }
}