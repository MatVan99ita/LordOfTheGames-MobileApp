package com.example.lordofthegames.user_login

import android.R
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.ViewModel.GameDetViewModel2
import com.example.lordofthegames.databinding.FragmentLoggedinBinding
import com.example.lordofthegames.recyclerView.UserGameGraphItem
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener


class LoggedInFragment: Fragment() , SeekBar.OnSeekBarChangeListener,
    OnChartValueSelectedListener {

    private lateinit var bind: FragmentLoggedinBinding
    private lateinit var viewm: LoggedViewModel
    private lateinit var statistics: UserGameGraphItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentLoggedinBinding.inflate(layoutInflater, container, false);
        viewm = ViewModelProvider(requireActivity())[LoggedViewModel::class.java]
        statistics = viewm.getUserStatisticsCounts()
        return bind.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val chart = bind.piechart
        bind.btnExit.setOnClickListener { eschilo() }

        chart.setUsePercentValues(true);
        chart.description.isEnabled = false;
        chart.setExtraOffsets(5.0F, 10.0F, 5.0F, 5.0F);
        chart.dragDecelerationFrictionCoef = 0.95f;
        //bind.piechart.setCenterTextTypeface(tfLight);
        chart.isDrawHoleEnabled = true;
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.holeRadius = 58f;
        chart.transparentCircleRadius = 61f;
        chart.setDrawCenterText(true);
        chart.rotationAngle = 0.0F;
        chart.isRotationEnabled = true;
        chart.isHighlightPerTapEnabled = true;
        //bind.piechart.setOnChartValueSelectedListener(this);


        val l: Legend = bind.piechart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(12f)

        val values = ArrayList<BarEntry>()
        values.add(
            BarEntry(0f, statistics.gameNumTot as Float)
        )
        values.add(
            BarEntry(0f, statistics.playingTot as Float)
        )
        values.add(
            BarEntry(0f, statistics.planToPlayTot as Float)
        )
        values.add(
            BarEntry(0f, statistics.abandonedTot as Float)
        )
        values.add(
            BarEntry(0f, statistics.completedTot as Float)
        )

        val set1: BarDataSet = chart.data.getDataSetByIndex(0) as BarDataSet
        set1.values = values;

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)
        val data = BarData(dataSets)
        data.setValueTextSize(10f)
        data.barWidth = 0.9f
        chart.setData(data)


    }

    fun eschilo(){
        val sharedPrefs = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.remove("nickname")
        editor.remove("email")
        editor.remove("logged")
        editor.apply()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        TODO("Not yet implemented")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        TODO("Not yet implemented")
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected() {
        TODO("Not yet implemented")
    }

}