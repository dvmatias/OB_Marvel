package com.cmdv.domain.usecases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.repositories.CharactersRepository
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
class GetCharactersUseCaseTest : BaseUnitTest<GetCharactersUseCase>() {
    @Mock
    private lateinit var charactersRepository: CharactersRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        uut = GetCharactersUseCase(charactersRepository)
    }

    @Test
    fun execute_use_case_response_success() {
        val params = GetCharactersUseCase.Params(true, 32, 0)
        // Expected use case response data
        val expectedData = fromJson("characters.json", Array<CharacterModel>::class.java).toList()
        // Set repository response with expected data
        whenever(
            charactersRepository.getCharacters(any(), any(), any())
        ).thenReturn(
            ResponseWrapper.success(expectedData)
        )

        runBlocking {
            val result = uut?.executeUseCase(params)

            // Check repository interactions
            verify(charactersRepository, times(1)).getCharacters(any(), any(), any())
            verifyNoMoreInteractions(charactersRepository)
            // Check use case result
            assertThat(result, notNullValue())
            assertThat(result!!.status, equalTo(ResponseWrapper.Status.READY))
            assertThat(result.failureType, nullValue())
            assertThat(result.data, notNullValue())
            assertThat(result.data!!.size, equalTo(32))
            // Check item 0
            with(result.data!![0]) {
                assertThat(this, instanceOf(CharacterModel::class.java))
                assertThat(id, equalTo(1011334))
                assertThat(name, equalTo("3-D Man"))
                assertThat(description, equalTo(""))
                assertThat(thumbnail, equalTo("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"))
                assertThat(isFavourite, equalTo(false))
                assertThat(comicsCount, equalTo(12))
                assertThat(seriesCount, equalTo(3))
                assertThat(storiesCount, equalTo(21))
            }
        }
    }
}