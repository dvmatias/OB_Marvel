package com.cmdv.domain.usecases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.utils.Event
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
class AddFavoriteCharacterUseCaseTest : BaseUnitTest<AddFavoriteCharacterUseCase>() {
    @Mock
    private lateinit var favoriteCharacterRepository: FavoriteCharacterRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        uut = AddFavoriteCharacterUseCase(favoriteCharacterRepository)
    }

    @Test
    fun execute_use_case_response_success() {
        val params = AddFavoriteCharacterUseCase.Params(1011334, 0)
        // Set repository response with expected data
        whenever(
            favoriteCharacterRepository.add(any(), any())
        ).thenReturn(
            ResponseWrapper.success(Event(content = 1))
        )

        runBlocking {
            val result = uut?.executeUseCase(params)

            // Check repository interactions
            verify(favoriteCharacterRepository, times(1)).add(any(), any())
            verifyNoMoreInteractions(favoriteCharacterRepository)
            // Check use case result
            MatcherAssert.assertThat(result, notNullValue())
            MatcherAssert.assertThat(result!!.status, equalTo(ResponseWrapper.Status.READY))
            MatcherAssert.assertThat(result.failureType, nullValue())
            MatcherAssert.assertThat(result.data, notNullValue())
            with(result.data!!) {
                MatcherAssert.assertThat(this, instanceOf(Event::class.java))
                MatcherAssert.assertThat(peekContent(), equalTo(1))
            }
        }
    }
}