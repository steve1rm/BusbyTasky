package me.androidbox.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.work.WorkManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.androidbox.data.local.DatabaseConstant.DATABASE_NAME
import me.androidbox.data.local.agenda.AgendaLocalRepositoryImp
import me.androidbox.data.local.converter.EventConverter
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.local.database.BusbyTaskyDatabase
import me.androidbox.data.local.event.EventRepositoryImp
import me.androidbox.data.remote.agenda.AgendaRemoteRepositoryImp
import me.androidbox.data.remote.event.VerifyVisitorEmailUseCaseImp
import me.androidbox.data.remote.preference.PreferenceRepositoryImp
import me.androidbox.data.worker_manager.AgendaSynchronizerImp
import me.androidbox.data.worker_manager.FullAgendaSynchronizerImp
import me.androidbox.data.worker_manager.UploadEventImp
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.remote.AgendaLocalRepository
import me.androidbox.domain.authentication.remote.EventRepository
import me.androidbox.domain.event.usecase.VerifyVisitorEmailUseCase
import me.androidbox.domain.repository.AgendaRemoteRepository
import me.androidbox.domain.work_manager.AgendaSynchronizer
import me.androidbox.domain.work_manager.FullAgendaSynchronizer
import me.androidbox.domain.work_manager.UploadEvent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsPreferenceRepositoryImp(preferenceRepositoryImp: PreferenceRepositoryImp): PreferenceRepository

    @Binds
    fun bindsEventRepositoryImp(eventRepositoryImp: EventRepositoryImp): EventRepository

    @Binds
    fun bindsAgendaRemoteRepositoryImp(agendaRemoteRepositoryImp: AgendaRemoteRepositoryImp): AgendaRemoteRepository

    @Binds
    fun bindsUploadEventImp(uploadEventImp: UploadEventImp): UploadEvent

    @Binds
    fun bindsSyncAgendaItemsImp(syncAgendaItemsImp: AgendaSynchronizerImp): AgendaSynchronizer

    @Binds
    fun fullAgendaSynchronizerImp(fullAgendaSynchronizerImp: FullAgendaSynchronizerImp): FullAgendaSynchronizer

    @Binds
    fun bindsVerifyVisitorEmailUseCaseImp(verifyVisitorEmailUseCaseImp: VerifyVisitorEmailUseCaseImp): VerifyVisitorEmailUseCase

    @Reusable
    @Binds
    fun bindsAgendaRepositoryImp(agendaLocalRepositoryImp: AgendaLocalRepositoryImp): AgendaLocalRepository

    companion object {
        private const val SECRET_SHARED_PREFERENCES = "secret_shared_preferences"

        @Provides
        fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences(SECRET_SHARED_PREFERENCES, Context.MODE_PRIVATE)

            /** FIXME Result in crash using shared preferences instead of encrypted shared preferences */
            /* val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

            return EncryptedSharedPreferences.create(
                SECRET_SHARED_PREFERENCES,
                masterKey,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )*/
        }

        @Provides
        fun providesEventConverter(): EventConverter {
            return EventConverter()
        }

        @Provides
        fun providesRoomDatabase(@ApplicationContext context: Context): BusbyTaskyDatabase {
            return Room.databaseBuilder(context, BusbyTaskyDatabase::class.java, DATABASE_NAME)
                .build()
        }

        @Provides
        fun providesEventDao(busbyTaskyDatabase: BusbyTaskyDatabase): EventDao {
            return busbyTaskyDatabase.eventDao()
        }

        @Provides
        fun providesTaskDao(busbyTaskyDatabase: BusbyTaskyDatabase): TaskDao {
            return busbyTaskyDatabase.taskDao()
        }

        @Provides
        fun providesReminderDao(busbyTaskyDatabase: BusbyTaskyDatabase): ReminderDao {
            return busbyTaskyDatabase.reminderDao()
        }

        @Provides
        fun providesWorkManager(@ApplicationContext context: Context): WorkManager {
            return WorkManager.getInstance(context)
        }
    }
}