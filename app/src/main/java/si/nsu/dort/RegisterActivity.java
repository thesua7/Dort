package si.nsu.dort;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import at.favre.lib.crypto.bcrypt.BCrypt;

public class RegisterActivity extends AppCompatActivity {

    EditText full_name,e_mail,pwd,con_pass,id,bio;
    Button register,sign_in;
    RadioButton male,female;
    TextView tvs;
    boolean retValue;

    DBhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);

        full_name = findViewById(R.id.name);
        e_mail = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        con_pass = findViewById(R.id.con_password);
        register =(Button) findViewById(R.id.register);
        id = findViewById(R.id.nsu_id);
        bio =  findViewById(R.id.bio);



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
            String nsuID = id.getText().toString();
            String bio_data = bio.getText().toString();


            //tvs.setText(c_pass);

            if(TextUtils.isEmpty(name)||TextUtils.isEmpty(mail)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(c_pass)||TextUtils.isEmpty(nsuID)||TextUtils.isEmpty(bio_data)){

                Toast.makeText(RegisterActivity.this,"All fields required",Toast.LENGTH_SHORT).show();
            }

            else if(pass.length()>=6){

                if(pass.equals(c_pass)){
               // String bcryptHashString= BCrypt.withDefaults().hashToString(12,pass.toCharArray());

                Boolean check_email = db.checkemail_(mail);

                boolean insert=false;
                if(!check_email){
                    if(male.isChecked()){
                         insert = db.insertData_(mail,name,pass,"Male",nsuID,bio_data);

                    }
                    else if(female.isChecked()){
                         insert = db.insertData_(mail,name,pass,"Female",nsuID,bio_data);

                    }

                    if(insert){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
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