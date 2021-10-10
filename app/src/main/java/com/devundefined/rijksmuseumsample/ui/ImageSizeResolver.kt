package com.devundefined.rijksmuseumsample.ui

import androidx.annotation.Px
import coil.size.PixelSize
import coil.size.SizeResolver
import com.devundefined.rijksmuseumsample.domain.model.ImageSpec
import coil.size.Size as CoilSize

class ImageSizeResolver(@Px private val parentWidth: Float, private val imageSpec: ImageSpec) : SizeResolver {
    override suspend fun size(): CoilSize {
        val ratio = parentWidth / imageSpec.width
        return PixelSize(parentWidth.toInt(), (imageSpec.height * ratio).toInt())
    }
}