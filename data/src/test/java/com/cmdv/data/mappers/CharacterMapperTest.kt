package com.cmdv.data.mappers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.data.entities.CharacterEntity
import com.cmdv.domain.models.CharacterModel
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterMapperTest : BaseUnitTest<CharacterMapper>() {

    private lateinit var characterEntity: CharacterEntity

    @Before
    fun setUp() {
        initEntities()

        uut = CharacterMapper
    }

    @Test
    fun transform_entity_to_model() {
        val result = uut?.transformEntityToModel(characterEntity)

        // Check transformation result
        assertThat(result, notNullValue())
        assertThat(result, instanceOf(CharacterModel::class.java))
        assertThat(result!!.id, equalTo(characterEntity.id))
        assertThat(result.id, equalTo(1009148))
        assertThat(result.name, equalTo("Absorbing Man"))
        assertThat(result.description, equalTo(characterEntity.description))
        assertThat(result.description, equalTo(""))
        assertThat(
            result.thumbnail,
            equalTo("${characterEntity.thumbnail?.path}.${characterEntity.thumbnail?.extension}")
        )
        assertThat(result.thumbnail, equalTo("http://i.annihil.us/u/prod/marvel/i/mg/1/b0/5269678709fb7.jpg"))
        assertThat(result.isFavourite, equalTo(false))
        assertThat(result.comicsCount, equalTo(characterEntity.comics?.available))
        assertThat(result.comicsCount, equalTo(96))
        assertThat(result.seriesCount, equalTo(characterEntity.series?.available))
        assertThat(result.seriesCount, equalTo(48))
        assertThat(result.storiesCount, equalTo(characterEntity.stories?.available))
        assertThat(result.storiesCount, equalTo(109))
    }

    private fun initEntities() {
        characterEntity =
            fromJson("character_entity.json", CharacterEntity::class.java)
    }
}