package com.example.pizzaapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.view.Menu
import android.widget.Toast
import com.example.pizzaapp.model.MenuModel
import java.io.ByteArrayOutputStream
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class DatabaseHelper (var context: Context): SQLiteOpenHelper(
    context,DATABASE_NAME,null,DATABASE_VERSION
) {
    companion object {
        private val DATABASE_NAME = "pizza"
        private val DATABASE_VERSION = 1

        //table name
        private val TABLE_ACCOUNT = "account"

        //column account table
        private val COLUMN_EMAIL = "email"
        private val COLUMN_NAME = "name"
        private val COLUMN_LEVEL = "level"
        private val COLUMN_PASSWORD = "password"

        //tabel menu
        private val TABLE_MENU = "menu"
        //coloumn menu tabel
        private val COLUMN_ID_MENU = "idMenu"
        private val COLUMN_NAMA_MENU = "menuName"
        private val COLUMN_PRICE_MENU = "price"
        private val COLUMN_IMAGE = "photo"
    }

    private val CREATE_ACCOUNT_TABLE = ("CREATE TABLE"+TABLE_ACCOUNT+"("
    + COLUMN_EMAIL + "TEXT PRIMARY KEY," + COLUMN_NAME + " TEXT,"
    +COLUMN_LEVEL + "TEXT,"+ COLUMN_PASSWORD+"TEXT)")
    //drop table account table query
    private val DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS $TABLE_ACCOUNT"

    //create table menu sql query
    private val CREATE_MENU_TABLE = ("CREATE TABLE"+ TABLE_ACCOUNT + "(" +
            COLUMN_ID_MENU + "INT PRIMARY KEY, "+ COLUMN_NAMA_MENU+" TEXT, "
            + COLUMN_PRICE_MENU + "INT, "+ COLUMN_IMAGE+"BLOB)")
    //DROP MENU SQL QUERY
    private val DROP_MENU_TABLE = "DROP TABLE IF EXISTS $TABLE_MENU"

    private val INSERT_ACCOUNT_TABLE

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(CREATE_ACCOUNT_TABLE)
        p0?.execSQL(CREATE_MENU_TABLE)
        p0?.execSQL(INSERT_ACCOUNT_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DROP_ACCOUNT_TABLE)
        p0?.execSQL(DROP_MENU_TABLE)
        onCreate(p0)
    }

    fun checkLogin(email: String, password: String): Boolean {
        val colums = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase
        //selection critorio
        val selection = "$COLUMN_EMAIL =? AND $COLUMN_PASSWORD =?"
        //selection argument
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(
            TABLE_ACCOUNT,
            colums, //colums return
            selection,//colums from where close
            selectionArgs,//the values for the where clause
            null,//group the rows
            null,//filter by rows group
            null
        )//the sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true
        else
            return false
        }
    //Add Userr
    fun addAccount(email: String, name: String, level: String, password: String){
        val db = this.readableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMAIL,email)
        values.put(COLUMN_NAME,name)
        values.put(COLUMN_LEVEL,level)
        values.put(COLUMN_PASSWORD,password)

        db.insert(TABLE_ACCOUNT,null,values)
        db.close()
    }
    fun checkData(email: String):String{
        val colums = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL = ?"
        val  selectionArgs = arrayOf(email)
        var name:String = ""

        val cursor = db.query(TABLE_ACCOUNT,//TABLETOQUERY
    colums,//columns to return
    selection,//soloms for where clause
    selectionArgs,//the values where clause
    null, null, null)

        if (cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        }
        cursor.close()
        db.close()
        return name
    }
    fun addMenu(menu: MenuModel{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_MENU,menu.id)
        values.put(COLUMN_NAMA_MENU,menu.name)
        values.put(COLUMN_PRICE_MENU,menu.price)
        //prepare image
        val byteOutputStream = ByteArrayOutputStream()
        val imageInByte:ByteArray
        menu.image.compress(Bitmap.CompressFormat.JPEG,100,byteOutputStream)
        imageInByte = byteOutputStream.toByteArray()
        values.put(COLUMN_IMAGE,imageInByte)

        val result = db.insert(TABLE_MENU, null, values)
        //show message
        if (result==(0).toLong()){
            Toast.makeText(context, "add menu Failed",Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context,"Add menu Failed",Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

}
