package com.batch08.srucms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Student_Dashboard extends AppCompatActivity {

    TextView stu_name,stu_roll,stu_branch,stu_section,stu_total_cls,stu_cls_attended,stu_percentage;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__dashboard);
        stu_name = findViewById(R.id.student_full_name);
        stu_roll = findViewById(R.id.student_roll_no);
        stu_branch = findViewById(R.id.student_branch);
        stu_section = findViewById(R.id.student_section);
        stu_total_cls = findViewById(R.id.student_totalClasses);
        stu_cls_attended = findViewById(R.id.student_classesAttended);
        stu_percentage = findViewById(R.id.percentage);
        btn_logout = findViewById(R.id.student_logout_btn);

        showAllUserData();
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }
    private void showAllUserData() {

        Intent intent = getIntent();
        String student_name = intent.getStringExtra("name");
        String student_roll = intent.getStringExtra("rollno");
        String student_branch = intent.getStringExtra("branch");
        String student_section = intent.getStringExtra("section");
        int student_totalClasses = intent.getIntExtra("totalClasses",0);
        int student_classesAttended = intent.getIntExtra("classesAttended",0);
        int student_percentage = (student_classesAttended / student_totalClasses)*100;
        String stu_totalPercentage = student_percentage +"%";

        stu_name.setText(student_name);
        stu_roll.setText(student_roll);
        stu_branch.setText(student_branch);
        stu_section.setText(student_section);
        stu_total_cls.setText(String.valueOf(student_totalClasses));
        stu_cls_attended.setText(String.valueOf(student_classesAttended));
        stu_percentage.setText(stu_totalPercentage);
    }
    private void logout(){
        finish();
    }
}