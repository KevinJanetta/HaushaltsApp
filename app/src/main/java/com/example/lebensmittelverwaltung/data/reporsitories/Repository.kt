package com.example.lebensmittelverwaltung.data.reporsitories

import com.example.lebensmittelverwaltung.data.db.EinkaufDatenbank
import com.example.lebensmittelverwaltung.data.db.entities.Einkaeufe
import com.example.lebensmittelverwaltung.data.db.entities.Produkt
import com.example.lebensmittelverwaltung.data.db.entities.ProduktVorrat

class Repository(                       // Enthält alle Funktionen der DAO's, greift dabei aber auf die Datenbank zu
    private val db : EinkaufDatenbank) {

    suspend fun upsert(produkt: Produkt) = db.getEinkaufsDao().upsert(produkt)

    suspend fun delete(produkt: Produkt) = db.getEinkaufsDao().delete(produkt)

    fun getAllProdukte () = db.getEinkaufsDao().getAllProdukteEinkaufsliste()

    suspend fun deleteAllProdukte(list : List<Produkt>) = db.getEinkaufsDao().deleteAll(list)

    suspend fun upsertVorrat(produkt: ProduktVorrat) = db.getVorratsDao().upsertVorrat(produkt)

    suspend fun deleteVorrat(produkt: ProduktVorrat) = db.getVorratsDao().deleteVorrat(produkt)

    suspend fun deleteAllVorrat(list : List<ProduktVorrat>) = db.getVorratsDao().deleteAllVorrat(list)

    fun getAllProdukteVorratsliste() = db.getVorratsDao().getAllProdukteVorratsliste()

    suspend fun upsertEinkauf(einkauf: Einkaeufe) = db.getEinkaeufeDao().upsertEinkauf(einkauf)

    suspend fun deleteEinkauf(einkauf: Einkaeufe) = db.getEinkaeufeDao().deleteEinkauf(einkauf)

    fun getAllEinkaeufe() = db.getEinkaeufeDao().getAllEinkaeufe()

}