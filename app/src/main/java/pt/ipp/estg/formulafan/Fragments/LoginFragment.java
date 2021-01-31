package pt.ipp.estg.formulafan.Fragments;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.card.MaterialCardView;

import pt.ipp.estg.formulafan.Activities.MainActivity;
import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.Utils.InternetUtil;

public class LoginFragment extends Fragment {

    private Context context;

    private EditText insertEmailField;
    private EditText insertPassField;
    private TextView welcomeView;
    private TextView orView;
    private ImageView logo;
    private Button signInButton;
    private Button registerButton;
    private MaterialCardView card;

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

        logo = view.findViewById(R.id.imageView);
        card = view.findViewById(R.id.materialCardView);
        welcomeView = view.findViewById(R.id.welcomeView);
        orView = view.findViewById(R.id.orView);
        insertEmailField = view.findViewById(R.id.editTextTextEmailAddress);
        insertPassField = view.findViewById(R.id.editTextTextPassword);
        signInButton = view.findViewById(R.id.logInButton);
        registerButton = view.findViewById(R.id.buttonRegister);

        InternetUtil internetUtil = new InternetUtil((Application) context.getApplicationContext());
        internetUtil.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
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
                if (((MainActivity) context).validateForm(insertEmailField, insertPassField) == true) {
                    String email = insertEmailField.getText().toString();
                    String password = insertPassField.getText().toString();
                    ((MainActivity) context).signIn(email, password);
                } else {
                    Toast.makeText(context, "Preencha todos os dados!",
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

        checkDarkMode();

        return view;
    }

    private void checkDarkMode(){
        Configuration config = getResources().getConfiguration();
        Drawable new_image;

        int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                new_image= getResources().getDrawable(R.drawable.imageonline_co_whitebackgroundremoved,
                        context.getTheme());
                card.setCardBackgroundColor(Color.WHITE);
                logo.setBackground(new_image);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                new_image= getResources().getDrawable(R.drawable.output_onlinepngtools__1_,
                        context.getTheme());
                logo.setBackground(new_image);
                welcomeView.setTextColor(Color.WHITE);
                card.setCardBackgroundColor(Color.DKGRAY);
                orView.setTextColor(Color.WHITE);
                break;
        }
    }
}