package pt.ipp.estg.formulafan.Fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pt.ipp.estg.formulafan.Interfaces.IQuizHistoryListener;
import pt.ipp.estg.formulafan.Interfaces.IQuizLeaderListener;
import pt.ipp.estg.formulafan.Interfaces.IStatisticsListener;
import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.ViewModels.UserInfoViewModel;

public class ProfileFragment extends Fragment {

    private IStatisticsListener statisticsListener;
    private IQuizHistoryListener quizHistoryListener;
    private IQuizLeaderListener quizLeaderListener;
    private Button statButton;
    private Button quizzHistoryButton;
    private Button quizLeaderBoardButton;
    private TextView userNameView;
    private TextView userQiView;
    private TextView userEmailView;

    public ProfileFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.statisticsListener = (IStatisticsListener) context;
        this.quizHistoryListener = (IQuizHistoryListener) context;
        this.quizLeaderListener = (IQuizLeaderListener) context;
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
        quizzHistoryButton = view.findViewById(R.id.quizzHistoryButton);
        quizLeaderBoardButton = view.findViewById(R.id.quizzLeadersButton);
        userEmailView = view.findViewById(R.id.emailViewProfile);
        userNameView = view.findViewById(R.id.userNameView);
        userQiView = view.findViewById(R.id.qiPointsView);

        String email = getActivity().getIntent().getExtras().getString("USER_MAIL");

        UserInfoViewModel userInfoViewModel =
                new ViewModelProvider(this,
                        new ViewModelProvider.AndroidViewModelFactory((Application) getActivity()
                                .getApplicationContext())).get(UserInfoViewModel.class);

        userInfoViewModel.getUserInfo(email).observe(this, (user) -> {
                    if (user != null) {
                        userNameView.setText(user.userName);
                        userEmailView.setText(user.email);
                        userQiView.setText(String.valueOf(user.qi));
                    }
                }
        );

        if (statButton != null) {
            statButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    statisticsListener.changeToStatistics();
                }
            });
        }

        quizzHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizHistoryListener.changeToQuizHistory();
            }
        });

        quizLeaderBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizLeaderListener.changeToQuizLeaderBoard();
            }
        });

        return view;
    }
}