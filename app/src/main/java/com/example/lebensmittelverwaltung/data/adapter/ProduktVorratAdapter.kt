package com.example.lebensmittelverwaltung.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lebensmittelverwaltung.R
import com.example.lebensmittelverwaltung.data.db.entities.ProduktVorrat
import com.example.lebensmittelverwaltung.ui.einkaufsliste.ViewModel
import kotlinx.android.synthetic.main.produkt_vorratsliste.view.*

class ProduktVorratAdapter (
    var items : List<ProduktVorrat>,
    private val viewModel: ViewModel
        ) : RecyclerView.Adapter<ProduktVorratAdapter.ProduktViewholder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduktViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.produkt_vorratsliste,parent, false) //Vorher angefertigte Layout übergeben, wie ein Produkt in der Liste aussehen soll
        return ProduktViewholder(view)
    }

    override fun onBindViewHolder(holder: ProduktViewholder, position: Int) {
        val curProdukt = items[position]                            //Produkt identifizieren

        holder.itemView.tvEinkaeufeNameEinkauf.text = curProdukt.name // Produktinformationen den Textviews zuweisen
        holder.itemView.tvPreisEinkauf.text = "${curProdukt.anzahl}"
        holder.itemView.tvAblaufdatum.text = curProdukt.datum

        holder.itemView.ivdelete.setOnClickListener {
            viewModel.deleteVorrat(curProdukt)          //Beim klick auf den ImageView wird das Produkt gelöscht
        }
    }

    override fun getItemCount(): Int {
        return items.size               //länge der Liste
    }

    inner class ProduktViewholder(itemView : View) : RecyclerView.ViewHolder(itemView) // Erstellen des Viewholders
}