package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import pt.ipp.estg.formulafan.Interfaces.IStatisticsListener;
import pt.ipp.estg.formulafan.R;

public class ProfileFragment extends Fragment {

    private IStatisticsListener statisticsListener;
    private Button statButton;

    public ProfileFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.statisticsListener = (IStatisticsListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        statButton = view.findViewById(R.id.statsButton);

        if (statButton != null) {

            statButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    statisticsListener.changeToStatistics();
                }
            });

        }

        return view;
    }

}