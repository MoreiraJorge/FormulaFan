package pt.ipp.estg.formulafan.Fragments;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import pt.ipp.estg.formulafan.Interfaces.IThemeListener;
import pt.ipp.estg.formulafan.R;

public class PieChartFragment extends Fragment implements IThemeListener {
    private PieChart pieChart;

    public PieChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        pieChart = view.findViewById(R.id.pieChart);
        generatePie(5, 4, pieChart);
        return view;
    }

    private void generatePie(float correct, float wrong, PieChart pie) {

        ArrayList<PieEntry> answers = new ArrayList<>();
        answers.add(new PieEntry(correct, "Corretas"));
        answers.add(new PieEntry(wrong, "Erradas"));

        PieDataSet pieDataSet = new PieDataSet(answers, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        pie.setUsePercentValues(false);

        PieData pieData = new PieData(pieDataSet);

        pie.setData(pieData);
        pie.getDescription().setEnabled(false);
        pie.setCenterText("Respostas");
        pie.setRotationEnabled(false);

        checkDarkMode();

        pie.invalidate();
    }

    @Override
    public void checkDarkMode() {
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pieChart.setHoleColor(Color.TRANSPARENT);

        Configuration config = getResources().getConfiguration();
        int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                l.setTextColor(Color.BLACK);
                pieChart.setCenterTextColor(Color.BLACK);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                l.setTextColor(Color.WHITE);
                pieChart.setCenterTextColor(Color.WHITE);
                break;
        }
    }
}