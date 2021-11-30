package com.example.lebensmittelverwaltung.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lebensmittelverwaltung.data.db.entities.ProduktVorrat


@Dao
interface VorratsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)        //Insert, aber bei vorhandenem Objekt wird es geupdatet
    suspend fun upsertVorrat(produkt: ProduktVorrat)        //suspend fun, welche für Corountines benötigt wird

    @Delete
    suspend fun deleteVorrat(produkt: ProduktVorrat)        // Löschen eines einzelnen Produktes

    @Delete
    suspend fun deleteAllVorrat(list : List<ProduktVorrat>) // Löschen der ganzen Liste

    @Query("SELECT * FROM vorrat_produkt")              //SQL-Anweisung um ganze Tabelle zu erhalten
    fun getAllProdukteVorratsliste() : LiveData<List<ProduktVorrat>>
}