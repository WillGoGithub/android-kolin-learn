package com.example.will.iotest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper: SQLiteOpenHelper {
    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : super(
        context,
        name,
        factory,
        version
    )

    private val createTable =
        "CREATE TABLE `sakura` (Id INTEGER PRIMARY KEY AUTOINCREMENT, CustName TEXT, Tel TEXT, Birthday DATE);"

    private val createTable_Order =
        "CREATE TABLE `skOrder` (Id INTEGER PRIMARY KEY AUTOINCREMENT, SID INTEGER, ProdName TEXT, QTY INTEGER);"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(createTable_Order)
    }
}