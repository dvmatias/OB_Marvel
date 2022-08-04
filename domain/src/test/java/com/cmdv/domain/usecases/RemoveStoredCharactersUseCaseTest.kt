package com.cmdv.domain.usecases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.ResponseWrapper
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class RemoveStoredCharactersUseCaseTest : BaseUnitTest<RemoveStoredCharactersUseCase>() {
    @Mock
    private lateinit var characterRepository: CharactersRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        uut = RemoveStoredCharactersUseCase(characterRepository)
    }

    @Test
    fun execute_use_case_response_success() {
        // Set repository response with expected data
        whenever(
            characterRepository.removeStoredCharacters()
        ).thenReturn(
            ResponseWrapper.success(1)
        )

        runBlocking {
            val result = uut?.executeUseCase(RemoveStoredCharactersUseCase.Params())

            // Check repository interactions
            verify(characterRepository, times(1)).removeStoredCharacters()
            verifyNoMoreInteractions(characterRepository)
            // Check use case result
            MatcherAssert.assertThat(result, notNullValue())
            MatcherAssert.assertThat(result!!.status, equalTo(ResponseWrapper.Status.READY))
            MatcherAssert.assertThat(result.failureType, nullValue())
            MatcherAssert.assertThat(result.data, notNullValue())
            MatcherAssert.assertThat(result.data, equalTo(1))
        }
    }

    @Test
    fun execute_use_case_response_failure() {
        // Set repository response with expected data
        whenever(
            characterRepository.removeStoredCharacters()
        ).thenReturn(
            ResponseWrapper.error(null, FailureType.LocalError("Error message"))
        )

        runBlocking {
            val result = uut?.executeUseCase(RemoveStoredCharactersUseCase.Params())

            // Check repository interactions
            verify(characterRepository, times(1)).removeStoredCharacters()
            verifyNoMoreInteractions(characterRepository)
            // Check use case result
            MatcherAssert.assertThat(result, notNullValue())
            MatcherAssert.assertThat(result!!.status, equalTo(ResponseWrapper.Status.ERROR))
            MatcherAssert.assertThat(result.failureType, notNullValue())
            MatcherAssert.assertThat(result.failureType, instanceOf(FailureType.LocalError::class.java))
            MatcherAssert.assertThat(result.failureType!!.message, equalTo("Error message"))
            MatcherAssert.assertThat(result.data, nullValue())
        }
    }
}