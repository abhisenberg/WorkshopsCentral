package com.abheisenberg.workshopscentral.SQLDatabaseWorkshops;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.abheisenberg.workshopscentral.WorkshopDataStructure.Workshop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abheisenberg on 31/1/18.
 */

public class DBHandler extends SQLiteOpenHelper {
    public static final String TAG = "DB";

    private Context context;

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "workshopsdb";
    public static final String TABLE_NAME = "workshop_data";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_VENUE = "venue";
    public static final String KEY_DATE = "date";
    public static final String KEY_FEES = "fees";

    private static final String CREATE = " CREATE ";
    private static final String DROP = " DROP ";
    private static final String IF = " IF ";
    private static final String EXISTS = " EXISTS ";
    private static final String TABLE = " TABLE ";
    private static final String PRIMARY = " PRIMARY ";
    private static final String TEXT = " TEXT ";
    private static final String INTEGER = " INTEGER ";
    private static final String KEY = " KEY ";
    private static final String OPEN_BRACKET = " ( ";
    private static final String CLOSE_BRACKET =  " ) ";
    private static final String COMMA = " , ";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public DBHandler(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ARTICLES_TABLE =
                CREATE + TABLE
                        + TABLE_NAME + OPEN_BRACKET
                        + KEY_ID + INTEGER + PRIMARY + KEY + COMMA
                        + KEY_TITLE + TEXT + COMMA
                        + KEY_VENUE + TEXT + COMMA
                        + KEY_DATE + TEXT + COMMA
                        + KEY_FEES + INTEGER
                        + CLOSE_BRACKET;

        db.execSQL(CREATE_ARTICLES_TABLE);
    }


    public void addWs(Workshop[] wslist){
        SQLiteDatabase database = getWritableDatabase();

        for(int i=0; i<wslist.length; i++){
            Workshop thisws = wslist[i];
            ContentValues values = new ContentValues();
            values.put(KEY_ID, thisws.getID());
            values.put(KEY_TITLE, thisws.getTITLE());
            values.put(KEY_VENUE, thisws.getVENUE());
            values.put(KEY_DATE, thisws.getDATE());
            values.put(KEY_FEES, thisws.getFEES());
            database.insert(TABLE_NAME, null, values);
        }

        database.close();

    }

    public ArrayList<Workshop> getAllWs(){
        ArrayList<Workshop> list = new ArrayList<>();
        String selectq = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectq,null);

        if(cursor.moveToFirst()){
            do {
                list.add(cursorToWs(cursor));
            } while (cursor.moveToNext());
        }

        return list;
    }

    private Workshop cursorToWs(Cursor cursor) {
       return new Workshop(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4)
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
