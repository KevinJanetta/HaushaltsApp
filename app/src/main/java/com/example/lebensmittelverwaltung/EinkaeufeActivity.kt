package com.example.lebensmittelverwaltung

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lebensmittelverwaltung.data.adapter.EinkaeufeAdapter
import com.example.lebensmittelverwaltung.data.db.EinkaufDatenbank
import com.example.lebensmittelverwaltung.data.reporsitories.Repository
import com.example.lebensmittelverwaltung.ui.einkaufsliste.EinkaufslisteActivity
import com.example.lebensmittelverwaltung.ui.einkaufsliste.ViewModel
import com.example.lebensmittelverwaltung.ui.einkaufsliste.ViewModelFactory
import com.example.lebensmittelverwaltung.ui.einkaufsliste.vorratsliste.VorratslisteActivity
import kotlinx.android.synthetic.main.activity_einkaufsliste.*
import java.io.ObjectStreamException

class EinkaeufeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_einkaeufe)

        val database = EinkaufDatenbank(this)
        val repository = Repository(database)
        val factory = ViewModelFactory(repository)


        val viewModel = ViewModelProviders.of(this, factory).get(ViewModel::class.java)

        val adapter = EinkaeufeAdapter(listOf(), viewModel)

        rvEinkaeufeListe.layoutManager = LinearLayoutManager(this)
        rvEinkaeufeListe.adapter = adapter

        viewModel.getAllEinkaeufe().observe(this, Observer {
            adapter.einkaeufe = it
            adapter.notifyDataSetChanged()
        })


        btnEinkaufsliste.setOnClickListener {   //Button zum starten der EinkaufslisteActivity
            Intent(this, EinkaufslisteActivity::class.java).also {
                startActivity(it)
            }
        }

        btnVorratsliste.setOnClickListener {    //Button zum starten der VorratslisteActivity
            Intent(this, VorratslisteActivity::class.java).also {
                startActivity(it)
            }
        }




    }

}