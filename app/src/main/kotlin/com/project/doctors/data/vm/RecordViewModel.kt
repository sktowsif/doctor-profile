package com.project.doctors.data.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.project.doctors.data.entities.Record
import com.project.doctors.data.mapper.RecordListMapper
import com.project.doctors.data.source.RecordAPI
import com.project.doctors.data.source.RecordDataSource

class RecordViewModel(
    private val recordListMapper: RecordListMapper,
    private val recordService: RecordAPI
) : ViewModel() {

    val recordLiveData: LiveData<PagedList<Record>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .build()
        recordLiveData = initializePageListBuilder(config).build()
    }

    private fun initializePageListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, Record> {
        val dataSourceFactory = object : DataSource.Factory<Int, Record>() {
            override fun create(): DataSource<Int, Record> {
                return RecordDataSource(viewModelScope, recordListMapper, recordService)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

}