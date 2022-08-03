package com.cmdv.domain.usecases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.domain.models.ComicModel
import com.cmdv.domain.repositories.CharacterDetailsRepository
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
class GetComicsByCharacterIdUseCaseTest : BaseUnitTest<GetComicsByCharacterIdUserCase>() {
    @Mock
    private lateinit var characterDetailsRepository: CharacterDetailsRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        uut = GetComicsByCharacterIdUserCase(characterDetailsRepository)
    }

    @Test
    fun execute_use_case_response_success() {
        val params = GetComicsByCharacterIdUserCase.Params(1010903, 32)
        // Expected use case response data
        val expectedData = fromJson("comics.json", Array<ComicModel>::class.java).toList()
        // Set repository response with expected data
        whenever(
            characterDetailsRepository.getComics(any(), any())
        ).thenReturn(
            ResponseWrapper.success(expectedData)
        )

        runBlocking {
            val result = uut?.executeUseCase(params)

            // Check repository interactions
            verify(characterDetailsRepository, times(1)).getComics(any(), any())
            verifyNoMoreInteractions(characterDetailsRepository)
            // Check use case result
            assertThat(result, notNullValue())
            assertThat(result!!.status, equalTo(ResponseWrapper.Status.READY))
            assertThat(result.failureType, nullValue())
            assertThat(result.data, notNullValue())
            assertThat(result.data!!.size, equalTo(8))
            // Check item 0
            with(result.data!![0]) {
                assertThat(this, instanceOf(ComicModel::class.java))
                assertThat(id, equalTo(2539))
                assertThat(title, equalTo("X-Men: The Complete Age of Apocalypse Epic Book 2 (Trade Paperback)"))
                assertThat(
                    description,
                    equalTo("See your favorite mutants through a dark glass as the epic that literally rebuilt the X-Men in eight miniseries and more continues!")
                )
                assertThat(thumbnail, equalTo("http://i.annihil.us/u/prod/marvel/i/mg/c/b0/4bc61dec7755f.jpg"))
            }
        }
    }
}