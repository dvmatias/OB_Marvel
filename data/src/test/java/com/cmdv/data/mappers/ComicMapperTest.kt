package com.cmdv.data.mappers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.data.entities.ComicEntity
import com.cmdv.domain.models.ComicModel
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComicMapperTest : BaseUnitTest<ComicMapper>() {

    private lateinit var comicEntity: ComicEntity

    @Before
    fun setUp() {
        initEntities()

        uut = ComicMapper
    }

    @Test
    fun transform_entity_to_model() {
        val result = uut?.transformEntityToModel(comicEntity)

        // Check transformation result
        assertThat(result, notNullValue())
        assertThat(result, instanceOf(ComicModel::class.java))
        assertThat(result!!.id, equalTo(comicEntity.id))
        assertThat(result.id, equalTo(94081))
        assertThat(result.description, equalTo(comicEntity.description ?: ""))
        assertThat(result.description, equalTo(""))
        assertThat(result.title, equalTo(comicEntity.title))
        assertThat(result.title, equalTo("Maestro: World War M (2022) #3"))
        assertThat(
            result.thumbnail,
            equalTo("${comicEntity.thumbnail?.path}.${comicEntity.thumbnail?.extension}")
        )
        assertThat(result.thumbnail, equalTo("http://i.annihil.us/u/prod/marvel/i/mg/e/30/62685195f3744.jpg"))
    }

    private fun initEntities() {
        comicEntity =
            fromJson("comic_entity.json", ComicEntity::class.java)
    }

}