package pt.ipp.estg.formulafan.Fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import pt.ipp.estg.formulafan.Activities.MainActivity;
import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.Utils.InternetUtil;

public class RegisterFragment extends Fragment {
    private Context context;

    private EditText registerMail;
    private EditText registerPass;
    private Button registerButton;

    public RegisterFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        registerMail = view.findViewById(R.id.registerEmail);
        registerPass = view.findViewById(R.id.registerPassword);
        registerButton = view.findViewById(R.id.buttonAccountRegister);

        InternetUtil internetUtil = new InternetUtil((Application) context.getApplicationContext());
        internetUtil.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    registerButton.setEnabled(true);
                } else {
                    registerButton.setEnabled(false);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) context).validateForm(registerMail, registerPass) == true) {
                    String email = registerMail.getText().toString();
                    String password = registerPass.getText().toString();
                    ((MainActivity) context).register(email, password);
                } else {
                    Toast.makeText(context, "Preencha todos os dados!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}