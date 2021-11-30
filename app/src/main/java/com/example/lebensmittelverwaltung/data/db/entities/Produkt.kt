package com.example.lebensmittelverwaltung.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName ="einkaufs_produkt")  //Deklarierung einer Entität und den Namen der Tabelle

data class Produkt (
    @ColumnInfo(name= "produkt_name") //Name des Attributs vergeben
    var name: String,
    @ColumnInfo(name = "produkt_anzahl")
    var anzahl: Int,
    @ColumnInfo(name = "produkt_preis")
    var preis: Double? = 0.00,
    @ColumnInfo(name = "produkt_gekauft")
    var gekauft : Boolean? = false


) : Serializable {
    @PrimaryKey(autoGenerate = true)     // Primärschlüssel im Sinne einer ID, die automatisch generiert wird
    var id: Int? = null
}