package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import pt.ipp.estg.formulafan.R;

public class BarChartFragment extends Fragment {

    private BarChart barChart;
    private Context context;

    public BarChartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bar_chart, container, false);
        barChart = view.findViewById(R.id.barChart);
        setData();
        return view;
    }

    private void setData() {
        int quizDone = 10;
        int quizMiss = 2;

        ArrayList<BarEntry> values1 = new ArrayList<>();
        values1.add(new BarEntry(1, quizDone));

        ArrayList<BarEntry> values2 = new ArrayList<>();
        values2.add(new BarEntry(2, quizMiss));

        BarDataSet set1;
        set1 = new BarDataSet(values1, "Quizzes feitos");
        set1.setDrawIcons(false);
        set1.setColors(Color.rgb(0, 204, 102));

        BarDataSet set2;
        set2 = new BarDataSet(values2, "Quizzes por fazer");
        set2.setDrawIcons(false);
        set2.setColors(Color.rgb(255, 214, 51));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        data.setHighlightEnabled(false);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);

        barChart.setTouchEnabled(true);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        barChart.invalidate();
    }
}