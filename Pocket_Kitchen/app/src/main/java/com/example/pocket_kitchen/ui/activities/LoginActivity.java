package com.example.pocket_kitchen.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private AlertDialog dialog, progressDialog;
    private EditText editEmail, editPassword;
    private Button btnLogin;
    private TextView textJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View progressView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        AlertDialog.Builder progressDialogBuilder = new AlertDialog.Builder(LoginActivity.this)
                .setView(progressView)
                .setCancelable(false);
        progressDialog = progressDialogBuilder.create();
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String pw = editPassword.getText().toString();
                if (!email.equals("") && !pw.equals("")) login(email, pw);
                else
                    Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        textJoin = findViewById(R.id.text_join);
        textJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJoinDialog();
            }
        });
    }

    private void login(String email, String password) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser auth = mAuth.getCurrentUser();
                            FirebaseFirestore.getInstance().collection("users").document(auth.getUid()).get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot document) {
                                            if (document.exists()) {
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                finish();
                                                progressDialog.dismiss();
                                            } else {
                                                User user = new User();
                                                user.setUid(Objects.requireNonNull(auth).getUid());
                                                user.setName("unKnown");
                                                user.setEmail(auth.getEmail());

                                                FirebaseFirestore.getInstance().collection("users").document(user.getUid()).set(user)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                                finish();

                                                                progressDialog.dismiss();
                                                                dialog.dismiss();
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void join(String email, String password, final String name) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser auth = mAuth.getCurrentUser();

                            User user = new User();
                            user.setUid(Objects.requireNonNull(auth).getUid());
                            user.setName(name);
                            user.setEmail(auth.getEmail());

                            FirebaseFirestore.getInstance().collection("users").document(user.getUid()).set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            dialog.dismiss();
                                        }
                                    });
                        } else {
                            Toast.makeText(getApplicationContext(), "이메일 혹은 비밀번호를 정확하게 입력했는지 확인해주세요", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void showJoinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_join, null);
        builder.setTitle("회원가입");
        builder.setView(view);
        dialog = builder.create();

        final Button btnJoin = view.findViewById(R.id.btn_join);
        final EditText editId = view.findViewById(R.id.edit_join_id);
        final EditText editPw = view.findViewById(R.id.edit_join_password);
        final EditText editPwRe = view.findViewById(R.id.edit_join_password_re);
        final EditText editName = view.findViewById(R.id.edit_name);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = editId.getText().toString();
                String password = editPw.getText().toString();
                String passwordRe = editPwRe.getText().toString();
                String name = editName.getText().toString();

                if (!email.equals("") && !password.equals("") && !passwordRe.equals("") && !name.equals("")) {
                    if (password.equals(passwordRe)) {
                        if (password.length() < 6) {
                            Toast.makeText(getApplicationContext(), "비밀번호를 6자리 이상으로 설정해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            join(email, password, name);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}
