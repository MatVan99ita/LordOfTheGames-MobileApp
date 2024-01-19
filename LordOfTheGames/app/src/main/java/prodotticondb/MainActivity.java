package prodotticondb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.prodotticondb.databinding.ActivityMainBinding;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemListener {

    private ActivityMainBinding binding;

    private List<Prodotto> prodotti;

    private ConnectionSource connectionSource;

    private Dao<Prodotto, Long> productDao;

    private ProductDB productDB;

    private ProductAdapter productAdapter;
    private List<Prodotto> product_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            this.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws SQLException {
        Log.i("BANANA", DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").withZone(DateTimeZone.forID("Europe/Rome")).print(DateTime.now()));

        binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        product_list = ProductManager.getINSTANCE(this).getAllNonExpiredProduct();
        if(product_list.size() > 0) {

            productAdapter = new ProductAdapter(product_list, this);
            binding.recyclerview.setAdapter(productAdapter);
        }

        binding.addFab.setOnClickListener(v -> {
            this.startActivity( new Intent(getApplicationContext(), DetailsActivity.class) );
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            restartInit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void restartInit() throws SQLException {
        product_list = ProductManager.getINSTANCE(this).getAllNonExpiredProduct();
        if(product_list.size() > 0) {
            productAdapter = new ProductAdapter(product_list, this);
            binding.recyclerview.setAdapter(productAdapter);
        }
    }

    @Override
    public void onItemClick(@NonNull View view, int position) {
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        Log.i("LOD", String.valueOf(product_list.get(position).getId()));
        intent.putExtra("product_index", product_list.get(position).getId()); // <- modalità edit
        this.startActivity(intent);
    }



}