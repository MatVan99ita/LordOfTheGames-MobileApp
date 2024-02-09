package prodotticondb;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;

public class EditFragment/* extends Fragment implements DatePickerDialog.OnDateSetListener*/{
/*
    //FragmentEditBinding binding;
    DateTime start;
    DateTime end;
    private Integer id;
    private ConnectionSource connectionSource;
    private Dao<Prodotto, Long> productDao;
    private ProductDB productDB;
    private Prodotto prodotto;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy").withZone(DateTimeZone.forID("Europe/Rome"));
    private Pair<DateTime, DateTime> timestamp;
    private DatePickerDialog datePickerDialog;
    private Boolean isStartClicked = false;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditBinding.inflate(inflater, container, false);
        bundle = savedInstanceState;
        timestamp = new Pair<>(null, null);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){
            id = getArguments().getInt("product_index", -1);
        } else {
            id = -1;
        }

        if(id > 0){
            try {
                prodotto = ProductManager.getINSTANCE(requireContext()).getProdotto(id);
                if(prodotto != null){
                    binding.detailEditName.setText(prodotto.getName());
                    binding.detailEditPrice.setText(prodotto.getPrice() + "");
                    binding.lblStart.setText(dateTimeFormatter.print(prodotto.getStartTime()));
                    binding.lblEnd.setText(dateTimeFormatter.print(prodotto.getEndTime()));
                    timestamp= new Pair<>(prodotto.getStartTime(), prodotto.getEndTime());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //get element from id

        } else {
            binding.btnEditDataFine.setEnabled(false);
        }

        binding.btnEditDataInizio.setOnClickListener(v -> {
            isStartClicked = true;
            if(!timestamp.isPairNull()){
                datePickerDialog = new DatePickerDialog(requireContext(), this, timestamp.getX().getYear(), timestamp.getX().getMonthOfYear(), timestamp.getX().getDayOfMonth());
            } else {
                datePickerDialog = new DatePickerDialog(requireContext(), this, DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth());
            }
            //Sceglie la data
            datePickerDialog.show();
            //poi viene richiamato onDateSet automaticamente credo
        });

        binding.btnEditDataFine.setOnClickListener(v -> {
            if(!timestamp.isPairNull()){
                datePickerDialog = new DatePickerDialog(requireContext(), this, timestamp.getY().getYear(), timestamp.getY().getMonthOfYear(), timestamp.getY().getDayOfMonth());
            } else {
                datePickerDialog = new DatePickerDialog(requireContext(), this, DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth());
            }
            datePickerDialog.show();
        });

        binding.detailEditSave.setOnClickListener(v -> {
            //salva il prodotto
            if(
                    binding.detailEditName.getText().length() > 0
                            &&
                            (
                                    binding.detailEditPrice.length()>0
                                    && Double.parseDouble(binding.detailEditPrice.getText().toString()) >= 0.0
                            ) &&
                            ! timestamp.isPairNull()
            ){
                if(id < 0){
                    ProductManager.getINSTANCE(requireContext()).insertProduct(
                            new Prodotto(
                                    0,
                                    binding.detailEditName.getText().toString(),

                                    Double.parseDouble(binding.detailEditPrice.getText().toString()),

                        *//*binding.btnEditImg.getBackground() != null ?
                                getImageForDB(requireContext(), binding.btnEditImg.getBackground())
                                :
                                *//*
                                    getImageForDB(requireContext(), R.drawable.ic_launcher_foreground),
                                    timestamp.getX(), timestamp.getY()
                            ),
                            true
                    );
                } else {
                    try {
                        ProductManager.getINSTANCE(requireContext()).updateProduct(
                                new Prodotto(
                                        id,
                                        binding.detailEditName.getText().toString(),

                                        Double.parseDouble(binding.detailEditPrice.getText().toString()),

                            *//*binding.btnEditImg.getBackground() != null ?
                                    getImageForDB(requireContext(), binding.btnEditImg.getBackground())
                                    :
                                    *//*
                                        getImageForDB(requireContext(), R.drawable.baseline_add_to_photos_24),
                                        timestamp.getX(), timestamp.getY()
                                ),
                                true
                        );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        binding.detailEditCancel.setOnClickListener(v -> {
            //dovrebbe tornare alla home
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        DateTime temp = new DateTime(year, month+1, dayOfMonth, DateTime.now().getHourOfDay(), DateTime.now().getMinuteOfHour());
        String printedTemp = dateTimeFormatter.print(temp);
        Log.i("IO PUO'", dateTimeFormatter.print(temp));

        if(isStartClicked){

            if(!timestamp.isXNull()) {

                if(temp.isBefore(timestamp.getY())) {
                    timestamp.setX(dateTimeFormatter.parseDateTime(printedTemp));
                    binding.lblStart.setText(dateTimeFormatter.print(timestamp.getX()));
                } else {
                    LaunchError("Starting date cannot be greater than end");
                }

            } else {
                timestamp.setX(dateTimeFormatter.parseDateTime(printedTemp));
                binding.lblStart.setText(dateTimeFormatter.print(temp));
            }
            if(!binding.btnEditDataFine.isEnabled()) binding.btnEditDataFine.setEnabled(!binding.btnEditDataFine.isEnabled());
            isStartClicked = !isStartClicked;
        } else {
            if(!timestamp.isYNull()) {

                if(temp.isAfter(timestamp.getX()) && temp.isAfter(DateTime.now())) {
                    timestamp.setY(dateTimeFormatter.parseDateTime(printedTemp));
                    binding.lblEnd.setText(dateTimeFormatter.print(timestamp.getY()));
                } else {
                    LaunchError("Ending date cannot be less than starting date or now");
                }
            } else {
                timestamp.setY(dateTimeFormatter.parseDateTime(printedTemp));
                binding.lblEnd.setText(dateTimeFormatter.print(temp));
            }
        }
    }


    private void LaunchError(String msg){

    // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

     // 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage(msg).setTitle("Error").setPositiveButton("ok", (dialog, which) -> dialog.cancel());

    // 3. Get the AlertDialog.
        AlertDialog dialog = builder.create();

    // 4. Show dialog
        dialog.show();
    }

    private byte[] getImageForDB(@NonNull Context context, @DrawableRes int res){
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), res, null);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }*/
}
