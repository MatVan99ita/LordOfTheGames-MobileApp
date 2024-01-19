package com.example.lordofthegames.ORM

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import org.joda.time.DateTime
import java.util.Arrays


@DatabaseTable(tableName = "prodotto")
class Prodotto (
    @DatabaseField(generatedId = true)
    var id: Int = 0,

    @DatabaseField
    var name: String? = null,

    @DatabaseField
    var price: Double = 0.0,

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    var imageBytes: ByteArray,

    @DatabaseField
    var startTime: DateTime? = null,

    @DatabaseField
    var endTime: DateTime? = null,
)
{
    override fun toString(): String {
        return "Prodotto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageBytes=" + Arrays.toString(imageBytes) +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}'
    }
}

