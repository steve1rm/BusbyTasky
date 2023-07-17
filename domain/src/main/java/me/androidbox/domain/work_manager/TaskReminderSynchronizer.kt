package me.androidbox.domain.work_manager

import java.util.UUID

interface TaskReminderSynchronizer {
    fun sync(): UUID

    fun cancel()
}
