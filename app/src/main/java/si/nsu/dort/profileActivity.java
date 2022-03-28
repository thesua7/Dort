package si.nsu.dort;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class profileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView getGenInfo= findViewById(R.id.getGenInfo);
        TextView workedon=findViewById(R.id.workedon);
        TextView interestArea=findViewById(R.id.interestArea);
        TextView currWorkon=findViewById(R.id.currentlyworkingon);

        DBhelper db= new DBhelper(this);

        //Get and set General Info

        Cursor cursor= db.getGenaralInfo();

        StringBuilder stringBuilder= new StringBuilder();

        getGenInfo.setText(stringBuilder);

        while (cursor.moveToNext())
        {
            stringBuilder.append("Name:"+cursor.getString(0)
                                +"\nNSU ID :"+cursor.getInt(1)
                                +"\nSex :" +cursor.getString(2)
                                +"\nBio :" +cursor.getString(3));
        }
        getGenInfo.setText(stringBuilder);



        //Get and set Research Info

        Cursor cursor1= db.getResearchInfo();

        StringBuilder stringBuilder1= new StringBuilder();
        StringBuilder stringBuilder2= new StringBuilder();
        StringBuilder stringBuilder3= new StringBuilder();
        while (cursor1.moveToNext())
        {
            stringBuilder1.append("Research Interest:"+cursor1.getString(0));
            stringBuilder2.append(cursor1.getString(1));
            stringBuilder3.append(cursor1.getString(2));
        }
        interestArea.setText(stringBuilder1);
        workedon.setText(stringBuilder2);
        currWorkon.setText(stringBuilder3);
    }


}