package com.project.doctors.data.source

import com.project.doctors.data.entities.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecordAPI {

    @GET("search/all_physicians")
    suspend fun search(@Query("page_no") pageNo: Int): Response

}