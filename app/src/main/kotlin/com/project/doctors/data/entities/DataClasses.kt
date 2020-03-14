package com.project.doctors.data.entities

import com.google.gson.annotations.SerializedName

data class Response(
    @field:SerializedName("meta")
    val meta: MetaEntity,
    @field:SerializedName("data")
    val data: List<DataEntity>,
    @field:SerializedName("links")
    val links: List<LinkEntity>
)

data class MetaEntity(
    @field:SerializedName("total")
    val total: Int,
    @field:SerializedName("page_size")
    val pageSize: Int,
    @field:SerializedName("page_no")
    val pageNo: Int,
    @field:SerializedName("type")
    val type: String
)

data class DataEntity(
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("record")
    val record: RecordEntity,
    @field:SerializedName("links")
    val links: List<LinkEntity>
)

data class RecordEntity(
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("first_name")
    val firstName: String?,
    @field:SerializedName("last_name")
    val lastName: String?,
    @field:SerializedName("uri")
    val uri: String?,
    @field:SerializedName("professional_statement")
    val statement: String?,
    @field:SerializedName("links")
    val links: List<LinkEntity>?
)

data class LinkEntity(
    @field:SerializedName("rel")
    val reference: String,
    @field:SerializedName("href")
    val url: String?
)


data class Record(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val uri: String?,
    val statement: String
)