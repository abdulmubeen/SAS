package com.batch08.srucms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class StudentLogin extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_student_login, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextInputLayout student_email = view.findViewById(R.id.student_email);
        final TextInputLayout student_password = view.findViewById(R.id.student_password);
        final Button btnSignIn = view.findViewById(R.id.s_login);
        final TextInputEditText stu_email = view.findViewById(R.id.s_email_input_edit_text);
        final TextInputEditText stu_pwd = view.findViewById(R.id.s_password_input_edit_text);
        ProgressDialog progressDialog = new ProgressDialog(view.getContext(), R.style.MyAlertDialogStyle);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Signing In...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        stu_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String f_email= student_email.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(f_email)){
                    student_email.setError("Required Field..");
                    return;
                }else
                    student_email.setError(null);
            }
        });
        stu_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String f_pwd = student_password.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(f_pwd)){
                    student_password.setError("Required Field..");
                    return;
                }
                else
                    student_password.setError(null);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_email= student_email.getEditText().getText().toString().trim();
                String s_pwd = student_password.getEditText().getText().toString().trim();
                progressDialog.show();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
                Query checkUser = reference.orderByChild("rollno");
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            progressDialog.dismiss();
                            student_email.setError(null);
                            student_email.setErrorEnabled(false);

                            String passwordFromDB = dataSnapshot.child(s_email).child("password").getValue(String.class);
                            if(passwordFromDB != null && passwordFromDB.equals(s_pwd)){

                                student_password.setError(null);
                                student_password.setErrorEnabled(false);
                                Integer totalClassesFromDB;
                                String nameFromDB = dataSnapshot.child(s_email).child("name").getValue(String.class);
                                String rollnoFromDB = dataSnapshot.child(s_email).child("rollno").getValue(String.class);
                                String emailFromDB = dataSnapshot.child(s_email).child("email").getValue(String.class);
                                String branchFromDB = dataSnapshot.child(s_email).child("branch").getValue(String.class);
                                String sectionFromDB = dataSnapshot.child(s_email).child("section").getValue(String.class);
                                Integer classesAttendedFromDB = dataSnapshot.child(s_email).child("classesAttended").getValue(int.class);
                                Integer classesAbsentFromDB = dataSnapshot.child(s_email).child("classesAbsent").getValue(int.class);
                                if(sectionFromDB.equals("A")){
                                    totalClassesFromDB = dataSnapshot.child("Classes").child("totalClassesA").getValue(int.class);
                                } else if(sectionFromDB.equals("B")){
                                    totalClassesFromDB = dataSnapshot.child("Classes").child("totalClassesB").getValue(int.class);
                                } else {
                                    totalClassesFromDB = dataSnapshot.child("Classes").child("totalClassesC").getValue(int.class);
                                }

                                Intent intent = new Intent(getActivity().getApplicationContext(),Student_Dashboard.class);

                                intent.putExtra("name",nameFromDB);
                                intent.putExtra("rollno",rollnoFromDB);
                                intent.putExtra("email",emailFromDB);
                                intent.putExtra("classesAttended",classesAttendedFromDB);
                                intent.putExtra("classesAbsent",classesAbsentFromDB);
                                intent.putExtra("totalClasses",totalClassesFromDB);
                                intent.putExtra("branch",branchFromDB);
                                intent.putExtra("section",sectionFromDB);

                                startActivity(intent);
                            }
                            else {
                                student_password.setError("Wrong Password");
                                progressDialog.dismiss();
                                student_password.requestFocus();
                            }
                        }
                        else{
                            student_email.setError("No such User exists");
                            progressDialog.dismiss();
                            student_email.requestFocus();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}