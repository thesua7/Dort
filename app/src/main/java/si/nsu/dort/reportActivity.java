package si.nsu.dort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class reportActivity extends AppCompatActivity {

    EditText msg;
    Button btn;
    SharedPreferences prf,temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        msg = findViewById(R.id.report_msg);
        btn = findViewById(R.id.submit_btn);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        String session_id = prf.getString("id",null);

        temp = getSharedPreferences("Match_Algo",MODE_PRIVATE);
        String card_session_id = temp.getString("Card_Session",null);
        DBhelper db = new DBhelper(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Boolean chk= db.insertData_Report(session_id,card_session_id,msg.getText().toString());
                if(chk){
                    Intent intent = new Intent(getApplicationContext(),feedActivity.class);
                    startActivity(intent);

                }

            }
        });

    }
}