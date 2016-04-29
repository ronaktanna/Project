package example.ronak.com.project;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText userInput;
    Button addNote;
    ListView lv;
    ArrayList<String> array;
    ArrayAdapter<String> adapter;
    List<Items> items;
    DatabaseHandler db;

    Button ret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ret = (Button)findViewById(R.id.retrieve);

        userInput = (EditText)findViewById(R.id.userInput);
        addNote = (Button)findViewById(R.id.button);
        lv = (ListView)findViewById(R.id.listView);
        array = new ArrayList<String>();
        registerForContextMenu(lv);

        db = new DatabaseHandler(this);

        /* Fetch items from the database and put the items into the list view when the app opens. */
        items = new ArrayList<Items>();
        items = db.getAllItems();
        for(Items i:items){
            array.add(i.getText_data());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, array);
        lv.setAdapter(adapter);


        //Add the newly inputted note into the database and also into the listview as a new item
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = userInput.getText().toString();
                str = str.trim();

                Items i = db.addListItem(str);//adds the items to the database and also returns an items object
                items.add(i);
                adapter.add(str);
                Toast.makeText(getApplicationContext(), "Inserted!", Toast.LENGTH_SHORT).show();
                userInput.setText("");
            }
        });


        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Items> tempitems = new ArrayList<Items>();
                tempitems = db.getAllItems();

                for(Items i:tempitems){
                    Log.e("Item id:=="+String.valueOf(i.getKey_id())+"Item data==",""+i.getText_data());
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                return false;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        //Toast.makeText(getApplicationContext(),String.valueOf(info.position),Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){

            case R.id.update:
                DatabaseHandler d = new DatabaseHandler(this);
                int pos = info.position;
                Items itemToUpdate = null;
                for(Items i: items){
                    if(i.getKey_id() == pos+1){
                        itemToUpdate = i;
                        break;
                    }
                }
                d.updateItem(itemToUpdate, userInput.getText().toString());
                array.set(info.position, userInput.getText().toString());
                adapter.notifyDataSetChanged();
                d.close();
                break;

            case R.id.delete:

                break;
        }

        return super.onContextItemSelected(item);
    }
}
