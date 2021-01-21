package pt.ipp.estg.formulafan;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import pt.ipp.estg.formulafan.Fragments.RaceFragment;
import pt.ipp.estg.formulafan.Utils.InternetUtil;


public class MainActivity extends AppCompatActivity {

    private Context context;
    private FragmentManager fragmentManager;

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

        RaceFragment raceFragment = new RaceFragment();
        raceFragment.setArguments(getIntent().getExtras());

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, raceFragment);
        fragmentTransaction.commit();
    }
}