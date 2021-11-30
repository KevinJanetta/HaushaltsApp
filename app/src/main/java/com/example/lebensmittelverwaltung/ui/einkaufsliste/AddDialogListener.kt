package com.example.lebensmittelverwaltung.ui.einkaufsliste

import com.example.lebensmittelverwaltung.data.db.entities.Produkt
import com.example.lebensmittelverwaltung.data.db.entities.ProduktVorrat

interface AddDialogListener { // Listener um aus den Dialogfenstern die Produkte, oder einen String, der Aktivität zu übertragen
    fun onAddButtonClicked(produkt : Produkt)

    fun onAddButtonClickedVorrat(produktVorrat: ProduktVorrat)

    fun onAddButtonClickedEinkauf(name : String)
}