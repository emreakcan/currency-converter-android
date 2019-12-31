package com.emre.currency

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.emre.repo.database.ProjectDatabase
import com.emre.repo.database.dao.CurrencySelectionDao
import com.emre.repo.database.entity.CurrencySelectionEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {

    private var noteDAO: CurrencySelectionDao? = null
    private var db: ProjectDatabase? = null

    @Before
    fun onCreateDB() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, ProjectDatabase::class.java).build()
        noteDAO = db!!.currencySelectionDao()
    }

    @Test
    @Throws(Exception::class)
    fun testInsertedCurrency() {
        val baseCurrency = CurrencySelectionEntity("CRY", "10.0")

        noteDAO!!.insert(baseCurrency).blockingAwait()

        noteDAO!!.getLastSelected()
            .test()
            .awaitCount(1)
            .assertValueCount(1)
            .assertValue {
                it.code == baseCurrency.code
            }

    }

    @Test
    @Throws(Exception::class)
    fun testInsertedAmount() {
        val baseCurrency = CurrencySelectionEntity("CRY", "10.0")

        noteDAO!!.insert(baseCurrency).blockingAwait()

        noteDAO!!.getLastSelected()
            .test()
            .awaitCount(1)
            .assertValueCount(1)
            .assertValue {
                it.amount == baseCurrency.amount
            }

    }

}

