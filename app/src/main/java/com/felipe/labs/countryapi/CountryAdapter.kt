package com.felipe.labs.countryapi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felipe.labs.countryapi.models.Country
import com.felipe.labs.countryapi.utils.ImageSvg
import com.squareup.picasso.Picasso

private const val TAG = "CountryAdapter"

class CountryAdapter(private val event: (Int) -> Unit): RecyclerView.Adapter<CountryAdapter.CountryVH>() {
    private var mItems = mutableListOf<Country>()

    inner class CountryVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nome = itemView.findViewById<TextView>(R.id.nameTextView)
        val capital = itemView.findViewById<TextView>(R.id.capitalTextView)
        val populacao = itemView.findViewById<TextView>(R.id.populacaoText)
        val flag = itemView.findViewById<ImageView>(R.id.flagImageView)
        fun bind(item: Country) {
            nome.text = "Name: ${item.name}"
            capital.text = "Capital: ${item.capital}"
            populacao.text = "Poulacao: ${item.population}"
            ImageSvg.loadUrlIntoImageView(item.imageUri().toString(), flag)
        }

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                Log.i(TAG, mItems[pos].name)
                //val item = mItems[pos]
                event(pos)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryVH {
        return parent.inflate()
    }

    override fun onBindViewHolder(holder: CountryVH, position: Int) {
        val item = mItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun ViewGroup.inflate(): CountryVH {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.country_item_row, this, false)
        return CountryVH(view)
    }

    fun submitLit(itens: List<Country>) {
        this.mItems.clear()
        this.mItems.addAll(itens)
        notifyDataSetChanged()
    }
}