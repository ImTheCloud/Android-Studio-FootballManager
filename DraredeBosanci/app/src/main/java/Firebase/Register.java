package Firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.draredebosanci.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_register);
        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        signupPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String regex = "^[a-zA-Z0-9]+$";
                if (user.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                } else if (!user.matches(regex)) {
                    signupEmail.setError("Email can only contain letters and numbers");
                } else if (!user.matches("^[\\p{L}A-Za-z0-9]+$")) {
                    signupEmail.setError("Email can only contain letters, numbers and symbols");
                }
                if (pass.isEmpty()) {
                    signupPassword.setError("Password cannot be empty");
                } else if (!pass.matches(regex)) {
                    signupPassword.setError("Password can only contain letters and numbers");
                } else if (!pass.matches("^[\\p{L}A-Za-z0-9]+$")) {
                    signupPassword.setError("Password can only contain letters, numbers and symbols");
                } else {
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register.this, "User registered, verify your email", Toast.LENGTH_SHORT).show();
                                            signupEmail.setText("");
                                            signupPassword.setText("");
                                            startActivity(new Intent(Register.this, Login.class));
                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        } else {
                                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(Register.this, "Register Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void goToLogin(View view) {
        startActivity(new Intent(Register.this, Login.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
