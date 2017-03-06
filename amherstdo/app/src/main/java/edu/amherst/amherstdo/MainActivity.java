package edu.amherst.amherstdo;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Model> modelItems;
    EditText inputSearch;
    TextView searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView1);
        searchText = (TextView) findViewById(R.id.searchText);
        modelItems = new ArrayList<Model>();
        modelItems.add(new Model("Run a Marathon", "This is part of Amherst's initiative","LOW PRIORITY"));
        modelItems.add(new Model("Finish COSC Exam", "Professor Prasad's Midterm","HIGH PRIORITY"));
        modelItems.add(new Model("End Global Warming", "don't let Netherlands sink!","VERY HIGH PRIORITY"));
        //modelItems[0] = new Model("Run a Marathon", 0);
        //modelItems[1] = new Model("Finish COSC Exam", 0);
        //modelItems[2] = new Model("End global warming", 0);
        //modelItems[3] = new Model("Be the first person to Mars", 0);

        final Button searchButton = (Button) findViewById(R.id.button);
        final Button clearButton = (Button) findViewById(R.id.button2);
        final EditText textField = (EditText)findViewById(R.id.searchText);

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                String filterText = searchText.getText().toString();
                ArrayList<Model> filterList = new ArrayList();
                for (int i = 0; i<modelItems.size();i++){
                    String name = ((Model) modelItems.get(i)).getName();
                    String description = ((Model) modelItems.get(i)).getDescription();
                    if(name.toLowerCase().indexOf(filterText)!= -1 || description.toLowerCase().indexOf(filterText)!= -1){
                        filterList.add(modelItems.get(i));
                    }
                }
                lv.setAdapter(new CustomAdapter(MainActivity.this, filterList));
            }

        }
        );

        clearButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                lv.setAdapter(new CustomAdapter(MainActivity.this,modelItems));
                textField.setText("");
            }
        });

        inputSearch =(EditText)findViewById(R.id.searchText);

        CustomAdapter adapter = new CustomAdapter(this, modelItems);
        lv.setAdapter(adapter);

//        inputSearch.addTextChangedListener(new TextWatcher() {

//            @Override
//            public void onTextChanged(CharSequence cs, int start, int before, int count) {
//                // When user changed the Text
//                if(count < before){
//                    adapter.resetData();
//                }
//                adapter.getFilter().filter(cs.toString());
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//                                          int arg3) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // TODO Auto-generated method stub
//            }
//        });
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
}}
