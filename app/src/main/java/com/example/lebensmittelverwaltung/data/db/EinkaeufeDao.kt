package com.example.lebensmittelverwaltung.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lebensmittelverwaltung.data.db.entities.Einkaeufe
import com.example.lebensmittelverwaltung.data.db.entities.Produkt


@Dao
interface EinkaeufeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //Insert, aber bei vorhandenem Objekt wird es geupdatet
    suspend fun upsertEinkauf(einkauf: Einkaeufe)    //suspend fun, welche für Corountines benötigt wird

    @Delete
    suspend fun deleteEinkauf(einkauf: Einkaeufe)   //Deletefunktion

    @Query("SELECT * FROM einkaeufe") //SQL-Anweisung um ganze Tabelle zu erhalten
    fun getAllEinkaeufe() : LiveData<List<Einkaeufe>>
}