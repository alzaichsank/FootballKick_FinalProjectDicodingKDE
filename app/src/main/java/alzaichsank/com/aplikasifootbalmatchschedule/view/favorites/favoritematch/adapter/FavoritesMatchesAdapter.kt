package alzaichsank.com.aplikasifootbalmatchschedule.view.favorites.favoritematch.adapter

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.system.adapter.BaseAdapter
import alzaichsank.com.aplikasifootbalmatchschedule.utils.DateTime
import android.content.Intent
import android.provider.CalendarContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_match.view.*
import java.util.concurrent.TimeUnit

class FavoritesMatchesAdapter(private val menuItem : Int,private val onClickListener: (position: Int) -> Unit) : BaseAdapter<RecyclerView.ViewHolder, EventsItem>() {


    companion object {
        const val TRANSACTION_ITEM_TYPE = 1
    }

    init {
        setHasStableIds(true)
    }

    private var data: MutableList<EventsItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_match, parent, false)
        return ProductViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> holder.bindData(data[position], position, menuItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TRANSACTION_ITEM_TYPE
    }

    override fun addAllData(data: MutableList<EventsItem>) {
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun addData(data: EventsItem) {
        this.data.add(data)
        this.notifyDataSetChanged()
    }

    override fun getDataAt(position: Int): EventsItem {
        return data[position]
    }

    override fun getAllData(): MutableList<EventsItem> {
        return data
    }

    override fun setData(data: MutableList<EventsItem>) {

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

    fun bindData(data: EventsItem, position: Int, menuData : Int) {
        when (menuData){
            1-> {
                itemView.date.text = DateTime.getLongDate(data.dateEvent)
                if (data.strTime.equals("00:00:00+00:00")) {
                    itemView.time.text = "-"
                } else {
                    itemView.time.text = DateTime.getBasicTime(DateTime.toGMTFormat(data.dateEvent.toString(), data.strTime.toString()))
                }
                itemView.ID_HOME_TEAM.text = data.strHomeTeam
                itemView.ID_HOME_SCORE.visibility = View.GONE
                itemView.ID_AWAY_TEAM.text = data.strAwayTeam
                itemView.ID_AWAY_SCORE.visibility = View.GONE
                itemView.eventCalender.visibility = View.VISIBLE
            }
            2->{
                if (data.strTime.equals("00:00:00+00:00")) {
                    itemView.time.text = "-"
                } else {
                    itemView.time.text = DateTime.getBasicTime(DateTime.toGMTFormat(data.dateEvent.toString(), data.strTime.toString()))
                }
                itemView.ID_HOME_TEAM.text = data.strHomeTeam
                if (data.intHomeScore.equals("null")) {
                    itemView.ID_HOME_SCORE.text = "-"
                } else {
                    itemView.ID_HOME_SCORE.text = data.intHomeScore
                }
                itemView.ID_AWAY_TEAM.text = data.strAwayTeam
                if (data.intAwayScore.equals("null")) {
                    itemView.ID_AWAY_SCORE.text = "-"
                } else {
                    itemView.ID_AWAY_SCORE.text = data.intAwayScore
                }
                itemView.eventCalender.visibility = View.GONE
                itemView.ID_HOME_SCORE.visibility = View.VISIBLE
                itemView.ID_AWAY_SCORE.visibility = View.VISIBLE
            }
            3->{
                if (data.strTime.equals("00:00:00+00:00")) {
                    itemView.time.text = "-"
                } else {
                    itemView.time.text = DateTime.getBasicTime(DateTime.toGMTFormat(data.dateEvent.toString(), data.strTime.toString()))
                }
                itemView.ID_HOME_TEAM.text = data.strHomeTeam
                if (data.intHomeScore.equals("null")) {
                    itemView.ID_HOME_SCORE.visibility = View.GONE
                } else {
                    itemView.ID_HOME_SCORE.visibility = View.VISIBLE
                    itemView.ID_HOME_SCORE.text = data.intHomeScore
                }
                itemView.ID_AWAY_TEAM.text = data.strAwayTeam
                if (data.intAwayScore.equals("null")) {
                    itemView.ID_AWAY_SCORE.visibility = View.GONE
                } else {
                    itemView.ID_AWAY_SCORE.visibility = View.VISIBLE
                    itemView.ID_AWAY_SCORE.text = data.intAwayScore
                }
                itemView.eventCalender.visibility = View.GONE
            } else ->{
            itemView.date.text = DateTime.getLongDate(data.dateEvent)
            itemView.time.text = "-"
            itemView.eventCalender.visibility = View.GONE
            itemView.ID_HOME_TEAM.text = ""
            itemView.ID_HOME_SCORE.text = ""
            itemView.ID_AWAY_TEAM.text = ""
            itemView.ID_AWAY_SCORE.text = ""
        }
        }
        if(menuData==1) {
            itemView.eventCalender.setOnClickListener {
                val matchTitle = "${data.strHomeTeam} VS ${data.strAwayTeam}"
                val intent = Intent(Intent.ACTION_EDIT)
                intent.type = "vnd.android.cursor.item/event"
                val startTime = DateTime.dateTimeToFormat(data.dateEvent, data.strTime)
                val endTime = startTime + TimeUnit.MINUTES.toMillis(90)
                intent.putExtra(CalendarContract.Events.TITLE, matchTitle)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                        .putExtra(CalendarContract.Events.ALL_DAY, false)
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Reminder from Football Kick")
                itemView.context.startActivity(intent)
            }
        }
    }
}
