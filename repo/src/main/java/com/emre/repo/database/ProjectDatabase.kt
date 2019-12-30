package com.emre.repo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.emre.common.Constants
import com.emre.repo.database.dao.CurrencySelectionDao
import com.emre.repo.database.entity.CurrencySelectionEntity
import io.reactivex.schedulers.Schedulers


/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
@Database(
    entities = [
        CurrencySelectionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ProjectDatabase : RoomDatabase(){
    abstract fun currencySelectionDao(): CurrencySelectionDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ProjectDatabase? = null

        fun getInstance(context: Context): ProjectDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ProjectDatabase {
            return Room.databaseBuilder(context, ProjectDatabase::class.java, "currency_selection_db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //pre-populate data
                            instance?.currencySelectionDao()
                                    ?.insert(CurrencySelectionEntity(Constants.BASE_CURRENCY, Constants.BASE_AMOUNT))
                                    ?.subscribeOn(Schedulers.io())
                                    ?.subscribe()
                    }
                })
                .build()
        }
    }

}