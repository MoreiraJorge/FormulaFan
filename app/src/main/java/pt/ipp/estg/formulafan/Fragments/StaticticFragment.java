package pt.ipp.estg.formulafan.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import pt.ipp.estg.formulafan.R;

public class StaticticFragment extends Fragment {
    private ViewPager2 pager;

    public StaticticFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statictic, container, false);
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
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();

        return view;
    }
}