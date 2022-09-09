package com.johndev.aitrainer.Iterations.IterationsChargeDataModule.model

import android.content.Context
import android.net.Uri

class RangesMemoryRepository {

    private var memoryDatabase = RangesMemoryDatabase()

    suspend fun readTextFromUri(uri: Uri, context: Context) = memoryDatabase.readTextFromUri(uri, context)

}