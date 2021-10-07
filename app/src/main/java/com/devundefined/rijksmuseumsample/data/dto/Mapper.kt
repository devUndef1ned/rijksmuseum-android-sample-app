package com.devundefined.rijksmuseumsample.data.dto

import com.devundefined.rijksmuseumsample.domain.model.ArtItem
import com.devundefined.rijksmuseumsample.domain.model.ImageSpec
import com.devundefined.rijksmuseumsample.domain.model.PageData

object Mapper {
    val collectionDtoToPageData: (CollectionDto) -> PageData =
        { dto -> PageData(dto.artObjects.map(artItemDtoToModel), dto.count) }

    private val artItemDtoToModel: ArtObjectDto.() -> ArtItem = {
        ArtItem(
            id = id,
            objectNumber = objectNumber,
            hasImage = hasImage,
            title = title,
            principalOrFirstMaker = principalOrFirstMaker,
            headerImage = headerImage.let(imageDtoToModel),
            image = webImage.let(imageDtoToModel),
        )
    }

    private val imageDtoToModel: ImageDto.() -> ImageSpec = { ImageSpec(width, height, url) }
}