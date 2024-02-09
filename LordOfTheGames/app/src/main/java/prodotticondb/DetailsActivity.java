package prodotticondb;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class DetailsActivity extends AppCompatActivity {
/*

    ActivityDetailsBinding binding;

    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        bundle = savedInstanceState;
        setContentView(binding.getRoot());

        this.init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.init();
    }

    private void init() {
        int id = getIntent().getIntExtra("product_index", -1);
        int mod = getIntent().getIntExtra("modifica", -1);
        Log.i("MOD", String.valueOf(mod));
        if(id <= 0) { // new product
            new Utilities().insertFragment(this, new EditFragment(), binding.fragmentContainerViewTag.getId(), EditFragment.class.getSimpleName(), null);
        } else { // detail mode -> get product from db with id

            Bundle bundle = new Bundle();
            bundle.putInt("product_index", id);

            if(mod < 0) {
                new Utilities().insertFragment(this, new DetailsFragment(), binding.fragmentContainerViewTag.getId(), DetailsFragment.class.getSimpleName(), bundle);
            } else {
                new Utilities().insertFragment(this, new EditFragment(), binding.fragmentContainerViewTag.getId(), EditFragment.class.getSimpleName(), bundle);
            }

        }

    }

*/



}
