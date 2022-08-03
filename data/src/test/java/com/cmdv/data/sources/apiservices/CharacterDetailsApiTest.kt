package com.cmdv.data.sources.apiservices

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.data.entities.CharacterEntity
import com.cmdv.data.entities.ComicEntity
import com.cmdv.data.entities.SerieEntity
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

private const val CHARACTER_ID = 1017100
private const val OFFSET = 32

@RunWith(AndroidJUnit4::class)
class CharacterDetailsApiTest : BaseUnitTest<CharacterDetailsApi>() {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(CharacterDetailsApi::class.java)

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUp() {
        uut = api
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun get_character_by_id() {
        mockWebServer.enqueue(getMockResponseSuccess("get_characters_response_entity.json"))

        runBlocking {
            val result = uut?.getCharacterById(CHARACTER_ID)?.execute()

            //
            assertThat(result!!.code(), equalTo(200))
            assertThat(result.message(), equalTo("OK"))
            with(result.body()!!) {
                assertThat(this, notNullValue())
                assertThat(code, equalTo(200))
                assertThat(status, equalTo("Ok"))
                assertThat(result.errorBody(), nullValue())
                with(data!!) {
                    assertThat(this, notNullValue())
                    assertThat(count, equalTo(32))
                    assertThat(offset, equalTo(64))
                    assertThat(limit, equalTo(32))
                    assertThat(total, equalTo(1562))
                    assertThat(results, notNullValue())
                    assertThat(results!!.size, equalTo(32))
                    assertThat(results!![0], instanceOf(CharacterEntity::class.java))
                }
            }
        }
    }

    @Test
    fun get_comics_by_character_id() {
        mockWebServer.enqueue(getMockResponseSuccess("get_comics_response_entity.json"))

        runBlocking {
            val result = uut?.getComicsByCharacterId(CHARACTER_ID, OFFSET)?.execute()

            //
            assertThat(result!!.code(), equalTo(200))
            assertThat(result.message(), equalTo("OK"))
            with(result.body()!!) {
                assertThat(this, notNullValue())
                assertThat(code, equalTo(200))
                assertThat(status, equalTo("Ok"))
                assertThat(result.errorBody(), nullValue())
                with(data!!) {
                    assertThat(this, notNullValue())
                    assertThat(count, equalTo(20))
                    assertThat(offset, equalTo(0))
                    assertThat(limit, equalTo(20))
                    assertThat(total, equalTo(54))
                    assertThat(results, notNullValue())
                    assertThat(results!!.size, equalTo(20))
                    assertThat(results!![0], instanceOf(ComicEntity::class.java))
                }
            }
        }
    }

    @Test
    fun get_series_by_character_id() {
        mockWebServer.enqueue(getMockResponseSuccess("get_series_response_entity.json"))

        runBlocking {
            val result = uut?.getSeriesByCharacterId(CHARACTER_ID, OFFSET)?.execute()

            //
            assertThat(result!!.code(), equalTo(200))
            assertThat(result.message(), equalTo("OK"))
            assertThat(result.errorBody(), nullValue())
            with(result.body()!!) {
                assertThat(this, notNullValue())
                assertThat(code, equalTo(200))
                assertThat(status, equalTo("Ok"))
                with(data!!) {
                    assertThat(this, notNullValue())
                    assertThat(count, equalTo(20))
                    assertThat(offset, equalTo(0))
                    assertThat(limit, equalTo(20))
                    assertThat(total, equalTo(27))
                    assertThat(results, notNullValue())
                    assertThat(results!!.size, equalTo(20))
                    assertThat(results!![0], instanceOf(SerieEntity::class.java))
                }
            }
        }
    }

    private fun getMockResponseSuccess(fileName: String): MockResponse {
        val inputStream = javaClass.getResourceAsStream(fileName)
        val source = inputStream?.let { inputStream.source().buffer() }
        return MockResponse()
            .setResponseCode(200)
            .setBody(source!!.readString(StandardCharsets.UTF_8))
    }
}