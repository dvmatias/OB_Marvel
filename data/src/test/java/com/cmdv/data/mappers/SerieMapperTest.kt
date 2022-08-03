package com.cmdv.data.mappers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.data.entities.SerieEntity
import com.cmdv.domain.models.SerieModel
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SerieMapperTest : BaseUnitTest<SerieMapper>() {

    private lateinit var serieEntity: SerieEntity

    @Before
    fun setUp() {
        initEntities()

        uut = SerieMapper
    }

    @Test
    fun transform_entity_to_model() {
        val result = uut?.transformEntityToModel(serieEntity)

        // Check transformation result
        assertThat(result, notNullValue())
        assertThat(result, instanceOf(SerieModel::class.java))
        assertThat(result!!.id, equalTo(serieEntity.id))
        assertThat(result.id, equalTo(354))
        assertThat(result.description, equalTo(serieEntity.description ?: ""))
        assertThat(result.description, equalTo("The Avengers return! Earth's Mightiest Heroes reunite with their biggest guns at the forefront to take on familiar enemies and new threats alike! Featuring the work of Kurt Busiek, George Perez and other quintessential Avengers creators!"))
        assertThat(result.title, equalTo(serieEntity.title))
        assertThat(result.title, equalTo("Avengers (1998 - 2004)"))
        assertThat(
            result.thumbnail,
            equalTo("${serieEntity.thumbnail?.path}.${serieEntity.thumbnail?.extension}")
        )
        assertThat(
            result.thumbnail,
            equalTo("http://i.annihil.us/u/prod/marvel/i/mg/6/00/5130f06bd981b.jpg")
        )
    }

    private fun initEntities() {
        serieEntity =
            fromJson("serie_entity.json", SerieEntity::class.java)
    }

}