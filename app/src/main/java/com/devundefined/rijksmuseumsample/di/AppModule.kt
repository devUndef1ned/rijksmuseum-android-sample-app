package com.devundefined.rijksmuseumsample.di

import com.devundefined.rijksmuseumsample.data.RijksmuseumApi
import com.devundefined.rijksmuseumsample.domain.CollectionDataMutableState
import com.devundefined.rijksmuseumsample.domain.CollectionDataState
import com.devundefined.rijksmuseumsample.domain.StateReducer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): RijksmuseumApi {
        return Retrofit.Builder().baseUrl(RijksmuseumApi.BASE_URL).addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            )
        ).build().create(RijksmuseumApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMutableState(): CollectionDataMutableState {
        return CollectionDataMutableState()
    }

    @Provides
    @Singleton
    fun provideCollectionState(mutableState: CollectionDataMutableState): CollectionDataState =
        mutableState

    @Provides
    @Singleton
    fun provideStateReducer(): StateReducer = StateReducer()
}