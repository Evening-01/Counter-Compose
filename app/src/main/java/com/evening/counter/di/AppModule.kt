package com.evening.counter.di


import android.content.Context
import androidx.room.Room
import com.evening.counter.data.AppDatabase
import com.evening.counter.data.dao.AccountingDao
import com.evening.counter.repository.AccountingRepository
import com.evening.counter.repository.AccountingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "accounting-db"
        ).build()
    }

    @Provides
    fun provideAccountingDao(database: AppDatabase): AccountingDao {
        return database.accountingDao()
    }

    @Provides
    @Singleton
    fun provideAccountingRepository(dao: AccountingDao): AccountingRepository {
        return AccountingRepositoryImpl(dao)
    }
}