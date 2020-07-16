package com.example.sharecab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    int RC_SIGN_IN=0;
    private EditText email;
    private EditText password;
    private Button login;
    private Button signup;
    private Button reset_password;
    private Button signout;
    private Button google;
    private Spinner spinner;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        google =findViewById(R.id.google);
//        databaseReference = FirebaseDatabase.getInstance().getReference();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        spinner =findViewById(R.id.type);
        email =findViewById(R.id.email);
        password =findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        signout =findViewById(R.id.signout1);
        reset_password =findViewById(R.id.reset_password);
        auth =FirebaseAuth.getInstance();
        signout.setVisibility(View.INVISIBLE);
        View.OnClickListener listener =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() != null) {
                    signout.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Already Logged In ", Toast.LENGTH_SHORT).show();
                } else {
                    signout.setVisibility(View.INVISIBLE);
                    final String email_s = email.getText().toString();
                    String password_s = password.getText().toString();
                    if (email_s.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please Enter an Email-Id", Toast.LENGTH_SHORT).show();
                    } else if (password_s.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please Enter an Password", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.signInWithEmailAndPassword(email_s, password_s).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Welcome " + email_s, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("name", email_s);
                                    intent.putExtra("Type",spinner.getSelectedItem().toString());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        };
        login.setOnClickListener(listener);

        View.OnClickListener listener1 =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auth.getCurrentUser()!=null) {
                    Toast.makeText(LoginActivity.this,"Already logged in",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent1 = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intent1);
                }
            }
        };
        signup.setOnClickListener(listener1);

        View.OnClickListener listener2 =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_s =email.getText().toString();
                if(email_s.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please Enter an Email-Id",Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.sendPasswordResetEmail(email_s).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Reset link send to your email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Password reset failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        };
        reset_password.setOnClickListener(listener2);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                signout.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent =new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("Type","Rider");
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        email.setText("");
        password.setText("");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finishAffinity();
    }
}
