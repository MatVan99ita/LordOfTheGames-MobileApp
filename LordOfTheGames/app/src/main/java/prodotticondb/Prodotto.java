package prodotticondb;


import androidx.annotation.NonNull;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.DateTime;

import java.util.Arrays;

@DatabaseTable(tableName = "prodotto")
public class Prodotto {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private double price;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] imageBytes;

    @DatabaseField
    private DateTime startTime;

    @DatabaseField
    private DateTime endTime;

    public Prodotto() {

    }

    public Prodotto(int id, String name, double price, byte[] imageBytes, DateTime startTime, DateTime endTime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageBytes = imageBytes;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    @NonNull
    @Override
    public String toString() {
        return "Prodotto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageBytes=" + Arrays.toString(imageBytes) +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
