package me.androidbox.domain.work_manager

import java.util.UUID

interface TaskCreateSynchronizer {
    fun sync(): UUID

    fun cancel()
}
