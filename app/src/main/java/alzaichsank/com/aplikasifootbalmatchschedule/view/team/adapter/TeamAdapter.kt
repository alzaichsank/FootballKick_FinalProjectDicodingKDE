package alzaichsank.com.aplikasifootbalmatchschedule.view.team.adapter

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.TeamsListItem
import alzaichsank.com.aplikasifootbalmatchschedule.system.adapter.BaseAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_team.view.*

class TeamAdapter(private val onClickListener: (position: Int) -> Unit) : BaseAdapter<RecyclerView.ViewHolder, TeamsListItem>() {


    companion object {
        const val TRANSACTION_ITEM_TYPE = 1
    }

    init {
        setHasStableIds(true)
    }

    private var data: MutableList<TeamsListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_team, parent, false)
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

    override fun addAllData(data: MutableList<TeamsListItem>) {
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun addData(data: TeamsListItem) {
        this.data.add(data)
        this.notifyDataSetChanged()
    }

    override fun getDataAt(position: Int): TeamsListItem {
        return data[position]
    }

    override fun getAllData(): MutableList<TeamsListItem> {
        return data
    }

    override fun setData(data: MutableList<TeamsListItem>) {

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

    fun bindData(data: TeamsListItem, position: Int) {
        Picasso.with(itemView.context)
                .load(data.strTeamBadge)
                .placeholder(R.drawable.ic_no_data)
                .error(R.drawable.ic_no_data)
                .into(itemView.team_image)
        itemView.name_team.text = data.strTeam


    }
}