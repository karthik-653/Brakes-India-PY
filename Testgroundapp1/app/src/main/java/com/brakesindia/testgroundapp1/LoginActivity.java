package com.brakesindia.testgroundapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.brakesindia.testgroundapp1.R;

import com.brakesindia.testgroundapp1.networking.ApiClient;
import com.brakesindia.testgroundapp1.networking.Requests.LoginRequest;
import com.brakesindia.testgroundapp1.networking.Response.LoginResponse;
import com.brakesindia.testgroundapp1.ui.gallery.GalleryFragment;
import com.brakesindia.testgroundapp1.ui.home.HomeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button submit;
    TextView signin;
    ProgressBar pbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        changeStatusBarColor(getResources().getColor(R.color.biblue));

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit_area);
        signin = findViewById(R.id.signin);
        pbar = findViewById(R.id.pbar);

        new ApiClient("http://192.168.7.111:5252/");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Inside login");

                if(TextUtils.isEmpty(username.getText().toString())){
                    System.out.println("Inside login, but text empty");
                    shakeEditText(username);
                    username.setBackgroundResource(R.drawable.custom_edittext_border_red);
                }
                else if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    shakeEditText(password);
                    password.setBackgroundResource(R.drawable.custom_edittext_border_red);
                }
                else {
                    System.out.println("Logging in");
                    pbar.setVisibility(View.VISIBLE);
                    login();
                }
            }
        });
    }
    public void login(){
        System.out.println("Inside login function");
        LoginRequest loginrequest = new LoginRequest();
        loginrequest.setId(username.getText().toString().trim());
        loginrequest.setPassword(password.getText().toString());
        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().
                userLogin(Integer.parseInt(loginrequest.getId()));
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println("Inside onResponse function");
                System.out.println(response.code());
                if(response.isSuccessful()){
                    System.out.println("Response is successful");
                    LoginResponse response1 = response.body();
                    assert response1 != null;
                    if(loginrequest.getPassword().equals(response1.getPassword())){
                        System.out.println("Password matching");
                        Toast.makeText(getApplicationContext(), "Login successful",
                                Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username.getText().toString());
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        pbar.setVisibility(View.GONE);

                        finish();
                    }
                    else{
                        System.out.println("Password not matching");
                        shakeEditText(username);
                        shakeEditText(password);
                        username.setBackgroundResource(R.drawable.custom_edittext_border_red);
                        password.setBackgroundResource(R.drawable.custom_edittext_border_red);
                        Toast.makeText(getApplicationContext(), "Username/Password not matching",
                                Toast.LENGTH_LONG).show();
                        pbar.setVisibility(View.GONE);

                    }
                }
                else{
                    System.out.println("Response is not successful");
                    Toast.makeText(getApplicationContext(), "Login failed",
                            Toast.LENGTH_LONG).show();
                    pbar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("Inside on failure method");
                System.out.println(t);
                pbar.setVisibility(View.GONE);
            }
        });
    }
    private void changeStatusBarColor(int color) {
        Window window = getWindow();
    }
    private void shakeEditText(View view) {
        // Shake animation
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);
    }

}