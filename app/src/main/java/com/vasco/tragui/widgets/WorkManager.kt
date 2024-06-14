package com.vasco.tragui.widgets

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import java.util.Calendar

class UpdateWidgetWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                // Actualiza todos los widgets de tipo DrinkWidget
                val glanceManager = GlanceAppWidgetManager(applicationContext)
                val glanceIds = glanceManager.getGlanceIds(DrinkWidget::class.java)
                glanceIds.forEach { glanceId ->
                    DrinkWidget().update(applicationContext, glanceId)
                    refreshWidgetContent(applicationContext, glanceId)
                }
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}



fun scheduleDailyWidgetUpdate(context: Context) {
    val currentTime = Calendar.getInstance()

    // Establece la hora deseada, por ejemplo, las 9 de la mañana
    val desiredTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 13)
        set(Calendar.MINUTE, 37)
        set(Calendar.SECOND, 0)
    }

    // Calcula el tiempo inicial de retraso
    val initialDelay = if (currentTime.before(desiredTime)) {
        desiredTime.timeInMillis - currentTime.timeInMillis
    } else {
        // Si la hora actual ya pasó, programa para el próximo día
        desiredTime.timeInMillis + TimeUnit.DAYS.toMillis(1) - currentTime.timeInMillis
    }

    val workRequest = PeriodicWorkRequestBuilder<UpdateWidgetWorker>(24, TimeUnit.HOURS)
        .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "DailyWidgetUpdate",
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )
}

