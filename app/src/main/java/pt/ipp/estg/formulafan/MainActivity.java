package pt.ipp.estg.formulafan;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pt.ipp.estg.formulafan.Fragments.LoginFragment;
import pt.ipp.estg.formulafan.Fragments.RaceFragment;
import pt.ipp.estg.formulafan.Interfaces.ISessionListener;
import pt.ipp.estg.formulafan.Utils.InternetUtil;


public class MainActivity extends AppCompatActivity implements ISessionListener {

    private static final String TAG = "SessionEmailPass";

    private Context context;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = getApplicationContext();

        InternetUtil internetUtil = new InternetUtil(getApplication());
        internetUtil.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if(isConnected) {
                    Toast.makeText(context, "Dispositivo Online!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Dispositivo Offline!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        LoginFragment loginFragment = new LoginFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, loginFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");

                            RaceFragment raceFragment = new RaceFragment();
                            raceFragment.setArguments(getIntent().getExtras());
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                                    .beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentContainer, raceFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                            Toast.makeText(context, "Login Success!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void register(String email, String password) {

    }

    @Override
    public void logout() {

    }
}