package alzaichsank.com.aplikasifootbalmatchschedule.view.detailteam.player.adapter

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.PlayerItem
import alzaichsank.com.aplikasifootbalmatchschedule.system.adapter.BaseAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_player_list.view.*

class PlayerAdapter(private val onClickListener: (position: Int) -> Unit) : BaseAdapter<RecyclerView.ViewHolder, PlayerItem>() {


    companion object {
        const val TRANSACTION_ITEM_TYPE = 1
    }

    init {
        setHasStableIds(true)
    }

    private var data: MutableList<PlayerItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_player_list, parent, false)
        return ProductViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> holder.bindData(data[position], position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TRANSACTION_ITEM_TYPE
    }

    override fun addAllData(data: MutableList<PlayerItem>) {
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun addData(data: PlayerItem) {
        this.data.add(data)
        this.notifyDataSetChanged()
    }

    override fun getDataAt(position: Int): PlayerItem {
        return data[position]
    }

    override fun getAllData(): MutableList<PlayerItem> {
        return data
    }

    override fun setData(data: MutableList<PlayerItem>) {

        this.data = data
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}

class ProductViewHolder(viewItem: View, onClickListener: (position: Int) -> Unit) : RecyclerView.ViewHolder(viewItem) {
    init {
        viewItem.setOnClickListener {
            onClickListener(adapterPosition)
        }

    }

    fun bindData(data: PlayerItem, position: Int) {
        if(data.strCutout  == null){
            Picasso.with(itemView.context)
                    .load(data.strThumb)
                    .placeholder(R.drawable.ic_no_data)
                    .error(R.drawable.ic_no_data)
                    .into(itemView.imges_Player)
        }else{
            Picasso.with(itemView.context)
                    .load(data.strCutout)
                    .placeholder(R.drawable.ic_no_data)
                    .error(R.drawable.ic_no_data)
                    .into(itemView.imges_Player)
        }
        itemView.position_Player.text = data.strPosition
        itemView.name_Player.text = data.strPlayer


    }
}