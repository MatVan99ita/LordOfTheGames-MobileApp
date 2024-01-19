package prodotticondb;

import android.view.View;

import androidx.annotation.NonNull;

public interface OnItemListener {
    void onItemClick(@NonNull View view, int position);
}
