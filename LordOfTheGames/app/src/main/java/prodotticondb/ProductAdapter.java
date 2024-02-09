package prodotticondb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lordofthegames.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private List<Prodotto> prodottos;
    private OnItemListener listener;

    public ProductAdapter(List<Prodotto> prodottos, OnItemListener listener) {
        this.prodottos=new ArrayList<>();//.addAll(prodottos);
        this.prodottos.addAll(prodottos);
        this.listener = listener;
    }


    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Prodotto p = this.prodottos.get(position);

        byte[] img = p.getImageBytes();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        holder.img.setImageBitmap(bitmap);

        holder.nome.setText(p.getName());
        holder.price.setText(p.getPrice()+"€");
        holder.timestamp.setText("da: " + p.getStartTime() + " a: " + p.getEndTime());

    }

    public void updateProductList(List<Prodotto> prodotti){
        prodottos.clear();
        prodottos.addAll(prodotti);
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return prodottos.size();
    }

    public static class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView img;
        private TextView nome;
        private TextView price;
        private TextView timestamp;

        private OnItemListener onItemListener;


        public ProductHolder(@NonNull View itemView, OnItemListener listener) {
            super(itemView);
            this.onItemListener = listener;
            //img = itemView.findViewById(R.id.immagine);
            //nome = itemView.findViewById(R.id.product_name);
            //price = itemView.findViewById(R.id.product_price);
            //timestamp = itemView.findViewById(R.id.product_valid_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(itemView, getAdapterPosition());
        }
    }
}
