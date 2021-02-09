package pt.ipp.estg.formulafan.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import pt.ipp.estg.formulafan.Fragments.QuizFragment;
import pt.ipp.estg.formulafan.R;

public class LiveQuizActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private QuizFragment quizFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Test", "Here");
        setContentView(R.layout.activity_quiz);
        quizFragment = new QuizFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentQuizContainer, quizFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}