package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import pt.ipp.estg.formulafan.MainActivity;
import pt.ipp.estg.formulafan.R;


public class LoginFragment extends Fragment {

    private Context context;

    private EditText insertEmailField;
    private EditText insertPassField;
    private Button signInButton;

    public LoginFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        insertEmailField = view.findViewById(R.id.editTextTextEmailAddress);
        insertPassField = view.findViewById(R.id.editTextTextPassword);
        signInButton = view.findViewById(R.id.logInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm() == true){
                    String email = insertEmailField.getText().toString();
                    String password = insertPassField.getText().toString();
                    ((MainActivity) context).signIn(email,password);
                } else {
                    Toast.makeText(context, "Input error!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    /**
     * Method to validate Email
     * and Password Input
     * @return true if forms are valid, otherwise
     * returns false
     */
    private boolean validateForm(){
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
}