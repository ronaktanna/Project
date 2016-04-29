package example.ronak.com.project;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userInput = (EditText)findViewById(R.id.userInput);
        addNote = (Button)findViewById(R.id.button);
        lv = (ListView)findViewById(R.id.listView);
        array = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, array);
        lv.setAdapter(adapter);

        final DatabaseHandler db = new DatabaseHandler(this);


        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = userInput.getText().toString();
                str = str.trim();
                db.addListItem(array, str);
                adapter.add(str);
                Toast.makeText(getApplicationContext(), "Inserted!", Toast.LENGTH_SHORT).show();
                userInput.setText("");

                /*array.add(new String(str));
                index++;
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();*/
            }
        });


    }
}
