package com.devundefined.rijksmuseumsample

import com.devundefined.rijksmuseumsample.data.dto.ArtObjectDto
import com.devundefined.rijksmuseumsample.data.dto.ImageDto
import net.bytebuddy.utility.RandomString
import kotlin.random.Random

object Mother {
    fun randomArtObjectDto(
        id: String = RandomString.make(),
        objectNumber: String = RandomString.make(),
        title: String = RandomString.make(),
        hasImage: Boolean = Random.nextBoolean(),
        principalOrFirstMaker: String = RandomString.make(),
        headerImage: ImageDto = randomImageDto(),
        webImage: ImageDto = randomImageDto(),
    ): ArtObjectDto = ArtObjectDto(
        id,
        objectNumber,
        title,
        hasImage,
        principalOrFirstMaker,
        headerImage,
        webImage
    )

    fun randomImageDto(
        width: Int = Random.nextInt(),
        height: Int = Random.nextInt(),
        url: String = RandomString.make()
    ): ImageDto = ImageDto(width, height, url)
}