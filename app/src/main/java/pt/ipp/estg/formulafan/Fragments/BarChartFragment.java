package pt.ipp.estg.formulafan.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import pt.ipp.estg.formulafan.Interfaces.IThemeListener;
import pt.ipp.estg.formulafan.R;

public class BarChartFragment extends Fragment implements IThemeListener {

    private BarChart barChart;
    private int quizesDone;

    public BarChartFragment() {
    }

    public BarChartFragment(int quizDone) {
        quizesDone = quizDone;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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

        ArrayList<BarEntry> values1 = new ArrayList<>();
        values1.add(new BarEntry(1, quizesDone));

        ValueFormatter vf = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "" + (int) value;
            }
        };

        BarDataSet set1;
        set1 = new BarDataSet(values1, "Quizzes feitos");
        set1.setDrawIcons(false);
        set1.setColors(Color.rgb(0, 204, 102));
        set1.setValueTextSize(16f);
        set1.setValueFormatter(vf);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setHighlightEnabled(false);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        barChart.setTouchEnabled(true);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftYAxix = barChart.getAxisLeft();
        leftYAxix.setTextSize(14f);
        leftYAxix.setAxisMinimum(0);

        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);
        rightYAxis.setDrawGridLines(false);

        checkDarkMode();

        barChart.invalidate();
    }

    @Override
    public void checkDarkMode() {
        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextSize(14f);

        Configuration config = getResources().getConfiguration();
        int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                l.setTextColor(Color.BLACK);
                barChart.getAxisLeft().setTextColor(Color.BLACK);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                l.setTextColor(Color.WHITE);
                barChart.getAxisLeft().setTextColor(Color.WHITE);
                break;
        }
    }
}