package example.ronak.com.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronak on 28-Apr-16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final String TABLE_LIST = "MyListItem";

    private static final String KEY_ID = "id";
    private static final String KEY_ListItem = "listitem";

    public DatabaseHandler(Context context){
        //super(Context context, String name, CursorFactory cursorFactory, int version);
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_LIST_TABLE = "CREATE TABLE " + TABLE_LIST + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ListItem + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    Items addListItem(String item){
        Items it = new Items();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e("Value inserting==", "" + item);
        values.put(KEY_ListItem, item);
        db.insert(TABLE_LIST, null, values);
        db.close();
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from "+TABLE_LIST+" where "+KEY_ListItem+" = ? ", new String[]{item});
        c.moveToFirst();
        Log.e("Value id==", "" + c.getString(0));
        it.setKey_id(Integer.parseInt(c.getString(0)));
        it.setText_data(item);
        c.close();

        return it;
    }

    void updateItem(int id, String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ListItem, text);
        //db.update(String table, ContentValues values, String whereClause, String[] whereArgs);
        int status = db.update(TABLE_LIST, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        Log.e("Status ==","Pass");
        Cursor c = db.rawQuery("select * from "+TABLE_LIST+" where "+KEY_ID+" = ? ", new String[]{String.valueOf(id)});
        c.moveToFirst();
        Log.e("Updated item text==", c.getString(1));
        db.close();

    }

    void deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(String table, String whereClause, String[] whereArgs);
        db.delete(TABLE_LIST, KEY_ID+" = ?", new String[]{String.valueOf(id)});
        Log.e("Value deleted!","");
        db.close();
    }

    //Getting all contacts
    public List<Items> getAllItems(){
        List<Items> itemList = new ArrayList<Items>();
        String query = "SELECT * FROM "+TABLE_LIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Items item = new Items();
                item.setKey_id(Integer.parseInt(cursor.getString(0)));
                item.setText_data(cursor.getString(1));
                itemList.add(item);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return itemList;
    }

}
