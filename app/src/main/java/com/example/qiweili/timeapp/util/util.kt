package com.example.qiweili.timeapp.util

import android.provider.BaseColumns


/**
 * Created by qiweili on 2018/3/5.
 */
class util() {
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "Cities list"
        const val COLUMN_NAME_TITLE = "clocker"
        const val COLUMN_NAME_TITLE2 = "cities"

    }

    companion object {
        fun createDataBase() {
            val SQL_CREATE_ENTRIES =
                    "CREATE TABLE ${FeedEntry.TABLE_NAME} (" +
                            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                            "${FeedEntry.COLUMN_NAME_TITLE} TEXT," +
                            "${FeedEntry.COLUMN_NAME_TITLE2} TEXT)"
            val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"
        }



    }


}