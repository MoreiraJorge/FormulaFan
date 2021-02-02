package pt.ipp.estg.formulafan.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import pt.ipp.estg.formulafan.R;

public class PieChartFragment extends Fragment {
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
        generatePie(5,4,pieChart);
        return view;
    }

    private void generatePie(float correct, float wrong, PieChart pie){

        ArrayList<PieEntry> answers = new ArrayList<>();
        answers.add(new PieEntry(correct, "Corretas"));
        answers.add(new PieEntry(wrong, "Erradas"));

        PieDataSet pieDataSet = new PieDataSet(answers,"");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        pie.setUsePercentValues(false);

        PieData pieData = new PieData(pieDataSet);

        Legend l = pie.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pie.setData(pieData);
        pie.getDescription().setEnabled(false);
        pie.setCenterText("Respostas");
        pie.setRotationEnabled(false);

        pie.invalidate();
    }

}