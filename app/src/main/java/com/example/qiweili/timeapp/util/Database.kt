package com.example.qiweili.timeapp.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.qiweili.timeapp.timer.City
import com.example.qiweili.timeapp.timer.clocker
import com.example.qiweili.timeapp.timer.listCities
import com.google.gson.GsonBuilder
import java.time.Clock

/**
 * Created by qiweili on 2018/3/5.
 */
class DatabaseHelper: SQLiteOpenHelper {

    constructor(context: Context,DATABSE_NAME : String) : super(context,DATABSE_NAME,null,1){

    }

    val TABLE_NAME = "TimerTable"
    val listCities = "city"
    val clockers = "timezone"
    val id = "ID"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE " + TABLE_NAME + "(${listCities} TEXT PRIMARY KEY,${clockers} TEXT, ${id} REAL)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        //db?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
    }

    fun insertData(mcity : String, mTimeZone : String, mID : Int) : Boolean{
        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(listCities,mcity)
        contentValue.put(clockers,mTimeZone)
        contentValue.put(id,mID)
        val success = db.insert(TABLE_NAME,null,contentValue).toInt()
        return success != -1

    }

    fun getCities(): MutableList<MutableList<String>> {
        val db = this.writableDatabase
        val projection = arrayOf(clockers)
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME",null)

        val itemIds = mutableListOf<MutableList<String>>()
        if(cursor != null && cursor.count > 0){
            if(cursor.moveToFirst()){
                do{
                    val info = mutableListOf<String>(cursor.getString(0),cursor.getInt(2).toString())
                    itemIds.add(info)
                }while (cursor.moveToNext())
            }
        }

        return itemIds
    }

    fun getClockers(): List<clocker> {
        val db = this.writableDatabase
        val projection = arrayOf(clockers)
        val cursor = db.rawQuery("SELECT $clockers FROM $TABLE_NAME",null)

        val itemIds = mutableListOf<String>()
        if(cursor != null && cursor.count > 0){
            if(cursor.moveToFirst()){
                do{
                    itemIds.add(cursor.getString(0))
                }while (cursor.moveToNext())
            }
        }

        val clockers = mutableListOf<clocker>()

        for( i in itemIds){
            clockers.add(clocker(i,null,null))
        }
        return clockers
    }
    private class cityInfo(val timezone : String)

}