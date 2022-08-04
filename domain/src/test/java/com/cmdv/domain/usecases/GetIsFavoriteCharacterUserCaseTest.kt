package com.cmdv.domain.usecases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.utils.FailureType
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

private const val CHARACTER_ID = 1017100

@RunWith(AndroidJUnit4::class)
class GetIsFavoriteCharacterUserCaseTest : BaseUnitTest<GetIsFavoriteCharacterUserCase>() {
    @Mock
    private lateinit var favoriteCharacterRepository: FavoriteCharacterRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        uut = GetIsFavoriteCharacterUserCase(favoriteCharacterRepository)
    }

    @Test
    fun execute_use_case_response_true() {
        // Set repository response with expected data
        whenever(
            favoriteCharacterRepository.isFavorite(CHARACTER_ID)
        ).thenReturn(
            ResponseWrapper.success(true)
        )

        runBlocking {
            val result = uut?.executeUseCase(GetIsFavoriteCharacterUserCase.Params(CHARACTER_ID))

            // Check repository interactions
            verify(favoriteCharacterRepository, times(1)).isFavorite(CHARACTER_ID)
            verifyNoMoreInteractions(favoriteCharacterRepository)
            // Check use case result
            assertThat(result, notNullValue())
            assertThat(result!!.status, equalTo(ResponseWrapper.Status.READY))
            assertThat(result.failureType, nullValue())
            assertThat(result.data, notNullValue())
            assertThat(result.data, equalTo(true))
        }
    }

    @Test
    fun execute_use_case_response_failure() {
        // Set repository response with expected data
        whenever(
            favoriteCharacterRepository.isFavorite(CHARACTER_ID)
        ).thenReturn(
            ResponseWrapper.error(null, FailureType.LocalError("Error message"))
        )

        runBlocking {
            val result = uut?.executeUseCase(GetIsFavoriteCharacterUserCase.Params(CHARACTER_ID))

            // Check repository interactions
            verify(favoriteCharacterRepository, times(1)).isFavorite(CHARACTER_ID)
            verifyNoMoreInteractions(favoriteCharacterRepository)
            // Check use case result
            assertThat(result, notNullValue())
            assertThat(result!!.status, equalTo(ResponseWrapper.Status.ERROR))
            assertThat(result.failureType, notNullValue())
            assertThat(result.failureType, instanceOf(FailureType.LocalError::class.java))
            assertThat(result.failureType!!.message, equalTo("Error message"))
            assertThat(result.data, nullValue())
        }
    }
}