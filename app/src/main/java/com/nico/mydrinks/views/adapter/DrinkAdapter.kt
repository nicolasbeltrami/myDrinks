package com.nico.mydrinks.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nico.mydrinks.R
import com.nico.mydrinks.data.model.Drink
import kotlinx.android.synthetic.main.item_drink.view.*

class DrinkAdapter(private val context: Context, private val drinksList:List<Drink>, private val itemClickListener: OnDrinkClickListener) : RecyclerView.Adapter<BaseViewHolder<*>>(){

    interface OnDrinkClickListener {
        fun onDrinkClick(drink: Drink)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return DrinkViewHolder(LayoutInflater.from(context).inflate(R.layout.item_drink, parent, false))
    }

    override fun getItemCount() = drinksList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is DrinkViewHolder -> holder.bind(drinksList[position], position)
        }
    }

    inner class DrinkViewHolder(itemView: View): BaseViewHolder<Drink>(itemView) {
        override fun bind(item: Drink, position: Int) {
            Glide.with(context).load(item.image).centerCrop().into(itemView.imgDrink)
            itemView.tvDrinkName.text = item.name
            itemView.tvDrinkDescription.text = item.description
            itemView.setOnClickListener { itemClickListener.onDrinkClick(item) }
        }
    }
}