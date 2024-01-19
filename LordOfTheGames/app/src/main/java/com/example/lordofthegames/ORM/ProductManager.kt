package com.example.lordofthegames.ORM

import android.content.Context
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.stmt.DeleteBuilder
import com.j256.ormlite.stmt.QueryBuilder
import com.j256.ormlite.stmt.UpdateBuilder
import org.joda.time.DateTime
import java.sql.SQLException
import java.util.Optional
import java.util.function.Consumer

class ProductManager(context: Context) {

    private val INDEX = "id"
    private val NAME = "name"
    private val PRICE = "price"
    private val IMG = "imageBytes"
    private val START = "startTime"
    private val END = "endTime"


    private val TAG = this@ProductManager.javaClass.simpleName
    private var INSTANCE: ProductManager? = ProductManager(context)
    private var productDBHelper: LotgDB_Orm? = OpenHelperManager.getHelper(context, LotgDB_Orm::class.java)

    private var productItemDao: Dao<Prodotto, *>? = productDBHelper?.productDao


    fun releaseDB() {
        if (productDBHelper != null) {
            OpenHelperManager.releaseHelper()
            productDBHelper = null
            INSTANCE = null
        }
    }

    fun clearAllData(): Int {
        return if (productDBHelper == null) -1 else try {
            productDBHelper!!.clearTable()
            0
        } catch (e: SQLException) {
            e.printStackTrace()
            -1
        }
    }

    fun insertProduct(p: Prodotto?, isEdit: Boolean): Int {
        if (productItemDao == null) return -2
        val updateBuilder: UpdateBuilder<Prodotto, out Any>? = productItemDao!!.updateBuilder()
        return try {
            productItemDao!!.createIfNotExists(p)
            0
        } catch (e: SQLException) {
            e.printStackTrace()
            -1
        }
    }


    fun deleteProductByID(id: Int): Int {
        if (productItemDao == null) return -2
        val deleteBuilder: DeleteBuilder<Prodotto, out Any>? = productItemDao!!.deleteBuilder()
        return try {
            if (id >= 0) {
                deleteBuilder?.where()?.eq(INDEX, id)
            }
            deleteBuilder?.delete()
            0
        } catch (e: SQLException) {
            e.printStackTrace()
            -1
        }
    }

    fun deleteProductByExpirationTime(expirationTime: DateTime?): Int {
        if (productItemDao == null) return -2
        
        val deleteBuilder: DeleteBuilder<Prodotto, out Any>? = productItemDao!!.deleteBuilder()
        return try {
            deleteBuilder?.where()?.lt(END, DateTime.now())?.query()
                ?.forEach { prod: Prodotto ->
                    try {
                        deleteBuilder.where()?.eq(INDEX, prod.id)
                        deleteBuilder.delete()
                    } catch (e: SQLException) {
                        e.printStackTrace()
                    }
                }
            0
        } catch (e: SQLException) {
            e.printStackTrace()
            -1
        }
    }

    @Throws(SQLException::class)
    fun getAllProduct(): List<Prodotto?>? {
        return if (productItemDao == null) null else productItemDao!!.queryForAll()
    }

    @Throws(SQLException::class)
    fun getProdotto(id: Int): Prodotto? {
        return if (productItemDao == null) null else productItemDao!!.queryBuilder().where()
            .eq(INDEX, id).query()[0]
    }

    @Throws(SQLException::class)
    fun getAllNonExpiredProduct(): List<Prodotto>? {
        return if (productItemDao == null) return null else productItemDao!!
            .queryBuilder()
            .where()
            .ge(START, DateTime.now())
            .and()
            .ge(END, DateTime.now())
            .query()
    }




    @Throws(SQLException::class)
    fun updateProduct(p: Prodotto, isEdit: Boolean): Int {
        if (productItemDao == null) return -2
        val updateBuilder: UpdateBuilder<Prodotto, out Any>? = productItemDao!!.updateBuilder()
        updateBuilder?.where()?.eq(INDEX, p.id)
        updateBuilder?.updateColumnValue(NAME, p.name)
        updateBuilder?.updateColumnValue(PRICE, p.price)
        updateBuilder?.updateColumnValue(IMG, p.imageBytes)
        updateBuilder?.updateColumnValue(START, p.startTime)
        updateBuilder?.updateColumnValue(END, p.endTime)
        updateBuilder?.update()
        return 0
    }


}