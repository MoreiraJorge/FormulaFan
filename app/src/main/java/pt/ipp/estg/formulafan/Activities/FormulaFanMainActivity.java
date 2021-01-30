package pt.ipp.estg.formulafan.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import pt.ipp.estg.formulafan.R;

public class FormulaFanMainActivity extends AppCompatActivity {

    private Context context;
    private Button logoutButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_fan_main);

        context = this.getApplicationContext();

        firebaseAuth = FirebaseAuth.getInstance();

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}