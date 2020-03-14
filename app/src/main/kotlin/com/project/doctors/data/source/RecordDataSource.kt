package com.project.doctors.data.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.project.doctors.data.entities.Record
import com.project.doctors.data.entities.Response
import com.project.doctors.data.mapper.RecordListMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

class RecordDataSource(
    private val scope: CoroutineScope,
    private val recordListMapper: RecordListMapper,
    private val recordService: RecordAPI
) : PageKeyedDataSource<Int, Record>(), AnkoLogger {

    companion object {
        private const val FIRST_PAGE = 1
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Record>
    ) {
        scope.launch {
            try {
                val response = recordService.search(FIRST_PAGE)
                val records = withContext(Dispatchers.Default) { transformResponse(response) }
                callback.onResult(records, null, FIRST_PAGE + 1)
            } catch (ex: Exception) {
                error("Error in loading initial page: ${params.requestedLoadSize}", ex)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Record>) {
        scope.launch {
            try {
                val response = recordService.search(params.requestedLoadSize)
                val records = withContext(Dispatchers.Default) { transformResponse(response) }
                callback.onResult(records, params.key + 1)
            } catch (ex: Exception) {
                error("Error in loading after page: ${params.requestedLoadSize}", ex)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Record>) {}

    private fun transformResponse(response: Response): List<Record> {
        val recordEntities = response.data.map { it.record }.toList()
        return recordListMapper.map(recordEntities)
    }

}