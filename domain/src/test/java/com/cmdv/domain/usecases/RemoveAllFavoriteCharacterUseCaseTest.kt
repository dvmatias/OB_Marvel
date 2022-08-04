package com.cmdv.domain.usecases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.utils.Event
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

@RunWith(AndroidJUnit4::class)
class RemoveAllFavoriteCharacterUseCaseTest : BaseUnitTest<RemoveAllFavoriteCharacterUseCase>() {
    @Mock
    private lateinit var favoriteCharacterRepository: FavoriteCharacterRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        uut = RemoveAllFavoriteCharacterUseCase(favoriteCharacterRepository)
    }

    @Test
    fun execute_use_case_response_success() {
        // Set repository response with expected data
        whenever(
            favoriteCharacterRepository.removeAll()
        ).thenReturn(
            ResponseWrapper.success(Event(1))
        )

        runBlocking {
            val result = uut?.executeUseCase(RemoveAllFavoriteCharacterUseCase.Params())

            // Check repository interactions
            verify(favoriteCharacterRepository, times(1)).removeAll()
            verifyNoMoreInteractions(favoriteCharacterRepository)
            // Check use case result
            assertThat(result, notNullValue())
            assertThat(result!!.status, equalTo(ResponseWrapper.Status.READY))
            assertThat(result.failureType, nullValue())
            assertThat(result.data, notNullValue())
            assertThat(result.data!!.peekContent(), equalTo(1))
        }
    }

    @Test
    fun execute_use_case_response_failure() {
        // Set repository response with expected data
        whenever(
            favoriteCharacterRepository.removeAll()
        ).thenReturn(
            ResponseWrapper.error(null, FailureType.LocalError("Error message"))
        )

        runBlocking {
            val result = uut?.executeUseCase(RemoveAllFavoriteCharacterUseCase.Params())

            // Check repository interactions
            verify(favoriteCharacterRepository, times(1)).removeAll()
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