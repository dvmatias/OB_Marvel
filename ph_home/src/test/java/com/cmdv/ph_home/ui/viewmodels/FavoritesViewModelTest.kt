package com.cmdv.ph_home.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.core.utils.TestCoroutineRule
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.usecases.*
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.ResponseWrapper
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.*
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
class FavoritesViewModelTest : BaseUnitTest<FavoritesViewModel>() {
    @Mock
    private lateinit var favoriteCharactersRepository: FavoriteCharacterRepository

    private lateinit var getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase

    private lateinit var removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase

    private lateinit var removeAllFavoriteCharacterUseCase: RemoveAllFavoriteCharacterUseCase

    @Mock
    private lateinit var viewModelStateObserver: Observer<ResponseWrapper.Status>

    @Captor
    private lateinit var viewModelStateCaptor: ArgumentCaptor<ResponseWrapper.Status>

    @Mock
    private lateinit var favoriteCharactersObserver: Observer<List<CharacterModel>>

    @Captor
    private lateinit var favoriteCharactersCaptor: ArgumentCaptor<List<CharacterModel>>

    @Mock
    private lateinit var removeAllObserver: Observer<Event<Int>>

    @Captor
    private lateinit var removeAllCaptor: ArgumentCaptor<Event<Int>>

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

        getFavoriteCharactersUseCase = GetFavoriteCharactersUseCase(favoriteCharactersRepository)
        removeFavoriteCharacterUseCase = RemoveFavoriteCharacterUseCase(favoriteCharactersRepository)
        removeAllFavoriteCharacterUseCase = RemoveAllFavoriteCharacterUseCase(favoriteCharactersRepository)

        uut = FavoritesViewModel(
            getFavoriteCharactersUseCase,
            removeFavoriteCharacterUseCase,
            removeAllFavoriteCharacterUseCase
        )
        uut?.viewModelState?.observeForever(viewModelStateObserver)
        uut?.favoriteCharacters?.observeForever(favoriteCharactersObserver)
        uut?.removeAll?.observeForever(removeAllObserver)
    }

    @After
    fun tearDown() {
        testCoroutineRule.after()
        testCoroutineRule.testDispatcher.cancel()
    }

    @Test
    fun get_favorites() = runBlocking {
        launch {
            // Set repository response with expected data
            val characters = fromJson("favorites.json", Array<CharacterModel>::class.java).toList()
            whenever(favoriteCharactersRepository.getAll()).thenReturn(ResponseWrapper.success(characters))

            uut?.getFavorites()
        }.join()

        verify(viewModelStateObserver, times(3)).onChanged(viewModelStateCaptor.capture())
        verify(favoriteCharactersObserver, times(1)).onChanged(favoriteCharactersCaptor.capture())

        // Verify repositories interactions
        verify(favoriteCharactersRepository, times(2)).getAll()
        // Check viewModelState
        assertThat(viewModelStateCaptor.allValues, hasSize(3))
        assertThat(viewModelStateCaptor.firstValue, equalTo(ResponseWrapper.Status.LOADING))
        assertThat(viewModelStateCaptor.secondValue, equalTo(ResponseWrapper.Status.LOADING))
        assertThat(viewModelStateCaptor.lastValue, equalTo(ResponseWrapper.Status.READY))
        // Check favoriteCharacters
        assertThat(favoriteCharactersCaptor.allValues, hasSize(1))
        assertThat(favoriteCharactersCaptor.allValues[0], hasSize(5))
        for (character in favoriteCharactersCaptor.allValues[0]) {
            assertThat(character.isFavorite, equalTo(true))
        }
    }

    @Test
    fun remove_all_favorites_success() = runBlocking {
        launch {
            // Set repository response with expected data
            whenever(favoriteCharactersRepository.removeAll()).thenReturn(ResponseWrapper.success(Event(1)))

            uut?.removeAllFavorites()
        }.join()

        verify(viewModelStateObserver, times(3)).onChanged(viewModelStateCaptor.capture())
        verify(removeAllObserver, times(1)).onChanged(removeAllCaptor.capture())

        // Verify repositories interactions
        verify(favoriteCharactersRepository, times(1)).removeAll()
        // Check viewModelState
        assertThat(viewModelStateCaptor.allValues, hasSize(3))
        assertThat(viewModelStateCaptor.firstValue, equalTo(ResponseWrapper.Status.LOADING))
        assertThat(viewModelStateCaptor.lastValue, equalTo(ResponseWrapper.Status.READY))
        // Check favoriteCharacters
        assertThat(removeAllCaptor.allValues, hasSize(1))
        assertThat(removeAllCaptor.value.peekContent(), equalTo(1))
    }
}