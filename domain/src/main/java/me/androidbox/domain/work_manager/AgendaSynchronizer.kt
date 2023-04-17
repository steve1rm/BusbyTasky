package me.androidbox.domain.work_manager

import java.util.UUID

interface AgendaSynchronizer {
    suspend fun sync(): UUID
}