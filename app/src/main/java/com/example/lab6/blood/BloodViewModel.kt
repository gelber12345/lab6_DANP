package com.example.lab6.blood

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class BloodViewModel (appObj: Application) : AndroidViewModel(appObj) {
    val bloodFlow = fetchBloodData()

    fun fetchBloodData(): Flow<PagingData<Blood>> {
        val repo = BloodRepository(totalCount = 99, pageSize = 5,this.getApplication<Application>().applicationContext)
        return Pager(PagingConfig(pageSize = repo.pageSize)) {
            BloodSource(repo,)
        }.flow
    }
}

