package com.cmdv.data.mappers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.data.entities.GetCharactersResponseEntity
import com.cmdv.domain.models.GetCharactersResponseModel
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetCharactersResponseMapperTest : BaseUnitTest<GetCharactersResponseMapper>() {

    private lateinit var getCharactersResponseEntity: GetCharactersResponseEntity

    @Before
    fun setUp() {
        initEntities()

        uut = GetCharactersResponseMapper
    }

    @Test
    fun transform_entity_to_model() {
        val result = uut?.transformEntityToModel(getCharactersResponseEntity)

        // Check transformation result
        assertThat(result, notNullValue())
        assertThat(result, instanceOf(GetCharactersResponseModel::class.java))
        assertThat(result!!.total, equalTo(getCharactersResponseEntity.data?.total))
        assertThat(result.total, equalTo(1562))
        assertThat(result.characters.size, equalTo(getCharactersResponseEntity.data?.results?.size))
        assertThat(result.characters.size, equalTo(32))
    }

    private fun initEntities() {
        getCharactersResponseEntity =
            fromJson("get_characters_response_entity.json", GetCharactersResponseEntity::class.java)
    }
}