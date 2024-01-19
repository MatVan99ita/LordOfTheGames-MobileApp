package prodotticondb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class ProductDB extends OrmLiteSqliteOpenHelper {


    private static final String DB_NAME = "ProductB";
    private static final int DB_VERSION = 2;
    private Dao<Prodotto, ?> productDao;

    public ProductDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Prodotto.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

            if(isTableExisting(database, "prodotto"))
                TableUtils.dropTable(connectionSource, ProductDB.class, false);

            onCreate(database, connectionSource);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Prodotto, ?> getProductDao() throws SQLException {
        return productDao == null ? productDao = getDao(Prodotto.class) : productDao;
    }

    public boolean isTableExisting(SQLiteDatabase db, String tableName){
        Cursor cursor = db.rawQuery("SELECT DISTINCT name FROM sqlite_master WHERE type='table' AND name='"+ tableName + "'", null);
        if(cursor!=null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    @Override
    public void close() {
        productDao = null;
        super.close();
    }

    public void clearTable() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), ProductDB.class);
    }

}
