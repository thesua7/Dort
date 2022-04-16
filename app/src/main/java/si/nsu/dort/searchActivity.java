package si.nsu.dort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.widget.SearchView;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {
    SearchView searchView;
    ListView myList;
    List<String> list;
    ArrayAdapter<String> adapter;
    SharedPreferences prf;
    SharedPreferences pref_Match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = (SearchView) findViewById(R.id.search_view);
        myList = (ListView) findViewById(R.id.myList);

        DBhelper dBhelper =new DBhelper(this);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        String session_id = prf.getString("id",null);

        Cursor cursor = dBhelper.getAllId(session_id);
        list = new ArrayList<String>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Log.d("Z",cursor.getString(0));                // move to the next row.
                list.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        myList.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(dBhelper.checkId_(query)){
                    pref_Match = getSharedPreferences("Match_Algo",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref_Match.edit();
                    editor.putString("Card_Session",query);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(),profileActivity.class);
                    startActivity(intent);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                Log.d("Sr",s);
                return false;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override

            public boolean onSuggestionSelect(int position) {
                Log.d("Sr","Selected");
                return false;
            }

            //Khi click suggestion
            @Override
            public boolean onSuggestionClick(int position) {
                Log.d("Sr","SelectedXXX");
                return false;
            }
        });

    }

    }


