package com.devundefined.rijksmuseumsample.data

import com.devundefined.rijksmuseumsample.data.dto.CollectionDto

class NetworkLoadService(private val api: RijksmuseumApi) {

    suspend fun loadPage(page: Int): Result<CollectionDto> {
        return runCatching {
            require(page > 0)

            api.getCollection(
                key = API_KEY,
                pageNumber = page,
                itemsPerPage = ITEMS_COUNT_PER_PAGE,
                sort = SORT_BY,
                material = MATERIAL
            )
        }
    }

    private companion object {
        private const val ITEMS_COUNT_PER_PAGE = 100
        private const val API_KEY = "0fiuZFh4"
        private const val SORT_BY = "artist"
        private const val MATERIAL = "canvas"
    }
}