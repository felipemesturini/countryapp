package com.felipe.labs.countryapi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.felipe.labs.countryapi.models.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val DB_VERSION = 1
private const val DB_NAME = "country.db"

@Database(entities = [Country::class], version = DB_VERSION, exportSchema = false)
abstract class DbCountry: RoomDatabase() {
    abstract val countryDao: CountryDao

    companion object {
        @Volatile
        private var INSTANCE: DbCountry? = null

        fun  getInstance(context: Context, coroutineScope: CoroutineScope): DbCountry {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DbCountry::class.java,
                    DB_NAME
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
//                    .addCallback(DbInitCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

//    private class DbInitCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            //Chamado após ter um abertura de conexao com o banco
//            INSTANCE?.let {db ->
//                scope.launch(Dispatchers.IO) {
//                    populateData(db.countryDao)
//                }
//            }
//        }
//
//        private fun populateData(userDao: UserDao) {
//            userDao.clear()
//            val user1 = User(0, "Felipe Mesturini", "fmesturini@gmail.com", "123456")
//            Log.i("populateData", user1.toString())
//            userDao.insert(user1)
//            val user2 = User(0, "Felipe Mesturini", "fmesturini@gmail.com", "123456")
//            userDao.insert(user2)
//            Log.i("populateData", user2.toString())
//        }
//    }
}