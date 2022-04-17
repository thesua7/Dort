package si.nsu.dort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.view.View;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class Message extends AppCompatActivity {

    Button btnSendMsg;
    EditText etMsg, usey;
    SharedPreferences prf;
    ListView lvDiscussion;
    ArrayList<String> listConversation = new ArrayList<String>();
    ArrayAdapter arrayAdpt;

    String UserName, user_msg_key;
    String temp;
    private DatabaseReference dbr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        etMsg = (EditText) findViewById(R.id.etMessage);
        usey = findViewById(R.id.usey);
        lvDiscussion = (ListView) findViewById(R.id.lvConversation);
        arrayAdpt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listConversation);
        lvDiscussion.setAdapter(arrayAdpt);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        String session_id = prf.getString("id",null);


        UserName = session_id;


        DBhelper db = new DBhelper(this);
        Cursor cursor = db.getAllInfobyId(UserName);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                temp = cursor.getString(0);
                cursor.moveToNext();
                break;
            }
        }

        usey.setText(temp);
        dbr = FirebaseDatabase.getInstance().getReference();


        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                user_msg_key = dbr.push().getKey();
                dbr.updateChildren(map);

                DatabaseReference dbr2 = dbr.child(user_msg_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("msg", temp + " : " + etMsg.getText().toString());
                dbr2.updateChildren(map2);
                etMsg.setText("");
            }
        });
        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void updateConversation(DataSnapshot dataSnapshot){
        String msg, conversation;
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext()){
            msg = (String) ((DataSnapshot)i.next()).getValue();
            conversation = ""+ msg;
            arrayAdpt.insert(conversation, arrayAdpt.getCount());
            arrayAdpt.notifyDataSetChanged();
        }
    }
}