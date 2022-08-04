package com.cmdv.domain.usecases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.utils.ResponseWrapper
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class GetFavoriteCharactersUseCaseTest : BaseUnitTest<GetFavoriteCharactersUseCase>() {
    @Mock
    private lateinit var favoriteCharacterRepository: FavoriteCharacterRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        uut = GetFavoriteCharactersUseCase(favoriteCharacterRepository)
    }

    @Test
    fun execute_use_case_response_success() {
        // Set repository response with expected data
        whenever(
            favoriteCharacterRepository.getAll()
        ).thenReturn(
            ResponseWrapper.success(listOf())
        )

        runBlocking {
            val result = uut?.executeUseCase(GetFavoriteCharactersUseCase.Params())

            // Check repository interactions
            verify(favoriteCharacterRepository, times(1)).getAll()
            verifyNoMoreInteractions(favoriteCharacterRepository)
            // Check use case result
            assertThat(result, notNullValue())
            assertThat(result!!.status, equalTo(ResponseWrapper.Status.READY))
            assertThat(result.failureType, nullValue())
            assertThat(result.data, notNullValue())
            assertThat(result.data, equalTo(listOf()))
        }
    }
}