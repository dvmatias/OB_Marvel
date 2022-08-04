package com.cmdv.ph_home.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.core.utils.TestCoroutineRule
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.usecases.*
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.ResponseWrapper
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoTestRule
import org.robolectric.annotation.Config

@Config(sdk = [Config.OLDEST_SDK])
@RunWith(AndroidJUnit4::class)
class CharactersViewModelTest : BaseUnitTest<CharactersViewModel>() {
    @Mock
    private lateinit var charactersRepository: CharactersRepository

    @Mock
    private lateinit var favoriteCharactersRepository: FavoriteCharacterRepository

    private lateinit var getTotalCharactersUseCase: GetTotalCharactersUseCase

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var removeStoredCharactersUseCase: RemoveStoredCharactersUseCase

    private lateinit var addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase

    private lateinit var removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase

    @Mock
    private lateinit var errorMessageObserver: Observer<String>

    @Mock
    private lateinit var viewModelStateObserver: Observer<ResponseWrapper.Status>

    @Mock
    private lateinit var charactersObserver: Observer<List<CharacterModel>>

    @Captor
    private lateinit var errorMessageCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var viewModelStateCaptor: ArgumentCaptor<ResponseWrapper.Status>

    @Captor
    private lateinit var charactersCaptor: ArgumentCaptor<List<CharacterModel>>

    @get:Rule
    val instantExecutionRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val mockitoRule: MockitoTestRule = MockitoJUnit.testRule(this)

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        testCoroutineRule.before()

        getTotalCharactersUseCase = GetTotalCharactersUseCase(charactersRepository)
        getCharactersUseCase = GetCharactersUseCase(charactersRepository)
        removeStoredCharactersUseCase = RemoveStoredCharactersUseCase(charactersRepository)
        addFavoriteCharacterUseCase = AddFavoriteCharacterUseCase(favoriteCharactersRepository)
        removeFavoriteCharacterUseCase = RemoveFavoriteCharacterUseCase(favoriteCharactersRepository)

        uut = CharactersViewModel(
            getTotalCharactersUseCase,
            getCharactersUseCase,
            removeStoredCharactersUseCase,
            addFavoriteCharacterUseCase,
            removeFavoriteCharacterUseCase
        )
    }

    @After
    fun tearDown() {
        errorMessageObserver.onChanged(null)
        viewModelStateObserver.onChanged(null)
        charactersObserver.onChanged(null)

        testCoroutineRule.after()
        testCoroutineRule.testDispatcher.cancel()
    }

    @Test
    fun get_total_characters_success() = runBlocking {
        // Set repository response with expected data
        whenever(
            charactersRepository.getTotalCharactersCount()
        ).thenReturn(
            ResponseWrapper.success(1562)
        )
        // Set repository response with expected data
        val characters = fromJson("characters.json", Array<CharacterModel>::class.java).toList()
        whenever(
            charactersRepository.getCharacters(true, 32, 0)
        ).thenReturn(
            ResponseWrapper.success(characters)
        )

        uut?.viewModelState?.observeForever(viewModelStateObserver)
        uut?.characters?.observeForever(charactersObserver)

        uut?.getTotalCharacters()

        delay(500)

        verify(viewModelStateObserver, times(6)).onChanged(viewModelStateCaptor.capture())
        verify(charactersObserver, times(1)).onChanged(charactersCaptor.capture())

        // Verify repositories interactions
        verify(charactersRepository, times(1)).getTotalCharactersCount()
        verify(charactersRepository, times(1)).getCharacters(any(), any(), any())
        verifyNoMoreInteractions(charactersRepository)
        // Check totalCharacterCount
        assertThat(uut?.totalCharactersCount, equalTo(1562))
        // Check errorMessage
        assertThat(uut?.errorMessage?.value, equalTo(""))
        // Check viewModelState
        assertThat(viewModelStateCaptor.allValues, hasSize(6))
        assertThat(viewModelStateCaptor.firstValue, equalTo(ResponseWrapper.Status.LOADING))
        assertThat(viewModelStateCaptor.secondValue, equalTo(ResponseWrapper.Status.LOADING))
        assertThat(viewModelStateCaptor.lastValue, equalTo(ResponseWrapper.Status.READY))
        // Check characters
        assertThat(charactersCaptor.allValues, hasSize(1))
        assertThat(charactersCaptor.allValues[0], hasSize(32))
    }

    @Test
    fun get_total_characters_error() = runBlocking {
        // Set repository response with expected data
        whenever(
            charactersRepository.getTotalCharactersCount()
        ).thenReturn(
            ResponseWrapper.error(0, FailureType.ServerError("Error message"))
        )

        uut?.errorMessage?.observeForever(errorMessageObserver)
        uut?.viewModelState?.observeForever(viewModelStateObserver)

        uut?.getTotalCharacters()

        delay(1000)

        verify(errorMessageObserver, times(2)).onChanged(errorMessageCaptor.capture())
        verify(viewModelStateObserver, times(4)).onChanged(viewModelStateCaptor.capture())

        // Verify repositories interactions
        verify(charactersRepository, times(1)).getTotalCharactersCount()
        verifyNoMoreInteractions(charactersRepository)
        // Check totalCharacterCount
        assertThat(uut?.totalCharactersCount, equalTo(0))
        // Check errorMessage
        assertThat(errorMessageCaptor.allValues, hasSize(2))
        assertThat(errorMessageCaptor.firstValue, equalTo(""))
        assertThat(errorMessageCaptor.lastValue, equalTo("Error message"))
        assertThat(uut?.errorMessage?.value, equalTo("Error message"))
        // Check viewModelState
        assertThat(viewModelStateCaptor.allValues, hasSize(4))
        assertThat(viewModelStateCaptor.firstValue, equalTo(ResponseWrapper.Status.LOADING))
        assertThat(viewModelStateCaptor.secondValue, equalTo(ResponseWrapper.Status.LOADING))
        assertThat(viewModelStateCaptor.thirdValue, equalTo(ResponseWrapper.Status.LOADING))
        assertThat(viewModelStateCaptor.lastValue, equalTo(ResponseWrapper.Status.ERROR))
    }

    @Test
    fun get_characters_fetch_true_not_all_characters_loaded_success() = runBlocking {
        // Set repository response with expected data
        val characters = fromJson("characters.json", Array<CharacterModel>::class.java).toList()
        whenever(
            charactersRepository.getCharacters(true, 32, 0)
        ).thenReturn(
            ResponseWrapper.success(characters)
        )

        uut?.viewModelState?.observeForever(viewModelStateObserver)
        uut?.characters?.observeForever(charactersObserver)

        uut?.getCharacters(true, 32, 0)

        delay(1500)

        verify(viewModelStateObserver, times(3)).onChanged(viewModelStateCaptor.capture())
        verify(charactersObserver, times(1)).onChanged(charactersCaptor.capture())

        // Verify repositories interactions
        verify(charactersRepository, times(1)).getCharacters(true, 32, 0)
        verifyNoMoreInteractions(charactersRepository)
        // Check viewModelState
        assertThat(viewModelStateCaptor.allValues, hasSize(3))
        assertThat(viewModelStateCaptor.firstValue, equalTo(ResponseWrapper.Status.LOADING))
        assertThat(viewModelStateCaptor.lastValue, equalTo(ResponseWrapper.Status.READY))
        // Check characters
        assertThat(charactersCaptor.allValues, hasSize(1))
        assertThat(charactersCaptor.firstValue, hasSize(32))
        assertThat(charactersCaptor.allValues[0], hasSize(32))
        // Check errorMessage
        assertThat(uut?.errorMessage?.value, equalTo(""))
    }

    @Test
    fun get_characters_fetch_true_not_all_characters_loaded_error() = runBlocking {
        // Set repository response with expected data
        whenever(
            charactersRepository.getCharacters(true, 32, 0)
        ).thenReturn(
            ResponseWrapper.error(null, FailureType.ServerError("Error message"))
        )

        uut?.viewModelState?.observeForever(viewModelStateObserver)
        uut?.characters?.observeForever(charactersObserver)

        uut?.getCharacters(true, 32, 0)

        delay(2000)

        verify(viewModelStateObserver, times(3)).onChanged(viewModelStateCaptor.capture())

        // Verify repositories interactions
        verify(charactersRepository, times(1)).getCharacters(true, 32, 0)
        verifyNoMoreInteractions(charactersRepository)
        // Check viewModelState
        assertThat(viewModelStateCaptor.allValues, hasSize(3))
        assertThat(viewModelStateCaptor.firstValue, equalTo(ResponseWrapper.Status.LOADING))
        assertThat(viewModelStateCaptor.secondValue, equalTo(ResponseWrapper.Status.LOADING))
        assertThat(viewModelStateCaptor.lastValue, equalTo(ResponseWrapper.Status.ERROR))
        // Check characters
        assertThat(charactersCaptor.allValues, hasSize(0))
        // Check errorMessage
        assertThat(uut?.errorMessage?.value, equalTo("Error message"))
    }

    @Test
    fun remove_stored_characters_success() = runBlocking {
        // Set repository response with expected data
        whenever(
            charactersRepository.removeStoredCharacters()
        ).thenReturn(
            ResponseWrapper.success(1)
        )

        uut?.removeStoredCharacters()

        // Verify repositories interactions
        verify(charactersRepository, times(1)).removeStoredCharacters()
        verifyNoMoreInteractions(charactersRepository)
    }

    @Test
    fun remove_stored_characters_failure() = runBlocking {
        // Set repository response with expected data
        whenever(
            charactersRepository.removeStoredCharacters()
        ).thenReturn(
            ResponseWrapper.error(null, FailureType.LocalError("Error message"))
        )

        uut?.removeStoredCharacters()

        // Verify repositories interactions
        verify(charactersRepository, times(1)).removeStoredCharacters()
        verifyNoMoreInteractions(charactersRepository)
    }

    @Test
    fun add_favorite_success() = runBlocking {
        // TODO
    }

    @Test
    fun add_favorite_failure() = runBlocking {
        // TODO
    }

    @Test
    fun remove_favorite() = runBlocking {
        // TODO
    }
}