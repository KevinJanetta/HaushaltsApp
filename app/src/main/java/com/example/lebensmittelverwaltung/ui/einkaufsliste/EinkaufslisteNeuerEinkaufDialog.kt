package com.example.lebensmittelverwaltung.ui.einkaufsliste

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.example.lebensmittelverwaltung.R
import kotlinx.android.synthetic.main.activity_einkaufsliste_neuer_einkauf.*

class EinkaufslisteNeuerEinkaufDialog (context: Context, var addDialogListener: AddDialogListener) : AppCompatDialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_einkaufsliste_neuer_einkauf)


        btnVorratProdukthinzu.setOnClickListener {
            var name = etNameEinkauf.text.toString()    //Name aus dem Dialogfenster wird einer variablen zugeordnet
            addDialogListener.onAddButtonClickedEinkauf(name)   //Name wird dem Listener übergeben
            dismiss()
        }


        btnNeuerEinkaufAbbrechen.setOnClickListener {
            dismiss()
        }

    }
}