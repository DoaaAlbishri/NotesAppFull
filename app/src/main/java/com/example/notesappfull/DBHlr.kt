package com.example.notesappfull

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHlr(context: Context?) : SQLiteOpenHelper(context, "details.db", null, 1) {
    val sql :SQLiteDatabase = writableDatabase

    override fun onCreate(p0: SQLiteDatabase?) {
        if (p0 != null) {
            p0.execSQL("create table notes(Note text)")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun savedata(s1: String){
        val cv = ContentValues()
        cv.put("Note", s1)
        sql.insert("notes", null, cv)
    }

    @SuppressLint("Range")
    fun retrive() :ArrayList<String> {
        val list = ArrayList<String>()
        val cursor: Cursor = sql.rawQuery("select * from notes", null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                val name: String = cursor.getString(cursor.getColumnIndex("Note"))
                list.add(name)
                cursor.moveToNext()
            }
        }
        return list
    }

    fun update(s1 :String , news1 :String){
        val cv = ContentValues()
        cv.put("Note", news1)
        sql.update("notes",  cv, "Note=?", arrayOf(s1))
        }

    fun delete(s1 :String){
        sql.delete("notes", "Note=?", arrayOf(s1))
    }
}
