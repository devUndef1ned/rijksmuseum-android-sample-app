package com.devundefined.rijksmuseumsample.data

import android.util.Log
import com.devundefined.rijksmuseumsample.data.dto.Mapper
import com.devundefined.rijksmuseumsample.domain.model.PageData
import javax.inject.Inject

class NetworkLoadService @Inject constructor(private val api: RijksmuseumApi) {

    suspend fun loadPage(page: Int): Result<PageData> {
        return runCatching {
            require(page >= 0)

            Log.d("NetworkLoadService", "Request page #$page")

            api.getCollection(
                key = API_KEY,
                pageNumber = page,
                itemsPerPage = ITEMS_COUNT_PER_PAGE,
                sort = SORT_BY,
                material = MATERIAL
            )
        }.map { Mapper.collectionDtoToPageData(it, ITEMS_COUNT_PER_PAGE) }
            .onFailure { Log.e("NetworkLoadService", "Failure occurs when loadPage\n$it") }
    }

    private companion object {
        private const val ITEMS_COUNT_PER_PAGE = 30
        private const val API_KEY = "0fiuZFh4"
        private const val SORT_BY = "artist"
        private const val MATERIAL = "canvas"
    }
}