package com.cmdv.data.repositories

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.data.database.CharactersRoomDatabase
import com.cmdv.data.entities.GetCharactersResponseEntity
import com.cmdv.data.errorhandling.CharactersApiErrorHandler
import com.cmdv.data.mappers.CharacterRoomMapper
import com.cmdv.data.mappers.GetCharactersResponseMapper
import com.cmdv.data.networking.ApiHandler
import com.cmdv.data.networking.NetworkHandler
import com.cmdv.data.sources.apiservices.CharactersApi
import com.cmdv.data.sources.dbdaos.CharactersDao
import com.cmdv.data.sources.dbdaos.FavoriteCharactersDao
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.ResponseWrapper
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CharactersRepositoryTest : BaseUnitTest<CharactersRepository>() {
    @Mock
    private lateinit var charactersApi: CharactersApi

    @Mock
    private lateinit var favoriteCharactersDao: FavoriteCharactersDao

    @Mock
    private lateinit var networkHandler: NetworkHandler

    @Mock
    private lateinit var errorHandler: CharactersApiErrorHandler

    @Mock
    private lateinit var apiHandler: ApiHandler

    private lateinit var charactersDB: CharactersRoomDatabase

    private lateinit var charactersDao: CharactersDao

    private lateinit var getCharactersResponseEntity: GetCharactersResponseEntity

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        initEntities()
        initApiHandler()
        initDb()

        uut = CharactersRepositoryImpl(charactersApi, charactersDao, favoriteCharactersDao, apiHandler, errorHandler)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        charactersDB.close()
    }

    @Test
    fun test_get_total_characters_count_success() = runBlocking {
        // Prepare service call
        setupSuccessGetCharactersServiceCall(1, 0)

        val response = uut?.getTotalCharactersCount()

        // Verify service call made
        verify(charactersApi, times(1)).getCharacters(1, 0)
        // Check correct status response
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        // Check correct data response
        assertThat(response?.data, notNullValue())
        assertThat(response?.data, equalTo(getCharactersResponseEntity.data!!.total))
        // Check no failures in response
        assertThat(response?.failureType, equalTo(null))
    }

    @Test
    fun get_characters_success_with_fetch_true_and_no_stored_characters_in_db() = runBlocking {
        // Remove characters from DB
        charactersDao.deleteAll()
        // Prepare service call
        setupSuccessGetCharactersServiceCall(32, 0)

        val response = uut?.getCharacters(true, 32, 0)

        // Verify service call made
        verify(charactersApi, times(1)).getCharacters(32, 0)
        // Check correct status response
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        // Check correct data response
        assertThat(response?.data, notNullValue())
        // Check correct data response size
        assertThat(response?.data?.size, equalTo(32))
        // Check characters were stored in DB
        assertThat(charactersDao.getAll().size, equalTo(32))
    }

    @Test
    fun get_characters_success_with_fetch_false_and_no_stored_characters_in_db() = runBlocking {
        // Remove characters from DB
        charactersDao.deleteAll()
        // Prepare service call
        setupSuccessGetCharactersServiceCall(32, 0)

        val response = uut?.getCharacters(false, 32, 0)

        // Verify service call made
        verify(charactersApi, times(1)).getCharacters(32, 0)
        // Check correct status response
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        // Check correct data response
        assertThat(response?.data, notNullValue())
        // Check correct data response size
        assertThat(response?.data?.size, equalTo(32))
        // Check characters were stored in DB
        assertThat(charactersDao.getAll().size, equalTo(32))
    }

    @Test
    fun get_characters_success_with_fetch_true_and_stored_characters_in_db() = runBlocking {
        // Store characters in DB
        storeCharactersInDb()
        // Prepare service call
        setupSuccessGetCharactersServiceCall(32, 0)

        val response = uut?.getCharacters(true, 32, 0)

        // Verify no service call made
        verify(charactersApi, times(1)).getCharacters(32, 0)
        // Check correct status response
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        // Check correct data response
        assertThat(response?.data, notNullValue())
        // Check correct data response size
        assertThat(response?.data?.size, equalTo(64))
        // Check characters were stored in DB
        assertThat(charactersDao.getAll().size, equalTo(64))
    }

    @Test
    fun get_characters_success_with_fetch_false_and_stored_characters_in_db() = runBlocking {
        // Store characters in DB
        storeCharactersInDb()

        val response = uut?.getCharacters(false, 32, 0)

        // Verify no service call made
        verify(charactersApi, times(0)).getCharacters(32, 0)
        // Check correct status response
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        // Check correct data response
        assertThat(response?.data, notNullValue())
        // Check correct data response size
        assertThat(response?.data?.size, equalTo(32))
        // Check characters were stored in DB
        assertThat(charactersDao.getAll().size, equalTo(32))
    }

    @Test
    fun remove_stored_characters() {
        // Store characters in DB
        storeCharactersInDb()

        // Check characters were stored in DB
        assertThat(charactersDao.getAll().size, equalTo(32))

        uut?.removeStoredCharacters()

        // Verify no service call made
        verify(charactersApi, times(0)).getCharacters(32, 0)
        // Check characters were deleted from DB
        assertThat(charactersDao.getAll().size, equalTo(0))
    }

    private fun initEntities() {
        getCharactersResponseEntity =
            getObject("get_characters_response_entity.json", GetCharactersResponseEntity::class.java)
    }

    private fun initApiHandler() {
        apiHandler = ApiHandler(networkHandler)
        whenever(networkHandler.isConnected).thenReturn(true)
    }

    private fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        charactersDB = Room.inMemoryDatabaseBuilder(
            context,
            CharactersRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        charactersDao = charactersDB.charactersDao
    }

    private fun storeCharactersInDb() {
        GetCharactersResponseMapper.transformEntityToModel(getCharactersResponseEntity).characters.map {
            CharacterRoomMapper.transformModelToEntity(it)
        }.also {
            charactersDao.insert(it)
        }
    }

    private fun setupSuccessGetCharactersServiceCall(limit: Int, @Suppress("SameParameterValue") offset: Int) {
        val call: Call<GetCharactersResponseEntity> = mock()
        whenever(call.execute()).thenReturn(Response.success(getCharactersResponseEntity))
        whenever(charactersApi.getCharacters(limit, offset)).thenReturn(call)
    }
}