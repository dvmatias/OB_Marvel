package com.cmdv.domain.usecases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.domain.models.CharacterModel
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
class GetCharacterByIdUseCaseTest : BaseUnitTest<GetCharacterByIdUserCase>() {
    @Mock
    private lateinit var characterDetailsRepository: CharacterDetailsRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        uut = GetCharacterByIdUserCase(characterDetailsRepository)
    }

    @Test
    fun execute_use_case_response_success() {
        val params = GetCharacterByIdUserCase.Params(1009144)
        // Expected use case response data
        val expectedData = fromJson("character.json", CharacterModel::class.java)
        // Set repository response with expected data
        whenever(
            characterDetailsRepository.getCharacterById(any())
        ).thenReturn(
            ResponseWrapper.success(expectedData)
        )

        runBlocking {
            val result = uut?.executeUseCase(params)

            // Check repository interactions
            verify(characterDetailsRepository, times(1)).getCharacterById(any())
            verifyNoMoreInteractions(characterDetailsRepository)
            // Check use case result
            assertThat(result, notNullValue())
            assertThat(result!!.status, equalTo(ResponseWrapper.Status.READY))
            assertThat(result.failureType, nullValue())
            assertThat(result.data, notNullValue())
            with(result.data!!) {
                assertThat(this, instanceOf(CharacterModel::class.java))
                assertThat(id, equalTo(1009144))
                assertThat(name, equalTo("A.I.M."))
                assertThat(description, equalTo("AIM is a terrorist organization bent on destroying the world."))
                assertThat(thumbnail, equalTo("http://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec.jpg"))
                assertThat(isFavourite, equalTo(false))
                assertThat(comicsCount, equalTo(52))
                assertThat(seriesCount, equalTo(34))
                assertThat(storiesCount, equalTo(54))
            }
        }
    }
}