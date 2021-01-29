package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pt.ipp.estg.formulafan.MainActivity;
import pt.ipp.estg.formulafan.R;

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

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity) context).validateForm(registerMail, registerPass) == true){
                    String email = registerMail.getText().toString();
                    String password = registerPass.getText().toString();
                    ((MainActivity) context).register(email,password);
                } else {
                    Toast.makeText(context, "Input error!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}