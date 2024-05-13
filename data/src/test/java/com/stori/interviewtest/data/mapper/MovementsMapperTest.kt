package com.stori.interviewtest.data.mapper

import org.junit.Before
import org.junit.Test

class MovementsMapperTest {

    private lateinit var mapper: BetMapper

    @Before
    fun setup() {
        mapper = BetMapper()
    }

    @Test
    fun dtoToModel() {
        /*val dto = Map("_", 0, 0, "")
        val model = mapper.fromResponseToDomain(dto)

        assertNotNull(this)
        assertEquals(dto.type, model.type)
        assertEquals(dto.odds, model.total)*/
    }
}
