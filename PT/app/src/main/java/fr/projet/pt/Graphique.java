package fr.projet.pt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graphique extends AppCompatActivity {


    BarChart barChart;



    // variable for our bar data set.
    BarDataSet barDataSet1, barDataSet2,barDataSet3;

    // array list for storing entries.
    ArrayList barEntries=new ArrayList<>();

    // creating a string array for displaying days.
    String[] days;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphique);
        Intent i=getIntent();

        System.out.println(i);
        days=i.getStringArrayExtra("date");








        System.out.println("dans graph");





        barChart=findViewById(R.id.graph);



        barDataSet1 = new BarDataSet(getBarVitesse(),"Vitesse");
        System.out.println("dans graph");


        barDataSet1.setColor(Color.RED);
        barDataSet2 = new BarDataSet(getBarDistance(), "Distance");

        barDataSet3 = new BarDataSet(getBarNote(), "Note");
        barDataSet2.setColor(Color.BLUE);
        barDataSet3.setColor(Color.YELLOW);


        // below line is to add bar data set to our bar data.
        BarData data = new BarData(barDataSet1, barDataSet2,barDataSet3);

        // after adding data to our bar data we
        // are setting that data to our bar chart.
        barChart.setData(data);

        // below line is to remove description
        // label of our bar chart.
        barChart.getDescription().setEnabled(false);

        // below line is to get x axis
        // of our bar chart.
        XAxis xAxis = barChart.getXAxis();

        // below line is to set value formatter to our x-axis and
        // we are adding our days to our x axis.
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));

        // below line is to set center axis
        // labels to our bar chart.
        xAxis.setCenterAxisLabels(true);

        // below line is to set position
        // to our x-axis to bottom.
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // below line is to set granularity
        // to our x axis labels.
        xAxis.setGranularity(1);

        // below line is to enable
        // granularity to our x axis.
        xAxis.setGranularityEnabled(true);

        // below line is to make our
        // bar chart as draggable.
        barChart.setDragEnabled(true);

        // below line is to make visible
        // range for our bar chart.
        barChart.setVisibleXRangeMaximum(3);

        // below line is to add bar
        // space to our chart.
        float barSpace = 0.1f;

        // below line is use to add group
        // spacing to our bar chart.
        float groupSpace = 0.5f;

        // we are setting width of
        // bar in below line.
        data.setBarWidth(0.15f);

        // below line is to set minimum
        // axis to our chart.
        barChart.getXAxis().setAxisMinimum(0);

        // below line is to
        // animate our chart.
        barChart.animate();

        // below line is to group bars
        // and add spacing to it.
        barChart.groupBars(0, groupSpace, barSpace);

        // below line is to invalidate
        // our bar chart.
        barChart.invalidate();
    }

    // array list for first set
    private ArrayList<BarEntry> getBarVitesse() {


        int tmp=0;
        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        while(Historique.valeur[tmp]!=null) {
            barEntries.add(new BarEntry(tmp, Float.parseFloat(Historique.valeur[tmp])));
            tmp++;
        }
        return barEntries;
    }

    // array list for second set.
    private ArrayList<BarEntry> getBarDistance() {

        // creating a new array list
        barEntries = new ArrayList<>();
        int tmp=0;

        while(Historique.valeur_distance[tmp]!=null) {
            barEntries.add(new BarEntry(tmp, Float.parseFloat(Historique.valeur_distance[tmp])));
            tmp++;
        }
        return barEntries;
    }

    private ArrayList<BarEntry> getBarNote() {

        // creating a new array list
        barEntries = new ArrayList<>();
        int tmp=0;

        while(Historique.valeur_note[tmp]!=null) {
            barEntries.add(new BarEntry(tmp, Float.parseFloat(Historique.valeur_note[tmp])));
            tmp++;
        }
        return barEntries;
    }

    public String deleteChar(String activite){
        activite = activite.replaceAll("[ +^\" ]","");
        activite = activite.replaceAll("\\\\", "");
        activite = activite.replaceAll("\\[","");
        activite = activite.replaceAll("]",",");

        return activite;
    }





}