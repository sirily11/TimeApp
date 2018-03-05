package com.example.qiweili.timeapp.timer

import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import com.example.qiweili.timeapp.timer.clocker

/**
 * Created by qiweili on 2018/3/5.
 */
class timeShow(val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, var clocks: List<clocker>?) : AsyncTask<Void, Void, Boolean>(){
    override fun doInBackground(vararg p0: Void?) : Boolean {
        updateTime()
        return true
    }

    fun updateTime() {
        for(clock in clocks!!) {
            clock.update()
            adapter.notifyDataSetChanged()
        }
        Thread.sleep(1000)

    }
}