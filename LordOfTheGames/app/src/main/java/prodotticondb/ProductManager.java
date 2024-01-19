package prodotticondb;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class ProductManager {

    private static final String INDEX = "id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String IMG = "imageBytes";
    private static final String START = "startTime";
    private static final String END = "endTime";



    private final String TAG = ProductManager.this.getClass().getSimpleName();
    private final Context context;
    private static ProductManager INSTANCE;
    private ProductDB productDBHelper;

    private Dao<Prodotto, Long> productItemDao;
    public ProductManager(Context context) {
        this.context = context;
        this.productDBHelper = OpenHelperManager.getHelper(context, ProductDB.class);

        try {
            productItemDao = (Dao<Prodotto, Long>) productDBHelper.getProductDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static ProductManager getINSTANCE(Context con){
        return INSTANCE == null ? INSTANCE = new ProductManager(con) : INSTANCE;

    }

    public void releaseDB() {
        if(productDBHelper != null) {
            OpenHelperManager.releaseHelper();
            productDBHelper = null;
            INSTANCE = null;
        }
    }

    public int clearAllData(){
        if(productDBHelper == null) return -1;
        try {
            productDBHelper.clearTable();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int insertProduct(Prodotto p, boolean isEdit) {
        if(productItemDao == null) return -2;

        UpdateBuilder<Prodotto, Long> updateBuilder = productItemDao.updateBuilder();

        try {
            productItemDao.createIfNotExists(p);
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public int deleteProductByID(int id){
        if(productItemDao==null) return -2;

        DeleteBuilder<Prodotto, Long> deleteBuilder = productItemDao.deleteBuilder();
        try {
            if(id >= 0){
                deleteBuilder.where().eq(INDEX, id);
            }
            deleteBuilder.delete();
            return 0;

        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public int deleteProductByExpirationTime(DateTime expirationTime){

        if(productItemDao == null) return -2;

        DeleteBuilder<Prodotto, Long> deleteBuilder = productItemDao.deleteBuilder();

        try {

            List<Prodotto> t = deleteBuilder.where().lt(END, DateTime.now()).query();

            t.forEach(prodotto -> {
                try {

                    deleteBuilder.where().eq(INDEX, prodotto.getId());
                    deleteBuilder.delete();

                } catch (SQLException e) {
                    e.printStackTrace();
                    return;
                }
            });
            return 0;

        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }


    public String checkTime() throws SQLException {
        return productItemDao.queryRaw("SELECT strftime('%s', 'now');", null).toString();
    }


    public List<Prodotto> getAllProduct() throws SQLException {
        if (productItemDao == null) return null;
        return productItemDao.queryForAll();
    }

    public Prodotto getProdotto(int id) throws SQLException {
        if(productItemDao == null) return null;
        return productItemDao.queryBuilder().where().eq(INDEX, id).query().get(0);
    }

    public List<Prodotto> getAllNonExpiredProduct() throws SQLException {
        if (productItemDao == null) return null;
        QueryBuilder<Prodotto, Long> queryBuilder = productItemDao.queryBuilder();
        return queryBuilder.where().ge(START, DateTime.now()).and().ge(END, DateTime.now()).query();
    }


    public int updateProduct(int id, Optional<String> nome, Optional<Double> price, Optional<byte[]> img, Optional<DateTime> start, Optional<DateTime> end) throws SQLException {
        if(productItemDao == null) return -2;

        UpdateBuilder<Prodotto, Long> updateBuilder = productItemDao.updateBuilder();

        updateBuilder.where().eq(INDEX, id);

        if(nome.isPresent())    updateBuilder.updateColumnValue(NAME, nome.get());
        if(price.isPresent())   updateBuilder.updateColumnValue(PRICE, price.get());
        if(img.isPresent())     updateBuilder.updateColumnValue(IMG, img.get());
        if(start.isPresent())   updateBuilder.updateColumnValue(START, start.get());
        if(end.isPresent())     updateBuilder.updateColumnValue(END, end.get());

        updateBuilder.update();
        return 0;
    }

    public int updateProduct(Prodotto p, boolean isEdit) throws SQLException {
        if(productItemDao == null) return -2;

        UpdateBuilder<Prodotto, Long> updateBuilder = productItemDao.updateBuilder();

        updateBuilder.where().eq(INDEX, p.getId());

        updateBuilder.updateColumnValue(NAME, p.getName());
        updateBuilder.updateColumnValue(PRICE, p.getPrice());
        updateBuilder.updateColumnValue(IMG, p.getImageBytes());
        updateBuilder.updateColumnValue(START, p.getStartTime());
        updateBuilder.updateColumnValue(END, p.getEndTime());

        updateBuilder.update();
        return 0;
    }


}
