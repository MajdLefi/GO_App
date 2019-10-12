package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewListContents extends AppCompatActivity {
    ListView simpleList;
    String[] nameList = {};
    String[] descriptionList = {};
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_contents);
        simpleList = (ListView)findViewById(R.id.simpleList);
        dbHelper=new DatabaseHelper(this);
        List<ListItem> userList = getListData();
        simpleList.setAdapter(new CustomListAdaptor(this, userList));
    }
    private List<ListItem> getListData() {

        return dbHelper.getAllUsers();
    }
}
