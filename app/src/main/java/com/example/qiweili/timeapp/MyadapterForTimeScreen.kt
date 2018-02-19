package com.example.qiweili.timeapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.cardview.view.*
import java.util.*

/**
 * Created by qiweili on 2018/2/18.
 */
class MyadapterForTimeScreen(time : clocker) : RecyclerView.Adapter<TimeScreen.ViewHolder>() {

    val time = time
    override fun getItemCount(): Int {
        return 2;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TimeScreen.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.cardview, parent, false)
        return TimeScreen.ViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: TimeScreen.ViewHolder?, position: Int) {
        val chicago = time.time_chicago
        val beijing = time.time_china
        if(position == 0){
            holder?.view?.timezone?.text = "Chicago"
            holder?.view?.time?.text = chicago
            if (time.chicago_daytime){
                holder?.view?.cardview_background?.setBackgroundResource(R.drawable.daytime)
            }else{
                holder?.view?.cardview_background?.setBackgroundResource(R.drawable.nightime)
            }
        }else{
            holder?.view?.timezone?.text = "Beijing"
            holder?.view?.time?.text = beijing
            if (time.china_daytime){
                holder?.view?.cardview_background?.setBackgroundResource(R.drawable.daytime)
            }else{
                holder?.view?.cardview_background?.setBackgroundResource(R.drawable.nightime)
            }

        }

    }
}