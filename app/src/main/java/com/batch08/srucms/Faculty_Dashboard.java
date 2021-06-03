package com.batch08.srucms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Faculty_Dashboard extends AppCompatActivity {

    TextView fac_name,fac_email,fac_department;
    TextInputLayout cls_input,sub_input;
    Button fac_next,fac_logOut;
    String selected_sec;
    private AutoCompleteTextView cls_dropdown, sub_dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty__dashboard);
        fac_name = findViewById(R.id.faculty_full_name);
        fac_email = findViewById(R.id.faculty_email_dash);
        fac_department = findViewById(R.id.faculty_department);
        fac_next = findViewById(R.id.faculty_nextBtn);
        cls_dropdown = findViewById(R.id.autocomplete_cls);
        sub_dropdown = findViewById(R.id.autocomplete_subject);
        cls_input = findViewById(R.id.cls_menu);
        sub_input = findViewById(R.id.subject_menu);
        fac_logOut = findViewById(R.id.faculty_logOutBtn);

        setData();
        Intent intent = getIntent();
        String faculty_subject = intent.getStringExtra("subject");
        String[] clsItems = new String[] {"A", "B", "C"};
        ArrayAdapter<String> clsAdapter = new ArrayAdapter<>(Faculty_Dashboard.this, R.layout.dropdown_item,clsItems);
        String[] subItems = new String[] {faculty_subject};
        ArrayAdapter<String> subAdapter = new ArrayAdapter<>(Faculty_Dashboard.this, R.layout.dropdown_item,subItems);

        cls_dropdown.setAdapter(clsAdapter); sub_dropdown.setAdapter(subAdapter);
        ((AutoCompleteTextView)cls_input.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selected_sec = clsAdapter.getItem(position);
            }
        });

        fac_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Faculty_Dashboard.this,FacultyAttendance.class);
                intent1.putExtra("name",getFacultyName());
                intent1.putExtra("email",getFacultyEmail());
                intent1.putExtra("section",selected_sec);
                startActivity(intent1);
            }
        });

        fac_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setData(){
        String faculty_name = getFacultyName();
        String faculty_dept = getFacultyDept();
        String faculty_email = getFacultyEmail();
        fac_name.setText(faculty_name);
        fac_email.setText(faculty_email);
        fac_department.setText(faculty_dept);
    }
    private String getFacultyName(){
        Intent intent = getIntent();
        return intent.getStringExtra("name");
    }
    private String getFacultyDept(){
        Intent intent = getIntent();
        return intent.getStringExtra("dept");
    }
    private String getFacultyEmail(){
        Intent intent = getIntent();
        return intent.getStringExtra("email");
    }
}