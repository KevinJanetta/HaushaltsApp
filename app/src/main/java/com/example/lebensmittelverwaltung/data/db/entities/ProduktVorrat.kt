package com.example.lebensmittelverwaltung.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DateFormat
import java.util.*


@Entity(tableName = "vorrat_produkt")       //Deklarierung einer Entität und den Namen der Tabelle
data class ProduktVorrat(
    @ColumnInfo(name = "vprodukt_name") //Name des Attributs vergeben
    var name :String,
    @ColumnInfo(name = "vprodukt_anzahl")
    var anzahl : Int,
    @ColumnInfo(name = "vprodukt_datum")
    var datum : String?,
    @ColumnInfo(name = "vprodukt_expiringSoon")
    var expiringSoon : Boolean? = false
) : Serializable {
    @PrimaryKey(autoGenerate = true)     // Primärschlüssel im Sinne einer ID, die automatisch generiert wird
    var id : Int? = null

}