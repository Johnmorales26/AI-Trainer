package com.johndev.aitrainer.Iterations.IterationsChargeDataModule.model

import android.content.Context
import android.net.Uri

class MemoryRepository {

    private var memoryDatabase = MemoryDatabase()

    suspend fun readTextFromUri(uri: Uri, context: Context) = memoryDatabase.readTextFromUri(uri, context)

}