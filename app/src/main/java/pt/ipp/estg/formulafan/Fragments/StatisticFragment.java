package pt.ipp.estg.formulafan.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import pt.ipp.estg.formulafan.R;

public class StatisticFragment extends Fragment {
    private ViewPager2 pager;

    public StatisticFragment() {
        // Required empty public constructor
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

        ArrayList<Fragment> listOfFragments = new ArrayList<>();
        listOfFragments.add(new PieChartFragment());
        listOfFragments.add(new BarChartFragment());

        StatisticPagerAdapter adapter =
                new StatisticPagerAdapter(requireActivity().getSupportFragmentManager(),
                        getLifecycle(),
                        listOfFragments);

        pager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, pager,
                (tab, position) -> tab.setText(tabTitle(position))
        ).attach();

        return view;
    }

    private String tabTitle(int pos) {
        return (pos == 0) ? "Repostas" : "Quizzes";
    }
}