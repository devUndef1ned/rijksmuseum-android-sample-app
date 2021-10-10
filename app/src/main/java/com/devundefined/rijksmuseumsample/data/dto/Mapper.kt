package com.devundefined.rijksmuseumsample.data.dto

import com.devundefined.rijksmuseumsample.domain.model.ArtItem
import com.devundefined.rijksmuseumsample.domain.model.ArtItemDetails
import com.devundefined.rijksmuseumsample.domain.model.ImageSpec
import com.devundefined.rijksmuseumsample.domain.model.PageData

val collectionDtoToPageData: (CollectionDto, Int) -> PageData =
    { dto, itemsPerPage ->
        PageData(
            dto.artObjects.map(artItemDtoToModel).toSet(),
            dto.count,
            itemsPerPage
        )
    }

val detailsDtoToArtItemDetails: (ArtDetailsDto) -> ArtItemDetails = { dto ->
    with(dto) {
        ArtItemDetails(
            id = id,
            objectNumber = objectNumber,
            title = title,
            image = webImage.let(imageDtoToModel),
            description = description,
            principalMaker = principalMaker
        )
    }
}

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
