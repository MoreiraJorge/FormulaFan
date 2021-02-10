package pt.ipp.estg.formulafan.Fragments;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.card.MaterialCardView;

import pt.ipp.estg.formulafan.Activities.MainActivity;
import pt.ipp.estg.formulafan.Interfaces.IThemeListener;
import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.Utils.InternetUtil;

public class RegisterFragment extends Fragment implements IThemeListener {

    private Context context;

    private EditText registerMail;
    private EditText registerPass;
    private EditText verifyPass;
    private TextView registView;
    private EditText registUserName;
    private Button registerButton;
    private MaterialCardView card;

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

        registView = view.findViewById(R.id.RegistView);
        registerMail = view.findViewById(R.id.registerEmail);
        registerPass = view.findViewById(R.id.registerPassword);
        registUserName = view.findViewById(R.id.registerName);
        verifyPass = view.findViewById(R.id.insertVerifyPass);
        card = view.findViewById(R.id.registerCard);

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
                if (((MainActivity) context).validateForm(registUserName, registerMail, registerPass) == true) {
                    String password = registerPass.getText().toString();
                    String passVerify = verifyPass.getText().toString();
                    if (passVerify.equals(password) == true) {
                        ((MainActivity) context).register(registUserName, registerMail, registerPass);
                    } else {
                        verifyPass.setError("As passwords não coincidem!");
                        Toast.makeText(context, "As passwords não coincidem!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Preencha todos os dados obrigatórios!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkDarkMode();

        return view;
    }

    @Override
    public void checkDarkMode() {
        Configuration config = getResources().getConfiguration();

        int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                registView.setTextColor(Color.BLACK);
                card.setCardBackgroundColor(Color.WHITE);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                card.setCardBackgroundColor(Color.DKGRAY);
                registView.setTextColor(Color.WHITE);
                break;
        }
    }
}
