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
    private val context: Context? = null
    private val INSTANCE: ProductManager? = null
    private var productDBHelper: LotgDB_orm? = null

    private var productItemDao: Dao<Prodotto, *>? = null

    init {
        initializeAll()
    }

    private fun initializeAll() {
        productItemDao = productDBHelper?.productDao;

    }


    fun getINSTANCE(con: Context?): ProductManager? {
        return if (ProductManager.INSTANCE == null) ProductManager(con!!).also {
            ProductManager.INSTANCE = it
        } else ProductManager.INSTANCE
    }

    fun releaseDB() {
        if (productDBHelper != null) {
            OpenHelperManager.releaseHelper()
            productDBHelper = null
            ProductManager.INSTANCE = null
        }
    }

    fun clearAllData(): Int {
        return if (productDBHelper == null) -1 else try {
            productDBHelper.clearTable()
            0
        } catch (e: SQLException) {
            e.printStackTrace()
            -1
        }
    }

    fun insertProduct(p: Prodotto?, isEdit: Boolean): Int {
        if (productItemDao == null) return -2
        val updateBuilder: UpdateBuilder<Prodotto, Long> = productItemDao!!.updateBuilder()
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
        val deleteBuilder: DeleteBuilder<Prodotto, Long> = productItemDao!!.deleteBuilder()
        return try {
            if (id >= 0) {
                deleteBuilder.where().eq(ProductManager.INDEX, id)
            }
            deleteBuilder.delete()
            0
        } catch (e: SQLException) {
            e.printStackTrace()
            -1
        }
    }

    fun deleteProductByExpirationTime(expirationTime: DateTime?): Int {
        if (productItemDao == null) return -2
        val deleteBuilder: DeleteBuilder<Prodotto, Long> = productItemDao!!.deleteBuilder()
        return try {
            val t = deleteBuilder.where().lt(ProductManager.END, DateTime.now()).query()
            t.forEach(Consumer<Prodotto> { prodotto: Prodotto ->
                try {
                    deleteBuilder.where().eq(ProductManager.INDEX, prodotto.getId())
                    deleteBuilder.delete()
                } catch (e: SQLException) {
                    e.printStackTrace()
                    return@forEach
                }
            })
            0
        } catch (e: SQLException) {
            e.printStackTrace()
            -1
        }
    }


    @Throws(SQLException::class)
    fun checkTime(): String? {
        return productItemDao.queryRaw("SELECT strftime('%s', 'now');", null).toString()
    }


    @Throws(SQLException::class)
    fun getAllProduct(): List<Prodotto?>? {
        return if (productItemDao == null) null else productItemDao!!.queryForAll()
    }

    @Throws(SQLException::class)
    fun getProdotto(id: Int): Prodotto? {
        return if (productItemDao == null) null else productItemDao!!.queryBuilder().where()
            .eq(ProductManager.INDEX, id).query()[0]
    }

    @Throws(SQLException::class)
    fun getAllNonExpiredProduct(): List<Prodotto>? {
        if (productItemDao == null) return null
        val queryBuilder: QueryBuilder<Prodotto, Long> = productItemDao!!.queryBuilder()
        return queryBuilder.where().ge(ProductManager.START, DateTime.now()).and()
            .ge(ProductManager.END, DateTime.now()).query()
    }


    @Throws(SQLException::class)
    fun updateProduct(
        id: Int,
        nome: Optional<String?>,
        price: Optional<Double?>,
        img: Optional<ByteArray?>,
        start: Optional<DateTime?>,
        end: Optional<DateTime?>
    ): Int {
        if (productItemDao == null) return -2
        val updateBuilder: UpdateBuilder<Prodotto, Long> = productItemDao!!.updateBuilder()
        updateBuilder.where().eq(ProductManager.INDEX, id)
        if (nome.isPresent) updateBuilder.updateColumnValue(ProductManager.NAME, nome.get())
        if (price.isPresent) updateBuilder.updateColumnValue(ProductManager.PRICE, price.get())
        if (img.isPresent) updateBuilder.updateColumnValue(ProductManager.IMG, img.get())
        if (start.isPresent) updateBuilder.updateColumnValue(ProductManager.START, start.get())
        if (end.isPresent) updateBuilder.updateColumnValue(ProductManager.END, end.get())
        updateBuilder.update()
        return 0
    }

    @Throws(SQLException::class)
    fun updateProduct(p: Prodotto, isEdit: Boolean): Int {
        if (productItemDao == null) return -2
        val updateBuilder: UpdateBuilder<Prodotto, Long> = productItemDao!!.updateBuilder()
        updateBuilder.where().eq(ProductManager.INDEX, p.getId())
        updateBuilder.updateColumnValue(ProductManager.NAME, p.getName())
        updateBuilder.updateColumnValue(ProductManager.PRICE, p.getPrice())
        updateBuilder.updateColumnValue(ProductManager.IMG, p.getImageBytes())
        updateBuilder.updateColumnValue(ProductManager.START, p.getStartTime())
        updateBuilder.updateColumnValue(ProductManager.END, p.getEndTime())
        updateBuilder.update()
        return 0
    }


}