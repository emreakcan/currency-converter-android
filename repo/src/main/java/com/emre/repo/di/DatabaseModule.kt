package com.emre.repo.di

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.emre.common.di.scope.RepositoryScope
import com.emre.repo.database.ProjectDatabase
import com.emre.repo.database.dao.CurrencySelectionDao
import dagger.Module
import dagger.Provides


/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
@Module
class DatabaseModule {

    @RepositoryScope
    @Provides
    fun provideDatabase(context: Context): ProjectDatabase {
        return ProjectDatabase.getInstance(context)
    }

    @RepositoryScope
    @Provides
    fun provideLocalizationDao(db: ProjectDatabase): CurrencySelectionDao {
        return db.currencySelectionDao()
    }
}