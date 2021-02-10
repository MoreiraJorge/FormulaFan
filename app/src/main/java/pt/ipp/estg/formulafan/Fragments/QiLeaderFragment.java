package pt.ipp.estg.formulafan.Fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.ViewModels.UserInfoViewModel;

public class QiLeaderFragment extends Fragment {

    private RecyclerView leaderBoard;
    private QiLeaderFragmentAdapter qiLeaderFragmentAdapter;
    private Context context;
    private TextView userLeaderBoardName;
    private TextView userLeaderBoardQi;

    public QiLeaderFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_qi_leader, container, false);
        leaderBoard = view.findViewById(R.id.leaderList);
        userLeaderBoardName = view.findViewById(R.id.leaderUserNameView);
        userLeaderBoardQi = view.findViewById(R.id.userLeaderboardQi);

        UserInfoViewModel userInfoViewModel =
                new ViewModelProvider(this,
                        new ViewModelProvider.AndroidViewModelFactory((Application) getActivity()
                                .getApplicationContext())).get(UserInfoViewModel.class);
        userInfoViewModel.insertAllInfo();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userInfoViewModel.getUserInfo(email).observe(this, (user) -> {
            userLeaderBoardQi.setText(String.valueOf(user.qi));
            userLeaderBoardName.setText(user.userName);
        });

        userInfoViewModel.getAllInfo().observe(this, (users) -> {
                    if (users != null) {

                        qiLeaderFragmentAdapter =
                                new QiLeaderFragmentAdapter();
                        qiLeaderFragmentAdapter.setLeaderBoardUserList(users);
                        leaderBoard.setAdapter(qiLeaderFragmentAdapter);

                        leaderBoard.setLayoutManager(new LinearLayoutManager(context));
                        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                        leaderBoard.addItemDecoration(itemDecoration);
                    }
                }
        );

        return view;
    }
}