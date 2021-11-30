package com.example.lebensmittelverwaltung.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lebensmittelverwaltung.data.db.entities.Produkt
import com.example.lebensmittelverwaltung.data.db.entities.ProduktVorrat

@Dao
interface EinkaufsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)    //Insert, aber bei vorhandenem Objekt wird es geupdatet
    suspend fun upsert(produkt: Produkt)                //suspend fun, welche für Corountines benötigt wird

    @Delete
    suspend fun delete(produkt: Produkt)                // Löschen eines einzelnen Produktes

    @Delete
    suspend fun deleteAll(list : List<Produkt>)            // Löschen der ganzen Liste


    @Query("SELECT * FROM einkaufs_produkt")       //SQL-Anweisung um ganze Tabelle zu erhalten
    fun getAllProdukteEinkaufsliste() : LiveData<List<Produkt>>


}