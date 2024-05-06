package com.example.lordofthegames.user_login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.lordofthegames.databinding.FragmentLoggedinBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener


class LoggedInFragment: Fragment() , SeekBar.OnSeekBarChangeListener,
    OnChartValueSelectedListener {

    private lateinit var bind: FragmentLoggedinBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentLoggedinBinding.inflate(layoutInflater, container, false);
        return bind.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        bind.btnExit.setOnClickListener { eschilo() }

        bind.piechart.setUsePercentValues(true);
        bind.piechart.description.isEnabled = false;
        bind.piechart.setExtraOffsets(5.0F, 10.0F, 5.0F, 5.0F);
        bind.piechart.dragDecelerationFrictionCoef = 0.95f;
        //bind.piechart.setCenterTextTypeface(tfLight);
        bind.piechart.isDrawHoleEnabled = true;
        bind.piechart.setHoleColor(Color.WHITE);
        bind.piechart.setTransparentCircleColor(Color.WHITE);
        bind.piechart.setTransparentCircleAlpha(110);
        bind.piechart.holeRadius = 58f;
        bind.piechart.transparentCircleRadius = 61f;
        bind.piechart.setDrawCenterText(true);
        bind.piechart.rotationAngle = 0.0F;
        bind.piechart.isRotationEnabled = true;
        bind.piechart.isHighlightPerTapEnabled = true;
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
        bind.piechart.setEntryLabelColor(Color.WHITE)
        bind.piechart.setEntryLabelTextSize(12f)


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