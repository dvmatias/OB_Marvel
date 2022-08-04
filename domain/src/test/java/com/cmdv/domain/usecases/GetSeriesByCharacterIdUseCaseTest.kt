package com.cmdv.domain.usecases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.domain.models.SerieModel
import com.cmdv.domain.repositories.CharacterDetailsRepository
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.ResponseWrapper
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class GetSeriesByCharacterIdUseCaseTest : BaseUnitTest<GetSeriesByCharacterIdUserCase>() {
    @Mock
    private lateinit var characterDetailsRepository: CharacterDetailsRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        uut = GetSeriesByCharacterIdUserCase(characterDetailsRepository)
    }

    @Test
    fun execute_use_case_response_success() {
        val params = GetSeriesByCharacterIdUserCase.Params(1010903, 32)
        // Expected use case response data
        val expectedData = fromJson("series.json", Array<SerieModel>::class.java).toList()
        // Set repository response with expected data
        whenever(
            characterDetailsRepository.getSeries(any(), any())
        ).thenReturn(
            ResponseWrapper.success(expectedData)
        )

        runBlocking {
            val result = uut?.executeUseCase(params)

            // Check repository interactions
            verify(characterDetailsRepository, times(1)).getSeries(any(), any())
            verifyNoMoreInteractions(characterDetailsRepository)
            // Check use case result
            assertThat(result, notNullValue())
            assertThat(result!!.status, equalTo(ResponseWrapper.Status.READY))
            assertThat(result.failureType, nullValue())
            assertThat(result.data, notNullValue())
            assertThat(result.data!!.size, equalTo(3))
            // Check item 0
            with(result.data!![0]) {
                assertThat(this, instanceOf(SerieModel::class.java))
                assertThat(id, equalTo(2258))
                assertThat(title, equalTo("Uncanny X-Men (1963 - 2011)"))
                assertThat(
                    description,
                    equalTo("The flagship X-Men comic for over 40 years, Uncanny X-Men delivers action, suspense, and a hint of science fiction month in and month out. Follow the adventures of Professor Charles Xavier's team of mutants as they attempt to protect a world that hates and fears them.")
                )
                assertThat(thumbnail, equalTo("http://i.annihil.us/u/prod/marvel/i/mg/9/00/512527be6fba3.jpg"))
            }
        }
    }

    @Test
    fun execute_use_case_response_failure() {
        val params = GetSeriesByCharacterIdUserCase.Params(1010903, 32)
        // Set repository response with expected data
        whenever(
            characterDetailsRepository.getSeries(any(), any())
        ).thenReturn(
            ResponseWrapper.error(null, FailureType.ServerError("Error message"))
        )

        runBlocking {
            val result = uut?.executeUseCase(params)

            // Check repository interactions
            verify(characterDetailsRepository, times(1)).getSeries(any(), any())
            verifyNoMoreInteractions(characterDetailsRepository)
            // Check use case result
            assertThat(result, notNullValue())
            assertThat(result!!.status, equalTo(ResponseWrapper.Status.ERROR))
            assertThat(result.failureType, notNullValue())
            assertThat(result.failureType, instanceOf(FailureType.ServerError::class.java))
            assertThat(result.failureType!!.message, equalTo("Error message"))
            assertThat(result.data, nullValue())
        }
    }
}