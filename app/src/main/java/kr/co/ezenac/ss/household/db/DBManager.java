package kr.co.ezenac.ss.household.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import kr.co.ezenac.ss.household.model.History;

/**
 * Created by Administrator on 2017-11-27.
 */

public class DBManager extends SQLiteOpenHelper {
    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE Household (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " type INTEGER, money INTEGER, year INTEGER, month INTEGER," +
                " day INTEGER);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void insert(int type, int money, int year, int month, int day){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("Insert into Household Values (null,"+ type +"," + money + ","
                + year +","+ month +"," + day + ");");
    }

    public  void delete(Integer id){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("delete from Household where id = " +id +";");
    }

    public ArrayList<History> getHistoryList(Integer nyear, Integer nmonth, Integer nday){
        ArrayList<History> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Household where year=" + nyear
                + " and month=" + nmonth + " and day=" + nday,null);

        while (cursor.moveToNext()){
            Integer id = cursor.getInt(0);
            Integer type = cursor.getInt(1);
            Integer money = cursor.getInt(2);
            Integer year = cursor.getInt(3);
            Integer month = cursor.getInt(4);
            Integer day = cursor.getInt(5);

            History tmpHistory = new History(id, type, money, year, month, day);
            list.add(tmpHistory);
        }
        cursor.close();
        return list;
    }
}
