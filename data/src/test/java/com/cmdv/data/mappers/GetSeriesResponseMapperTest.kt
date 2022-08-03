package com.cmdv.data.mappers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmdv.core.base.BaseUnitTest
import com.cmdv.data.entities.GetSeriesResponseEntity
import com.cmdv.domain.models.GetSeriesResponseModel
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetSeriesResponseMapperTest : BaseUnitTest<GetSeriesResponseMapper>() {

    private lateinit var getSeriesResponseEntity: GetSeriesResponseEntity

    @Before
    fun setUp() {
        initEntities()

        uut = GetSeriesResponseMapper
    }

    @Test
    fun transform_entity_to_model() {
        val result = uut?.transformEntityToModel(getSeriesResponseEntity)

        // Check transformation result
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(GetSeriesResponseModel::class.java))
        MatcherAssert.assertThat(result!!.total, CoreMatchers.equalTo(getSeriesResponseEntity.data?.total))
        MatcherAssert.assertThat(result.total, CoreMatchers.equalTo(27))
        MatcherAssert.assertThat(
            result.series.size,
            CoreMatchers.equalTo(getSeriesResponseEntity.data?.results?.size)
        )
        MatcherAssert.assertThat(result.series.size, CoreMatchers.equalTo(20))
    }

    private fun initEntities() {
        getSeriesResponseEntity =
            fromJson("get_series_response_entity.json", GetSeriesResponseEntity::class.java)
    }
}