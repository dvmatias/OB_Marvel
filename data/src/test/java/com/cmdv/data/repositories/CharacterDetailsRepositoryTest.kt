package com.cmdv.data.repositories

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.data.entities.GetCharactersResponseEntity
import com.cmdv.data.entities.GetComicsResponseEntity
import com.cmdv.data.entities.GetSeriesResponseEntity
import com.cmdv.data.errorhandling.CharacterDetailsApiErrorHandler
import com.cmdv.data.networking.ApiHandler
import com.cmdv.data.networking.NetworkHandler
import com.cmdv.data.sources.apiservices.CharacterDetailsApi
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.models.ComicModel
import com.cmdv.domain.models.SerieModel
import com.cmdv.domain.repositories.CharacterDetailsRepository
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.ResponseWrapper
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response

private const val CHARACTER_ID = 1017100
private const val OFFSET = 0

@RunWith(AndroidJUnit4::class)
class CharacterDetailsRepositoryTest : BaseUnitTest<CharacterDetailsRepository>() {
    @Mock
    private lateinit var characterDetailsApi: CharacterDetailsApi

    @Mock
    private lateinit var networkHandler: NetworkHandler

    @Mock
    private lateinit var errorHandler: CharacterDetailsApiErrorHandler

    @Mock
    private lateinit var apiHandler: ApiHandler

    private lateinit var getCharactersByIdResponseEntity: GetCharactersResponseEntity

    private lateinit var getComicsResponseEntity: GetComicsResponseEntity

    private lateinit var getSeriesResponseEntity: GetSeriesResponseEntity

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        initEntities()
        initApiHandler()

        uut = CharacterDetailsRepositoryImpl(characterDetailsApi, apiHandler, errorHandler)
    }

    @Test
    fun get_character_by_id_success() {
        // Prepare service call
        setupSuccessGetCharacterByIdServiceCall()

        val response = uut?.getCharacterById(CHARACTER_ID)

        // Verify service call made
        verify(characterDetailsApi, times(1)).getCharacterById(CHARACTER_ID)
        // Check correct status response
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        // Check correct data response
        assertThat(response?.data, notNullValue())
        assertThat(response?.data, instanceOf(CharacterModel::class.java))
        // Check no failures in response
        assertThat(response?.failureType, equalTo(null))
    }

    @Test
    fun get_character_by_id_failure() {
        // Prepare service call
        setupFailureGetCharacterBiIdServiceCall()

        val response = uut?.getCharacterById(CHARACTER_ID)

        // Verify service call made
        verify(characterDetailsApi, times(1)).getCharacterById(CHARACTER_ID)
        // Check correct status response
        assertThat(response?.status, equalTo(ResponseWrapper.Status.ERROR))
        // Check correct data response
        assertThat(response?.data, nullValue())
        // Check failure type in response
        assertThat(response?.failureType, instanceOf(FailureType.ConnectionError::class.java))
    }

    @Test
    fun get_comics_success() {
        // Prepare service call
        setupSuccessGetComicsByCharacterIdServiceCall()

        val response = uut?.getComics(CHARACTER_ID, OFFSET)

        // Verify service call made
        verify(characterDetailsApi, times(1)).getComicsByCharacterId(CHARACTER_ID, OFFSET)
        // Check correct status response
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        // Check correct data response
        assertThat(response?.data, notNullValue())
        assertThat(response?.data, instanceOf(List::class.java))
        assertThat(response?.data?.get(0) , instanceOf(ComicModel::class.java))
        // Check no failures in response
        assertThat(response?.failureType, equalTo(null))
    }

    @Test
    fun get_comics_failure() {
        // Prepare service call
        setupFailureGetComicsByCharacterIdServiceCall()

        val response = uut?.getComics(CHARACTER_ID, OFFSET)

        // Verify service call made
        verify(characterDetailsApi, times(1)).getComicsByCharacterId(CHARACTER_ID, OFFSET)
        // Check correct status response
        assertThat(response?.status, equalTo(ResponseWrapper.Status.ERROR))
        // Check correct data response
        assertThat(response?.data, nullValue())
        // Check failure type in response
        assertThat(response?.failureType, instanceOf(FailureType.ConnectionError::class.java))
    }

    @Test
    fun get_series_success() {
        // Prepare service call
        setupSuccessGetSeriesByCharacterIdServiceCall()

        val response = uut?.getSeries(CHARACTER_ID, OFFSET)

        // Verify service call made
        verify(characterDetailsApi, times(1)).getSeriesByCharacterId(CHARACTER_ID, OFFSET)
        // Check correct status response
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        // Check correct data response
        assertThat(response?.data, notNullValue())
        assertThat(response?.data, instanceOf(List::class.java))
        assertThat(response?.data?.get(0) , instanceOf(SerieModel::class.java))
        // Check no failures in response
        assertThat(response?.failureType, equalTo(null))
    }

    @Test
    fun get_series_failure() {
        // Prepare service call
        setupFailureGetSeriesByCharacterIdServiceCall()

        val response = uut?.getSeries(CHARACTER_ID, OFFSET)

        // Verify service call made
        verify(characterDetailsApi, times(1)).getSeriesByCharacterId(CHARACTER_ID, OFFSET)
        // Check correct status response
        assertThat(response?.status, equalTo(ResponseWrapper.Status.ERROR))
        // Check correct data response
        assertThat(response?.data, nullValue())
        // Check failure type in response
        assertThat(response?.failureType, instanceOf(FailureType.ConnectionError::class.java))
    }

    private fun initApiHandler() {
        apiHandler = ApiHandler(networkHandler)
        whenever(networkHandler.isConnected).thenReturn(true)
    }

    private fun setupSuccessGetCharacterByIdServiceCall() {
        val call: Call<GetCharactersResponseEntity> = mock()
        whenever(call.execute()).thenReturn(Response.success(getCharactersByIdResponseEntity))
        whenever(characterDetailsApi.getCharacterById(CHARACTER_ID)).thenReturn(call)
    }

    private fun setupFailureGetCharacterBiIdServiceCall() {
        whenever(networkHandler.isConnected).thenReturn(false)
        val call: Call<GetCharactersResponseEntity> = mock()
        whenever(call.execute()).thenReturn(Response.success(getCharactersByIdResponseEntity))
        whenever(characterDetailsApi.getCharacterById(CHARACTER_ID)).thenReturn(call)
    }

    private fun setupSuccessGetComicsByCharacterIdServiceCall() {
        val call: Call<GetComicsResponseEntity> = mock()
        whenever(call.execute()).thenReturn(Response.success(getComicsResponseEntity))
        whenever(characterDetailsApi.getComicsByCharacterId(CHARACTER_ID, OFFSET)).thenReturn(call)
    }

    private fun setupFailureGetComicsByCharacterIdServiceCall() {
        whenever(networkHandler.isConnected).thenReturn(false)
        val call: Call<GetComicsResponseEntity> = mock()
        whenever(call.execute()).thenReturn(Response.success(getComicsResponseEntity))
        whenever(characterDetailsApi.getComicsByCharacterId(CHARACTER_ID, OFFSET)).thenReturn(call)
    }

    private fun setupSuccessGetSeriesByCharacterIdServiceCall() {
        val call: Call<GetSeriesResponseEntity> = mock()
        whenever(call.execute()).thenReturn(Response.success(getSeriesResponseEntity))
        whenever(characterDetailsApi.getSeriesByCharacterId(CHARACTER_ID, OFFSET)).thenReturn(call)
    }

    private fun setupFailureGetSeriesByCharacterIdServiceCall() {
        whenever(networkHandler.isConnected).thenReturn(false)
        val call: Call<GetSeriesResponseEntity> = mock()
        whenever(call.execute()).thenReturn(Response.success(getSeriesResponseEntity))
        whenever(characterDetailsApi.getSeriesByCharacterId(CHARACTER_ID, OFFSET)).thenReturn(call)
    }

    private fun initEntities() {
        getCharactersByIdResponseEntity =
            getObject("get_character_by_id_response_entity.json", GetCharactersResponseEntity::class.java)
        getComicsResponseEntity =
            getObject("get_comics_response_entity.json", GetComicsResponseEntity::class.java)
        getSeriesResponseEntity =
            getObject("get_series_response_entity.json", GetSeriesResponseEntity::class.java)

    }
}