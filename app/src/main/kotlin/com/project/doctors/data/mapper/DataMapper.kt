package com.project.doctors.data.mapper

import com.project.doctors.data.entities.LinkEntity
import com.project.doctors.data.entities.Record
import com.project.doctors.data.entities.RecordEntity

interface Mapper<I, O> {
    fun map(input: I): O
}

interface ListMapper<I, O> :
    Mapper<List<I>, List<O>>

open class ListMapperImpl<I, O>(private val mapper: Mapper<I, O>) :
    ListMapper<I, O> {
    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}

class RecordListMapper(mapper: RecordMapper) : ListMapperImpl<RecordEntity, Record>(mapper)

class RecordMapper :
    Mapper<RecordEntity, Record> {

    companion object {
        private const val PROFILE_PHOTO = "profile_photo"
    }

    override fun map(input: RecordEntity): Record {
        return Record(
            id = input.id,
            firstName = input.firstName ?: "",
            lastName = input.lastName ?: "",
            prefix = input.prefix ?: "",
            type = input.type ?: "",
            imageUri = findProfilePhoto(input.links),
            statement = input.statement ?: ""
        )
    }

    private fun findProfilePhoto(links: List<LinkEntity>?): String? {
        return if (links.isNullOrEmpty()) null
        else links.map { it.rel to it.url }.toMap()[PROFILE_PHOTO]
    }

}