package hu.bme.aut.stringartdesigner.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DbConstants.DATABASE_NAME, null, DbConstants.DATABASE_VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        DbConstants.Polygon.onCreate(sqLiteDatabase)
        DbConstants.Points.onCreate(sqLiteDatabase)
        DbConstants.Lines.onCreate(sqLiteDatabase)
    }

    override fun onUpgrade(
        sqLiteDatabase: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        DbConstants.Polygon.onUpgrade(sqLiteDatabase, oldVersion, newVersion)
        DbConstants.Points.onUpgrade(sqLiteDatabase, oldVersion, newVersion)
        DbConstants.Lines.onUpgrade(sqLiteDatabase, oldVersion, newVersion)
    }
}