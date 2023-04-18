package me.androidbox.domain.work_manager

interface FullAgendaSynchronizer {
    suspend fun sync()

    suspend fun cancel()
}