package me.androidbox.domain.work_manager

import java.util.UUID

interface SyncAgendaItems {
    suspend fun sync(): UUID
}