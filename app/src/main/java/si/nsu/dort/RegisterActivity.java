package si.nsu.dort;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import at.favre.lib.crypto.bcrypt.BCrypt;

public class RegisterActivity extends AppCompatActivity {

    EditText full_name,e_mail,pwd,con_pass;
    Button register,sign_in;
    TextView tvs;
    boolean retValue;

    DBhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        full_name = findViewById(R.id.name);
        e_mail = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        con_pass = findViewById(R.id.con_password);
        register = findViewById(R.id.btn_signup);
        sign_in = findViewById(R.id.btn_signin);
        db= new DBhelper(this);
        //tvs = findViewById(R.id.tv);//for test

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String name = full_name.getText().toString();
            String mail = e_mail.getText().toString();
            String pass = pwd.getText().toString();
            String c_pass = con_pass.getText().toString();


            //tvs.setText(c_pass);

            if(TextUtils.isEmpty(name)||TextUtils.isEmpty(mail)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(c_pass)){

                Toast.makeText(RegisterActivity.this,"All fields required",Toast.LENGTH_SHORT).show();
            }

            else if(pass.length()>=6){
                retValue=mail.endsWith("@northsouth.edu");
                if(!retValue)
                {
                     Toast.makeText(RegisterActivity.this,"Use NSU Mail",Toast.LENGTH_SHORT).show();
                }
                else if(pass.equals(c_pass)){
               // String bcryptHashString= BCrypt.withDefaults().hashToString(12,pass.toCharArray());
                Boolean check_email = db.checkemail(mail);
                if(!check_email){
                    Boolean insert = db.insertData(mail,name,pass);
                    if(insert){
                        Toast.makeText(RegisterActivity.this,"Registered",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Mail already in use",Toast.LENGTH_SHORT).show();
                }
            } }
            else
            {
                Toast.makeText(RegisterActivity.this,"Must containt 6 chars",Toast.LENGTH_SHORT).show();
            }





            }
        });
    }
}