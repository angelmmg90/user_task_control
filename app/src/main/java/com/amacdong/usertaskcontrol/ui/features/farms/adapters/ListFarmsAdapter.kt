package com.amacdong.usertaskcontrol.ui.features.farms.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amacdong.data.model.FarmModel
import com.amacdong.usertaskcontrol.R
import kotlinx.android.synthetic.main.item_farm.view.*

class ListFarmsAdapter(
    val context: Context,
    private val listFarm: List<FarmModel>,
    private val clickListener: (FarmModel, View) -> Unit
    ) : RecyclerView.Adapter<ListFarmsAdapter.FarmItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FarmItemViewHolder {
        val inflatedView =
            LayoutInflater.from(context).inflate(R.layout.item_farm, parent, false)

        return FarmItemViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int = listFarm.size

    override fun onBindViewHolder(holder: FarmItemViewHolder, position: Int) {
        holder.bindFarmItem(listFarm[position], clickListener)
    }

    class FarmItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bindFarmItem(
            farmItem: FarmModel,
            clickListener: (FarmModel, View) -> Unit
        ) {
            itemView.tvFarmCategory.text = farmItem.category
            itemView.tvFarmItem.text = farmItem.item
            itemView.tvFarmName.text = farmItem.farmName
            itemView.tvFarmPhone.text = farmItem.phone1
            itemView.setOnClickListener {
                clickListener(farmItem, view)
            }
        }
    }
}
