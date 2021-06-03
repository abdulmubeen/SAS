package com.batch08.srucms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyAttendance extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Model> modelArrayList;
    private CustomAdapter customAdapter;
    private TextView fac_name,fac_email;
    private Button btn_submit;
    private List<Model> currentSelectedItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_attendance);

        recyclerView = (RecyclerView) findViewById(R.id.stu_list_recycler);
        fac_name = findViewById(R.id.fac_full_name);
        fac_email = findViewById(R.id.fac_email_dash);
        btn_submit = findViewById(R.id.fac_list_submit);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String selected_sec = intent.getStringExtra("section");
        fac_name.setText(name);
        fac_email.setText(email);

        if(selected_sec.equals("A")){
            ArrayList<String> stu_roll = new ArrayList<>();
            for(int i=0;i<=54;i++){
                StudentEmail email1 = new StudentEmail();
                String temp = email1.getEmail(i);
                stu_roll.add(temp);
            }
            modelArrayList = getModelA(false,stu_roll);
            customAdapter = new CustomAdapter(this, modelArrayList, new CustomAdapter.OnItemCheckListener() {
                @Override
                public void onItemCheck(Model item) {
                    currentSelectedItems.add(item);
                }

                @Override
                public void onItemUncheck(Model item) {
                    currentSelectedItems.remove(item);
                }
            });
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }
        if(selected_sec.equals("B")){
            ArrayList<String> stu_roll = new ArrayList<>();
            for(int i=55;i<=102;i++){
                StudentEmail email1 = new StudentEmail();
                String temp = email1.getEmail(i);
                stu_roll.add(temp);
            }
            modelArrayList = getModelB(false,stu_roll);
            customAdapter = new CustomAdapter(this, modelArrayList, new CustomAdapter.OnItemCheckListener() {
                @Override
                public void onItemCheck(Model item) {
                    currentSelectedItems.add(item);
                }

                @Override
                public void onItemUncheck(Model item) {
                    currentSelectedItems.remove(item);
                }
            });
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }
        if(selected_sec.equals("C")){
            ArrayList<String> stu_roll = new ArrayList<>();
            for(int i=103;i<=156;i++){
                StudentEmail email1 = new StudentEmail();
                String temp = email1.getEmail(i);
                stu_roll.add(temp);
            }
            modelArrayList = getModelC(false,stu_roll);
            customAdapter = new CustomAdapter(this, modelArrayList, new CustomAdapter.OnItemCheckListener() {
                @Override
                public void onItemCheck(Model item) {
                    currentSelectedItems.add(item);
                }

                @Override
                public void onItemUncheck(Model item) {
                    currentSelectedItems.remove(item);
                }
            });
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i< currentSelectedItems.size();i++){
                    String temp = currentSelectedItems.get(i).getHno();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
                    Query checkUser = reference.orderByChild("rollno");
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                int cls = snapshot.child(temp).child("classesAttended").getValue(int.class);
                                String sectionFromDB = snapshot.child(temp).child("section").getValue(String.class);
                                if(sectionFromDB.equals("A")){
                                    Integer totalClassesFromDB = snapshot.child("Classes").child("totalClassesA").getValue(int.class);
                                    totalClassesFromDB++;
                                    reference.child("Classes").child("totalClassesA").setValue(totalClassesFromDB);
                                } else if(sectionFromDB.equals("B")){
                                    Integer totalClassesFromDB = snapshot.child("Classes").child("totalClassesB").getValue(int.class);
                                    totalClassesFromDB++;
                                    reference.child("Classes").child("totalClassesB").setValue(totalClassesFromDB);
                                } else {
                                    Integer totalClassesFromDB = snapshot.child("Classes").child("totalClassesC").getValue(int.class);
                                    totalClassesFromDB++;
                                    reference.child("Classes").child("totalClassesC").setValue(totalClassesFromDB);
                                }
                                cls++;
                                reference.child(temp).child("classesAttended").setValue(cls);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                finish();
            }
        });
    }

    private ArrayList<Model> getModelA(boolean isSelect,ArrayList<String> roll) {
        ArrayList<Model> list = new ArrayList<>();
        for(int i = 0; i < roll.size(); i++){
            Model model = new Model();
            model.setSelected(isSelect);
            model.setHno(roll.get(i));
            list.add(model);
        }
        return list;
    }
    private ArrayList<Model> getModelB(boolean isSelect,ArrayList<String> roll) {
        ArrayList<Model> list = new ArrayList<>();
        for(int i = 0; i < roll.size(); i++){
            Model model = new Model();
            model.setSelected(isSelect);
            model.setHno(roll.get(i));
            list.add(model);
        }
        return list;
    }
    private ArrayList<Model> getModelC(boolean isSelect,ArrayList<String> roll) {
        ArrayList<Model> list = new ArrayList<>();
        for(int i = 0; i < roll.size(); i++){
            Model model = new Model();
            model.setSelected(isSelect);
            model.setHno(roll.get(i));
            list.add(model);
        }
        return list;
    }
}