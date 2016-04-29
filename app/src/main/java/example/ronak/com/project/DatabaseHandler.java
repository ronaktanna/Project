package example.ronak.com.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ronak on 28-Apr-16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myDatabase";
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
                + " INTEGER," + KEY_ListItem + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    void addListItem(List<String> listItem, String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        /*for(int i=0; i<listItem.size(); i++){

            Log.e("Value inserting==",""+listItem.get(i));
            values.put(KEY_ListItem, listItem.get(i));
            db.insert(TABLE_LIST, null, values);
        }*/
        Log.e("Value inserting==", "" + item);
        values.put(KEY_ListItem, item);
        db.insert(TABLE_LIST, null, values);
        db.close();
    }

    Cursor getListItem(){
        String query = "SELECT * FROM "+TABLE_LIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        return c;

    }
}
