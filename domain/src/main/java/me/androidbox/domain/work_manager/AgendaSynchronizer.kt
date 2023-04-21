package me.androidbox.domain.work_manager

import java.util.UUID

interface AgendaSynchronizer {
    fun sync(): UUID
    fun cancel()
}