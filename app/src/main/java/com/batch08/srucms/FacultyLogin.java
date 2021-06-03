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

import java.util.Objects;

public class FacultyLogin extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_faculty_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextInputLayout faculty_email = view.findViewById(R.id.faculty_email);
        final TextInputLayout faculty_password = view.findViewById(R.id.faculty_password);
        final Button btnSignIn = view.findViewById(R.id.f_login);
        final TextInputEditText fac_email = view.findViewById(R.id.f_email_input_edit_text);
        final TextInputEditText fac_pwd = view.findViewById(R.id.f_password_input_edit_text);
        ProgressDialog progressDialog = new ProgressDialog(view.getContext(), R.style.MyAlertDialogStyle);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Signing In...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        fac_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String f_email= Objects.requireNonNull(faculty_email.getEditText()).getText().toString().trim();
                if(TextUtils.isEmpty(f_email)){
                    faculty_email.setError("Required Field..");
                }else
                    faculty_email.setError(null);

            }
        });
        fac_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String f_pwd = Objects.requireNonNull(faculty_password.getEditText()).getText().toString().trim();
                if(TextUtils.isEmpty(f_pwd)){
                    faculty_password.setError("Required Field..");
                }else
                    faculty_password.setError(null);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f_email= Objects.requireNonNull(faculty_email.getEditText()).getText().toString().trim();
                String f_pwd = Objects.requireNonNull(faculty_password.getEditText()).getText().toString().trim();
                progressDialog.show();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Faculty");
                Query checkUser = reference.orderByChild("subject").equalTo(f_email);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            progressDialog.dismiss();
                            faculty_email.setError(null);
                            faculty_email.setErrorEnabled(false);

                            String passwordFromDB = dataSnapshot.child(f_email).child("password").getValue(String.class);
                            if(passwordFromDB != null && passwordFromDB.equals(f_pwd)){

                                faculty_password.setError(null);
                                faculty_password.setErrorEnabled(false);

                                String nameFromDB = dataSnapshot.child(f_email).child("name").getValue(String.class);
                                String deptFromDB = dataSnapshot.child(f_email).child("department").getValue(String.class);
                                String emailFromDB = dataSnapshot.child(f_email).child("email").getValue(String.class);
                                String subjectFromDB = dataSnapshot.child(f_email).child("subject").getValue(String.class);

                                Intent intent = new Intent(getActivity().getApplicationContext(),Faculty_Dashboard.class);

                                intent.putExtra("name",nameFromDB);
                                intent.putExtra("dept",deptFromDB);
                                intent.putExtra("email",emailFromDB);
                                intent.putExtra("subject",subjectFromDB);

                                startActivity(intent);
                            }
                            else {
                                faculty_password.setError("Wrong Password");
                                progressDialog.dismiss();
                                faculty_password.requestFocus();
                            }
                        }
                        else{
                            faculty_email.setError("No such User exists");
                            progressDialog.dismiss();
                            faculty_email.requestFocus();
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