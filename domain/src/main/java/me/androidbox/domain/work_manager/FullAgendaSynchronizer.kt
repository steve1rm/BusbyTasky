package me.androidbox.domain.work_manager

import java.util.UUID

interface FullAgendaSynchronizer {
    fun sync(): UUID

    fun cancel()
}