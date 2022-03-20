package si.nsu.dort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class feedActivity extends AppCompatActivity {

    Button profile_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        profile_test = findViewById(R.id.profile_btn);

        profile_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),profileActivity.class);
                startActivity(intent);
            }
        });


    }
}