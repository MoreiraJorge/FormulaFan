package pt.ipp.estg.formulafan.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import pt.ipp.estg.formulafan.Fragments.LoginFragment;
import pt.ipp.estg.formulafan.Fragments.RegisterFragment;
import pt.ipp.estg.formulafan.Interfaces.ISessionListener;
import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.Utils.InternetUtil;

public class MainActivity extends AppCompatActivity implements ISessionListener {

    private static final String TAG = "SessionEmailPass";

    private Context context;
    private FragmentManager fragmentManager;
    private FirebaseAuth firebaseAuth;
    private InternetUtil internetUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = getApplicationContext();

        internetUtil = new InternetUtil(getApplication());
        internetUtil.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (!isConnected) {
                    Toast.makeText(context, "Dispositivo Offline!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        LoginFragment loginFragment = new LoginFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, loginFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            String errorCode = ((FirebaseAuthException) task.getException())
                                    .getErrorCode();

                            switch(errorCode){
                                case "ERROR_WRONG_PASSWORD":
                                    Toast.makeText(context, "A password inválida!",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case "ERROR_USER_NOT_FOUND":
                                    Toast.makeText(context, "O utilizador nao existe ou foi apagado!",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case "ERROR_USER_DISABLED":
                                    Toast.makeText(context, "Esta conta foi desativada!",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, "Erro de login!",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            Intent intent = new Intent(context, FormulaFanMainActivity.class);
                            startActivity(intent);
                            Toast.makeText(context, "Login efetuado!",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    @Override
    public void register(EditText email, EditText password) {
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                password.getText().toString())

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            String errorCode = ((FirebaseAuthException) task.getException())
                                    .getErrorCode();

                            switch(errorCode){
                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    email.setError("Email em uso!");
                                    Toast.makeText(context, "Este email já está em uso!",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case "ERROR_INVALID_EMAIL":
                                    email.setError("Email Inválido!");
                                    Toast.makeText(context, "Email mal formatado!",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case "ERROR_WEAK_PASSWORD":
                                    password.setError("Password Fraca!");
                                    Toast.makeText(context, "Password fraca! Minimo 6 caracteres.",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, "Erro de registo!",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } else {
                            fragmentManager.popBackStack();
                            Toast.makeText(context, "Registo completo!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean validateForm(EditText insertEmailField,
                                EditText insertPassField) {
        boolean valid = true;

        String email = insertEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            insertEmailField.setError("Campo obrigatório!");
            valid = false;
        } else {
            insertEmailField.setError(null);
        }

        String password = insertPassField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            insertPassField.setError("Campo obrigatório!");
            valid = false;
        } else {
            insertPassField.setError(null);
        }

        return valid;
    }

    public void registerRedirect() {
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, registerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}