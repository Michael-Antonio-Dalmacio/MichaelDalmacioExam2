package com.dalmacio.michael.exam2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference root;
    EditText eFname, eLname, etExam1, etExam2;
    TextView tvAverage;
    int index;
    ArrayList<String> keyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        root = db.getReference("grade");
        eFname = findViewById(R.id.eFN);
        eLname = findViewById(R.id.eLN);
        etExam1 = findViewById(R.id.eExam1);
        etExam2 = findViewById(R.id.eExam2);
        tvAverage = findViewById(R.id.tvAve);
        keyList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ss: dataSnapshot.getChildren()) {
                    keyList.add(ss.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Toast.makeText(this, keyList.get(0), Toast.LENGTH_SHORT).show();
    }

    public void addRecord(View v) {
        if (v.getId() == R.id.displayAve) {
            String fname = eFname.getText().toString().trim();
            String lname = eLname.getText().toString().trim();
            Long exam1 = Long.parseLong(etExam1.getText().toString().trim());
            Long exam2 = Long.parseLong(etExam2.getText().toString().trim());
            Long average = (exam1 + exam2) / 2;
            Average ave = new Average(fname, lname, average);
            String key = root.push().getKey();
            root.child(key).setValue(ave);
            Toast.makeText(this, "record added to db", Toast.LENGTH_LONG).show();


            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    index = (int) dataSnapshot.getChildrenCount() - 1;
                    Average aver = dataSnapshot.child(keyList.get(index)).getValue(Average.class);
                    tvAverage.setText("Your Average is: " + aver.getAverage());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
