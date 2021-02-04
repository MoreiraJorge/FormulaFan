package pt.ipp.estg.formulafan.Fragments;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.formulafan.Models.DriverPosition;
import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.R;

public class DriverPositionRecyclerViewAdapter extends RecyclerView.Adapter<DriverPositionRecyclerViewAdapter.ViewHolder> {

    private List<DriverPosition> driverPositions;

    public DriverPositionRecyclerViewAdapter(Context context) {
        this.driverPositions = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_driver_position, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.driverPosition = driverPositions.get(position);
        String text = "" + holder.driverPosition.position + " - " + holder.driverPosition.driver.givenName
                + " " + holder.driverPosition.driver.familyName + " - " + holder.driverPosition.points + " Pts";
        holder.textView.setText(text);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "Olá!");
            }
        });
    }

    @Override
    public int getItemCount() {
        return driverPositions.size();
    }

    public void setDriverPositionsList(List<DriverPosition> driverPositions) {
        this.driverPositions = driverPositions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView textView;
        public DriverPosition driverPosition;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            textView = view.findViewById(R.id.driverPosition);
        }
    }
}