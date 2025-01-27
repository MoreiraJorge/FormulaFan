package pt.ipp.estg.formulafan.Fragments;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.ViewModels.UserInfoViewModel;

public class StatisticFragment extends Fragment {
    private ViewPager2 pager;

    private UserInfoViewModel userInfoViewModel;
    private String userMail;
    private int correct;
    private int wrong;
    private int done;

    public StatisticFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        pager = view.findViewById(R.id.pager);
        pager.setSaveEnabled(false);

        userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        userInfoViewModel =
                new ViewModelProvider(this,
                        new ViewModelProvider.AndroidViewModelFactory((Application) getActivity().getApplicationContext())).get(UserInfoViewModel.class);

        userInfoViewModel.getUserInfo(userMail).observe(this, (user) -> {

            if (user != null) {
                done = user.quizesDone;
                correct = user.correctAnswers;
                wrong = user.wrongAnsers;

                ArrayList<Fragment> listOfFragments = new ArrayList<>();

                listOfFragments.add(new PieChartFragment(correct, wrong));
                listOfFragments.add(new BarChartFragment(done));

                StatisticPagerAdapter adapter =
                        new StatisticPagerAdapter(getChildFragmentManager(),
                                getLifecycle(),
                                listOfFragments);

                pager.setAdapter(adapter);

                TabLayout tabLayout = view.findViewById(R.id.tab_layout);
                new TabLayoutMediator(tabLayout, pager,
                        (tab, position) -> tab.setText(tabTitle(position))
                ).attach();
            }

        });

        return view;
    }

    private String tabTitle(int pos) {
        return (pos == 0) ? "Repostas" : "Quizzes";
    }
}