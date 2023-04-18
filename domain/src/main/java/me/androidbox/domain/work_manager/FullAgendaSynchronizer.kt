package me.androidbox.domain.work_manager

import java.util.UUID

interface FullAgendaSynchronizer {
    suspend fun sync(): UUID

    suspend fun cancel()
}