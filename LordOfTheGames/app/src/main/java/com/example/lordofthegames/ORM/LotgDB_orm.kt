package com.example.lordofthegames.ORM

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.lordofthegames.Pair
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import prodotticondb.Prodotto
import prodotticondb.ProductDB
import java.sql.SQLException


const val DB_NAME = "lotgdb"
const val DB_VERSION = 2

class LotgDB_orm(context: Context) : OrmLiteSqliteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    var productDao: Dao<Prodotto, *>? = getDao(Prodotto::class.java)


    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        try {
            TableUtils.createTable(connectionSource, Prodotto::class.java)
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        try {
            if (isTableExisting(database, "prodotto")) {
                TableUtils.dropTable(
                    connectionSource,
                    LotgDB_orm::class.java,
                    false
                )
            }
            onCreate(database, connectionSource)
        } catch (e: SQLException) {
            throw java.lang.RuntimeException(e)
        }
    }

    private fun isTableExisting(db: SQLiteDatabase?, tableName: String): Boolean {
        val cursor: Cursor = db?.rawQuery(
            "SELECT DISTINCT name FROM sqlite_master WHERE type='table' AND name='$tableName'",
            null)!!

        if (cursor.count > 0) {
            cursor.close()
            return true
        }
        cursor.close()
        return false
    }

    fun clearTable(){
        TableUtils.clearTable(connectionSource, LotgDB_orm::class.java)
    }

    override fun close() {
        productDao = null
        super.close()
    }
}