package si.nsu.dort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class settingsActivity extends AppCompatActivity {

    SharedPreferences pref;
    EditText r_interests, work_done,currently_working;
    Button submit_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        String session_id = pref.getString("id",null);

        r_interests = findViewById(R.id.research_interest);
        work_done = findViewById(R.id.worked_on);
        currently_working = findViewById(R.id.current_work);
        submit_b = findViewById(R.id.submit_btn);




        DBhelper db = new DBhelper(this);

        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String r = r_interests.getText().toString();
                String wd = work_done.getText().toString();
                String cw = currently_working.getText().toString();



                Boolean check = db.CheckResearchInfo(session_id);

               // Boolean check_p = db.insertData_to_Research(cw,wd,session_id,r);

                if(check){
                    db.insertData_to_Research(cw,wd,session_id,r);
                    Log.d("Set","Done");
                    Intent intent = new Intent(getApplicationContext(),feedActivity.class);
                    startActivity(intent);
                }

                else {
                    db.insertData_to_Research(cw,wd,session_id,r);
                    Log.d("Set","Undone");
                }





            }
        });





    }
}