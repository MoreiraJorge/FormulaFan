package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import pt.ipp.estg.formulafan.Models.LeaderBoardUser;
import pt.ipp.estg.formulafan.Models.QuizDone;
import pt.ipp.estg.formulafan.R;

public class QuizLeaderFragment extends Fragment {

    private RecyclerView leaderBoard;
    private QuizLeaderFragmentAdapter quizLeaderFragmentAdapter;
    private Context context;
    private TextView userLeaderBoardName;
    private TextView userLeaderBoardQi;

    public QuizLeaderFragment() {
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
        View view = inflater.inflate(R.layout.fragment_quiz_leader, container, false);
        leaderBoard = view.findViewById(R.id.leaderList);
        userLeaderBoardName = view.findViewById(R.id.leaderUserNameView);
        userLeaderBoardQi = view.findViewById(R.id.userLeaderboardQi);

        List<LeaderBoardUser> leaderBoardUsers = temporaryUsers(100);

        //os dados deste user depois sao enviados pelos args nos fragments
        LeaderBoardUser myUser = new LeaderBoardUser("My User",543);
        userLeaderBoardQi.setText(String.valueOf(myUser.Qi));
        userLeaderBoardName.setText(myUser.UserName);

        quizLeaderFragmentAdapter =
                new QuizLeaderFragmentAdapter();
        quizLeaderFragmentAdapter.setLeaderBoardUserList(leaderBoardUsers);
        leaderBoard.setAdapter(quizLeaderFragmentAdapter);

        leaderBoard.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        leaderBoard.addItemDecoration(itemDecoration);

        return view;
    }

    /**
     * Objetos LeaderBoardUser para testar a UI
     * (Apagar mais tarde)
     *
     * @param numOfusers
     * @return
     */
    private List<LeaderBoardUser> temporaryUsers(int numOfusers) {
        List<LeaderBoardUser> temporaryList = new ArrayList<>();
        Random rd = new Random();

        for (int i = 0; i < numOfusers; i++) {
            temporaryList.add(new LeaderBoardUser("User" + i,rd.nextInt(1000)));
        }
        temporaryList.add(new LeaderBoardUser("My User", 543));

        Collections.sort(temporaryList, new Comparator<LeaderBoardUser>() {
            @Override
            public int compare(LeaderBoardUser o1, LeaderBoardUser o2) {
                if(o1.Qi < o2.Qi){
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        return temporaryList;
    }
}