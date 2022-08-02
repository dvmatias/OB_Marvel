package com.cmdv.data.mappers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.data.entities.GetComicsResponseEntity
import com.cmdv.domain.models.GetComicsResponseModel
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetComicsResponseMapperTest : BaseUnitTest<GetComicsResponseMapper>() {

    private lateinit var getComicsResponseEntity: GetComicsResponseEntity

    @Before
    fun setUp() {
        initEntities()

        uut = GetComicsResponseMapper
    }

    @Test
    fun transform_entity_to_model() {
        val result = uut?.transformEntityToModel(getComicsResponseEntity)

        // Check transformation result
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(GetComicsResponseModel::class.java))
        MatcherAssert.assertThat(result!!.total, CoreMatchers.equalTo(getComicsResponseEntity.data?.total))
        MatcherAssert.assertThat(result.total, CoreMatchers.equalTo(54))
        MatcherAssert.assertThat(
            result.comics.size,
            CoreMatchers.equalTo(getComicsResponseEntity.data?.results?.size)
        )
        MatcherAssert.assertThat(result.comics.size, CoreMatchers.equalTo(20))
    }

    private fun initEntities() {
        getComicsResponseEntity =
            getObject("get_comics_response_entity.json", GetComicsResponseEntity::class.java)
    }
}