package com.example.tutorial_7;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText fname,lname,email,password;
    RadioGroup gender;
    RadioButton Gender;
    CheckBox checkBox;
    Button signup;
    Spinner city;
    Switch branch;
    //*****************"Tutorial 07***********************
    MyDatabaseHelper mydb;
    //*****************"Tutorial 07"***********************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fname = findViewById(R.id.signup_fname);
        lname = findViewById(R.id.signup_lname);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        gender = findViewById(R.id.signup_gender);
        branch = findViewById(R.id.signup_branch);
        checkBox = findViewById(R.id.signup_checkbox);
        city = findViewById(R.id.signup_spinner);
        signup = findViewById(R.id.submit);

        signup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N_MR1)
            @Override
            public void onClick(View v) {
                Boolean Fname = fname.getText().toString().trim().isEmpty();
                Boolean Lname = lname.getText().toString().trim().isEmpty();
                String Email = email.getText().toString().trim();
                Boolean Email_val = email.getText().toString().trim().isEmpty();
                String Password = password.getText().toString().trim();
                Boolean Password_val = password.getText().toString().trim().isEmpty();
                Boolean Branch = branch.isChecked();
                int id = gender.getCheckedRadioButtonId();
                String set_city = city.getSelectedItem().toString();

                if(!Fname && !Lname && !Email_val && !Password_val && Patterns.EMAIL_ADDRESS.matcher(Email).matches() && Password.length()>=8 && !set_city.equals("Select Your City...")){
                    Gender = findViewById(id);
                    //*****************"Tutorial 07"***********************
                    mydb = new MyDatabaseHelper(SignUp.this);
                    Boolean res=mydb.reg_insert(fname.getText().toString().trim(),lname.getText().toString().trim(),Email,Branch,Password,Gender.getText().toString(),set_city);
                    Intent intent = new Intent(SignUp.this,MainActivity.class);
                    if(res){
                        Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        intent.putExtra("user_id",Email);
                        intent.putExtra("pass",Password);
                        startActivity(intent);
                    }else {
                        Toast.makeText(SignUp.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    //*****************"Tutorial 07"***********************
                }
                else {
                    if(Fname){
                        fname.setError("Name is Require");
                    }
                    if(Lname){
                        lname.setError("Surname is Require");
                    }
                    if(Email_val){
                        email.setError("Email is Require");
                    }
                    else{
                        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                            email.setError("Email is Invalid");
                        }
                    }
                    if(Password_val){
                        password.setError("Password is Invalid");
                    }
                    else{
                        if(Password.length()<8) {
                            password.setError("Password to short");
                        }
                    }
                    if(set_city.equals("Select Your City...")){
                        TextView textView = (TextView) city.getSelectedView();
                        textView.setError("Selected Item is Invalid");
                    }
                    Toast.makeText(SignUp.this,"Invalid Credentials...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}