package pt.ipp.estg.formulafan.Fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pt.ipp.estg.formulafan.MainActivity;
import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.Utils.InternetUtil;

public class LoginFragment extends Fragment {

    private Context context;

    private EditText insertEmailField;
    private EditText insertPassField;
    private Button signInButton;
    private Button registerButton;

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
        registerButton = view.findViewById(R.id.buttonRegister);

        InternetUtil internetUtil = new InternetUtil((Application) context.getApplicationContext());
        internetUtil.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if(isConnected) {
                    signInButton.setEnabled(true);
                    registerButton.setEnabled(true);
                } else {
                    signInButton.setEnabled(false);
                    registerButton.setEnabled(false);
                }
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity) context).validateForm(insertEmailField, insertPassField) == true){
                    String email = insertEmailField.getText().toString();
                    String password = insertPassField.getText().toString();
                    ((MainActivity) context).signIn(email,password);
                } else {
                    Toast.makeText(context, "Input error!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).registerRedirect();
            }
        });

        return view;
    }
}