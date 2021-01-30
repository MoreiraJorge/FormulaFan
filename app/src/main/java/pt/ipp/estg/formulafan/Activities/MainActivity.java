package pt.ipp.estg.formulafan.Activities;

import android.content.Context;
import android.content.Intent;
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

import pt.ipp.estg.formulafan.Activities.FormulaFanMainActivity;
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
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(context, FormulaFanMainActivity.class);
                            startActivity(intent);
                            Toast.makeText(context, "Login Success!",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, "Authentication failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            fragmentManager.popBackStack();
                            Toast.makeText(context, "Registration completed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean validateForm(EditText insertEmailField,
                                EditText insertPassField) {
        boolean valid = true;

        String email = insertEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            insertEmailField.setError("Required!");
            valid = false;
        } else {
            insertEmailField.setError(null);
        }

        String password = insertPassField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            insertPassField.setError("Required!");
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