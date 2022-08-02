package com.cmdv.data.repositories

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.data.database.CharacterRoomDatabase
import com.cmdv.data.database.FavoriteCharacterRoomDataBase
import com.cmdv.data.mappers.CharacterRoomMapper
import com.cmdv.data.sources.dbdaos.CharacterDao
import com.cmdv.data.sources.dbdaos.FavoriteCharacterDao
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.utils.ResponseWrapper
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

private const val CHARACTER_ID = 1017100
private const val FAVORITE_CHARACTER_ID_1 = 1010686
private const val FAVORITE_CHARACTER_ID_2 = 1009159
private const val FAVORITE_CHARACTER_ID_3 = 1009160
private const val NO_FAVORITE_CHARACTER_ID_1 = 1017100
private const val POSITION_0 = 0
private const val POSITION_1 = 1
private const val POSITION_2 = 2
private const val POSITION_3 = 3

@RunWith(AndroidJUnit4::class)
class FavoriteCharacterRepositoryTest : BaseUnitTest<FavoriteCharacterRepository>() {

    private lateinit var characterDB: CharacterRoomDatabase

    private lateinit var characterDao: CharacterDao

    private lateinit var favoriteDB: FavoriteCharacterRoomDataBase

    private lateinit var favoriteCharacterDao: FavoriteCharacterDao

    private lateinit var characters: List<CharacterModel>

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        initDb()
        initEntities()

        uut = FavoriteCharacterRepositoryImpl(characterDao, favoriteCharacterDao)
    }

    @Test
    fun add() {
        // Get DB count items before adding element
        val originalCount = favoriteCharacterDao.getAll().size

        val response = uut?.add(CHARACTER_ID, POSITION_0)

        // Initially DB was empty
        assertThat(originalCount, equalTo(0))
        // Check new size and element insertion
        assertThat(favoriteCharacterDao.getAll().size, equalTo(1))
        assertThat(favoriteCharacterDao.exists(CHARACTER_ID), equalTo(true))
        assertThat(favoriteCharacterDao.getById(CHARACTER_ID), notNullValue())
        assertThat(favoriteCharacterDao.getById(CHARACTER_ID)?.characterId, equalTo(CHARACTER_ID))
        // Check response
        assertThat(response, notNullValue())
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        assertThat(response?.data, notNullValue())
        assertThat(response?.data?.getContentIfNotHandled(), equalTo(POSITION_0))
        assertThat(response?.failureType, nullValue())
    }

    @Test
    fun remove() {
        // Add an item to DB
        add()
        // Get DB count items before removing element
        val originalCount = favoriteCharacterDao.getAll().size

        val response = uut?.remove(CHARACTER_ID, POSITION_0)

        // Initially DB was not empty
        assertThat(originalCount, equalTo(1))
        // Check new size and element insertion
        assertThat(favoriteCharacterDao.getAll().size, equalTo(0))
        assertThat(favoriteCharacterDao.getAll().isEmpty(), equalTo(true))
        assertThat(favoriteCharacterDao.exists(CHARACTER_ID), equalTo(false))
        assertThat(favoriteCharacterDao.getById(CHARACTER_ID), nullValue())
        // Check response
        assertThat(response, notNullValue())
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        assertThat(response?.data, notNullValue())
        assertThat(response?.data?.getContentIfNotHandled(), equalTo(POSITION_0))
        assertThat(response?.failureType, nullValue())
    }

    @Test
    fun get_all() {
        // Add 3  characters
        characters.map { CharacterRoomMapper.transformModelToEntity(it) }.also { characterDao.insert(it) }
        // Add 3  favorites
        uut?.add(FAVORITE_CHARACTER_ID_1, POSITION_1)
        uut?.add(FAVORITE_CHARACTER_ID_2, POSITION_2)
        uut?.add(FAVORITE_CHARACTER_ID_3, POSITION_3)

        val response = uut?.getAll()

        // Check new size and element insertion
        assertThat(favoriteCharacterDao.getAll().size, equalTo(3))
        assertThat(favoriteCharacterDao.getAll().isEmpty(), equalTo(false))
        assertThat(favoriteCharacterDao.exists(FAVORITE_CHARACTER_ID_1), equalTo(true))
        assertThat(favoriteCharacterDao.getById(FAVORITE_CHARACTER_ID_1), notNullValue())
        assertThat(favoriteCharacterDao.exists(FAVORITE_CHARACTER_ID_2), equalTo(true))
        assertThat(favoriteCharacterDao.getById(FAVORITE_CHARACTER_ID_2), notNullValue())
        assertThat(favoriteCharacterDao.exists(FAVORITE_CHARACTER_ID_3), equalTo(true))
        assertThat(favoriteCharacterDao.getById(FAVORITE_CHARACTER_ID_3), notNullValue())
        // Check response
        assertThat(response, notNullValue())
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        assertThat(response?.data, notNullValue())
        assertThat(response?.data?.size, equalTo(3))
        assertThat(response?.data?.get(0)?.id, equalTo(FAVORITE_CHARACTER_ID_1))
        assertThat(response?.data?.get(1)?.id, equalTo(FAVORITE_CHARACTER_ID_2))
        assertThat(response?.data?.get(2)?.id, equalTo(FAVORITE_CHARACTER_ID_3))
        assertThat(response?.failureType, nullValue())
    }

    @Test
    fun is_favorite_true() {
        // Add 3  characters
        characters.map { CharacterRoomMapper.transformModelToEntity(it) }.also { characterDao.insert(it) }
        // Add 3  favorites
        uut?.add(FAVORITE_CHARACTER_ID_1, POSITION_1)
        uut?.add(FAVORITE_CHARACTER_ID_2, POSITION_2)
        uut?.add(FAVORITE_CHARACTER_ID_3, POSITION_3)

        val response = uut?.isFavorite(FAVORITE_CHARACTER_ID_1)

        // Check new size and element insertion
        assertThat(favoriteCharacterDao.getAll().size, equalTo(3))
        assertThat(favoriteCharacterDao.getAll().isEmpty(), equalTo(false))
        assertThat(favoriteCharacterDao.exists(FAVORITE_CHARACTER_ID_1), equalTo(true))
        assertThat(favoriteCharacterDao.getById(FAVORITE_CHARACTER_ID_1), notNullValue())
        assertThat(favoriteCharacterDao.exists(FAVORITE_CHARACTER_ID_2), equalTo(true))
        assertThat(favoriteCharacterDao.getById(FAVORITE_CHARACTER_ID_2), notNullValue())
        assertThat(favoriteCharacterDao.exists(FAVORITE_CHARACTER_ID_3), equalTo(true))
        assertThat(favoriteCharacterDao.getById(FAVORITE_CHARACTER_ID_3), notNullValue())
        // Check response
        assertThat(response, notNullValue())
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        assertThat(response?.data, notNullValue())
        assertThat(response?.data, equalTo(true))
        assertThat(response?.failureType, nullValue())
    }

    @Test
    fun is_favorite_false() {
        // Add 3  characters
        characters.map { CharacterRoomMapper.transformModelToEntity(it) }.also { characterDao.insert(it) }
        // Add 3  favorites
        uut?.add(FAVORITE_CHARACTER_ID_1, POSITION_1)
        uut?.add(FAVORITE_CHARACTER_ID_2, POSITION_2)
        uut?.add(FAVORITE_CHARACTER_ID_3, POSITION_3)

        val response = uut?.isFavorite(NO_FAVORITE_CHARACTER_ID_1)

        // Check new size and element insertion
        assertThat(favoriteCharacterDao.getAll().size, equalTo(3))
        assertThat(favoriteCharacterDao.getAll().isEmpty(), equalTo(false))
        assertThat(favoriteCharacterDao.exists(FAVORITE_CHARACTER_ID_1), equalTo(true))
        assertThat(favoriteCharacterDao.getById(FAVORITE_CHARACTER_ID_1), notNullValue())
        assertThat(favoriteCharacterDao.exists(FAVORITE_CHARACTER_ID_2), equalTo(true))
        assertThat(favoriteCharacterDao.getById(FAVORITE_CHARACTER_ID_2), notNullValue())
        assertThat(favoriteCharacterDao.exists(FAVORITE_CHARACTER_ID_3), equalTo(true))
        assertThat(favoriteCharacterDao.getById(FAVORITE_CHARACTER_ID_3), notNullValue())
        // Check response
        assertThat(response, notNullValue())
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        assertThat(response?.data, notNullValue())
        assertThat(response?.data, equalTo(false))
        assertThat(response?.failureType, nullValue())
    }

    @Test
    fun remove_all() {
        // Add 3  characters
        characters.map { CharacterRoomMapper.transformModelToEntity(it) }.also { characterDao.insert(it) }
        // Add 3  favorites
        uut?.add(FAVORITE_CHARACTER_ID_1, POSITION_1)
        uut?.add(FAVORITE_CHARACTER_ID_2, POSITION_2)
        uut?.add(FAVORITE_CHARACTER_ID_3, POSITION_3)
        // Get DB count items before adding element
        val originalCount = favoriteCharacterDao.getAll().size

        val response = uut?.removeAll()

        // Initially DB was not empty
        assertThat(originalCount, equalTo(3))
        assertThat(favoriteCharacterDao.getAll().size, equalTo(0))
        assertThat(favoriteCharacterDao.getAll().isEmpty(), equalTo(true))
        // Check response
        assertThat(response, notNullValue())
        assertThat(response?.status, equalTo(ResponseWrapper.Status.READY))
        assertThat(response?.data, notNullValue())
        assertThat(response?.data?.peekContent(), equalTo(1))
        assertThat(response?.failureType, nullValue())
    }

    private fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        // Character DB
        characterDB = Room.inMemoryDatabaseBuilder(
            context,
            CharacterRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        characterDao = characterDB.characterDao
        // Favorite DB
        favoriteDB = Room.inMemoryDatabaseBuilder(
            context,
            FavoriteCharacterRoomDataBase::class.java
        ).allowMainThreadQueries().build()
        favoriteCharacterDao = favoriteDB.favoriteCharacterDao
    }

    private fun initEntities() {
        val character1 =
            getObject("favorite_character_model_1.json", CharacterModel::class.java)
        val character2 =
            getObject("favorite_character_model_2.json", CharacterModel::class.java)
        val character3 =
            getObject("favorite_character_model_3.json", CharacterModel::class.java)
        characters = listOf(character1, character2, character3)
    }
}