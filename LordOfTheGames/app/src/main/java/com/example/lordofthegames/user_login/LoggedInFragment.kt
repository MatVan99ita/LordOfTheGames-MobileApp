package com.example.lordofthegames.user_login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentLoggedinBinding
import com.example.lordofthegames.recyclerView.UserGameGraphItem
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.progressindicator.CircularProgressIndicator


class LoggedInFragment: Fragment(){

    private lateinit var bind: FragmentLoggedinBinding
    private lateinit var viewm: LoggedViewModel
    private lateinit var statistics: UserGameGraphItem
    private lateinit var circularProgress: CircularProgressIndicator
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentLoggedinBinding.inflate(layoutInflater, container, false);
        viewm = ViewModelProvider(requireActivity())[LoggedViewModel::class.java]
        statistics = viewm.getUserStatisticsCounts(TODO("METTERE LA MAIL DAL BUNDLE"))
        return bind.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        bind.btnExit.setOnClickListener { eschilo() }
        val banana = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val p: PieChart = bind.chart

        Utilities.setupPieChart(p, statistics, banana.getString("Theme", "NoTheme").equals("Night"))


    }

    fun eschilo(){
        val sharedPrefs = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.remove("nickname")
        editor.remove("email")
        editor.remove("logged")
        editor.apply()
    }
    /* TODO: aggiungere tipo una lista con gli chievement dell'utente come
             giochi completati,
             achievement completati,
             giochi aggiunti alle liste
             giochi abbandonati
             _
             ALLA BATTLE CATS a
             livelli
             legno x10 ->
             ferro x20 ->
             rame x35 ->
             bronzo   V IV III II I -> x40   - 70
             argento  V IV III II I -> x75   - 99
             oro      V IV III II I -> x100  - 700
             diamante V IV III II I -> x1000 - 1300
             smeraldo V IV III II I -> x1400 - 1600
             platino  V IV III II I -> x1700 - 1800
             immortal V IV III II I -> x2000 - e oltre
    */
}