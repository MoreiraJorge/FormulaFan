package pt.ipp.estg.formulafan.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import pt.ipp.estg.formulafan.Fragments.LiveQuizFragment;
import pt.ipp.estg.formulafan.R;

public class LiveQuizActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private LiveQuizFragment liveQuizFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        liveQuizFragment = new LiveQuizFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentQuizContainer, liveQuizFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}