package si.nsu.dort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText e_mail,pass_word;
    Button signUp,signIn;

    DBhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e_mail = findViewById(R.id.email);
        pass_word = findViewById(R.id.password);
        signUp = findViewById(R.id.btn_signup);
        signIn = findViewById(R.id.btn_login);

        db= new DBhelper(this);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = e_mail.getText().toString();
                String pass = pass_word.getText().toString();

                if(TextUtils.isEmpty(mail)|| TextUtils.isEmpty(pass)){
                    Toast.makeText(MainActivity.this,"All fields required",Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkemailpass = db.CheckEmailPass(mail,pass);
                    if(checkemailpass==true){
                       // Toast.makeText(MainActivity.this,"Successfull",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),profileActivity.class);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(MainActivity.this,"Unsuccessfull",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}