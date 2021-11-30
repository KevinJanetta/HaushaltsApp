package com.example.lebensmittelverwaltung.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName ="einkaeufe") //Deklarierung einer Entität und den Namen der Tabelle
data class Einkaeufe (
    @ColumnInfo(name= "einkauf_name") //Name des Attributs vergeben
    var name: String,

    @ColumnInfo(name= "gesamtpreis")
    var gesamtpreis : Double = 0.00
        ) {


    @PrimaryKey(autoGenerate = true) // Primärschlüssel im Sinne einer ID, die automatisch generiert wird
    var id: Int? = null


}