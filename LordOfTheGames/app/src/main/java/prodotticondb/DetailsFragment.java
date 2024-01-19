package prodotticondb;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.prodotticondb.databinding.ActivityDetailsBinding;
import com.example.prodotticondb.databinding.FragmentDetailsBinding;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.SQLException;

public class DetailsFragment extends Fragment {

    FragmentDetailsBinding binding;
    ActivityDetailsBinding activityBinding;

    private ConnectionSource connectionSource;

    private Dao<Prodotto, Long> productDao;

    private ProductDB productDB;



    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy").withZone(DateTimeZone.forID("Europe/Rome"));

    Prodotto prodotto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        activityBinding = ActivityDetailsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            prodotto = ProductManager.getINSTANCE(requireContext()).getProdotto(getArguments().getInt("product_index"));
            binding.detailImg.setImageBitmap(BitmapFactory.decodeByteArray(prodotto.getImageBytes(), 0, prodotto.getImageBytes().length));
            binding.detailName.setText(prodotto.getName());
            binding.detailPrice.setText(String.valueOf(prodotto.getPrice()));
            binding.detailTime.setText("Dal " + DATE_TIME_FORMATTER.print(prodotto.getStartTime()) + " al " + DATE_TIME_FORMATTER.print(prodotto.getEndTime()));

            binding.detailModify.setOnClickListener(v -> {

                Bundle bundle = new Bundle();
                bundle.putInt("product_index", prodotto.getId());
                new Utilities().insertFragment(
                        ((DetailsActivity) getActivity()),
                        new EditFragment(),
                        activityBinding.fragmentContainerViewTag.getId(),
                        EditFragment.class.getSimpleName(),
                        bundle
                );


            });

            binding.detailDelete.setOnClickListener(v -> {
                ProductManager.getINSTANCE(requireContext()).deleteProductByID(getArguments().getInt("product_index"));
                //torna al main
            });


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
