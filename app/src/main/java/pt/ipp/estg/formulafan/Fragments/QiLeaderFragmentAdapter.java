package pt.ipp.estg.formulafan.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pt.ipp.estg.formulafan.Models.LeaderBoardUser;
import pt.ipp.estg.formulafan.R;

public class QiLeaderFragmentAdapter extends RecyclerView.Adapter<QiLeaderFragmentAdapter.ViewHolder> {

    List<LeaderBoardUser> leaderBoardUserList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View quizzLeaderBoardView = inflater
                .inflate(R.layout.fragment_qi_leader_item, parent, false);
        return new QiLeaderFragmentAdapter.ViewHolder(quizzLeaderBoardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderBoardUser user = this.leaderBoardUserList.get(position);
        holder.userQiView.setText(String.valueOf(user.Qi));
        holder.userLeaderBoardView.setText(user.UserName);
    }

    @Override
    public int getItemCount() {
        return this.leaderBoardUserList.size();
    }

    public void setLeaderBoardUserList(List<LeaderBoardUser> leaderBoardUserList) {
        this.leaderBoardUserList = leaderBoardUserList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView userLeaderBoardView;
        public final TextView userQiView;

        public ViewHolder(View view) {
            super(view);
            userLeaderBoardView = view.findViewById(R.id.leaderName);
            userQiView = view.findViewById(R.id.leaderPoints);
        }
    }
}
