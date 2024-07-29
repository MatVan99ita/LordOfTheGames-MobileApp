package com.example.lordofthegames.home

//import com.example.lordofthegames.Database.LOTGDAO
//import com.example.lordofthegames.Database.LOTGRepository
//import com.example.lordofthegames.db_entities.Game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RadioGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.GameDetails.GameDetActivity
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.recyclerView.CardAdapter
import com.example.lordofthegames.recyclerView.CategoryCardItem
import com.example.lordofthegames.recyclerView.GameCardItem
import com.example.lordofthegames.recyclerView.OnItemListener
import com.example.lordofthegames.recyclerView.PlatformCardItem
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView


class HomeFragment: Fragment(), OnItemListener {


    private var adapter: CardAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    private var bundle: Bundle? = null

    private var gameItems:    MutableList<GameCardItem>     = mutableListOf()
    private var categoriesdb: MutableList<CategoryCardItem> = mutableListOf()
    private var platformdb:   MutableList<PlatformCardItem> = mutableListOf()
    private var gheimerzz:    MutableList<GameItem>         = mutableListOf()
    private var gheimerzzReversed: Boolean = false
    private var filterGheimerzz: MutableList<GameItem> = mutableListOf()

    private lateinit var filterFrameLayout: FrameLayout //TODO: AVERE IL DB PER POTER FILTRARE <- OBBLIGATORIA

    private lateinit var su_giu: Button
    private lateinit var radioHead: RadioGroup
    private lateinit var plat_btn: Button
    private lateinit var cat_btn: Button
    private lateinit var radioStar: RadioGroup
    private lateinit var btn_canc: Button
    private lateinit var btn_save: Button
    val listener: OnItemListener = this



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        filterFrameLayout = view.findViewById(R.id.filter_home)
        bundle = arguments
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: Activity? = activity
        if(activity != null) {
            setRecyclerView(activity)
            if(adapter != null){
                displayGame()
            }

            val banana = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val navigationView = requireActivity().findViewById<View>(R.id.nav_view) as NavigationView
            val headerView = navigationView.getHeaderView(0)

            val close_btn: MaterialButton = requireActivity().findViewById(R.id.close_btn_filter)
            val frameLayout: FrameLayout = requireActivity().findViewById(R.id.second_filter)
            close_btn.setOnClickListener{
                frameLayout.visibility =
                    if(frameLayout.isVisible) View.GONE else View.VISIBLE
            }

            Utilities.setDrawerWithUser(
                requireActivity().findViewById<View>(R.id.nav_view) as NavigationView,
                banana.getString("nickname", "BANANA").toString(),
                banana.getString("email", "BANANA").toString(),
                homeViewModel.getUsrImg(banana.getString("email", "BANANA").toString())
            )
            su_giu =    requireActivity().findViewById(R.id.btn_order)
            radioHead = requireActivity().findViewById(R.id.Rgroup)
            plat_btn =  requireActivity().findViewById(R.id.btn_annulla2222)
            cat_btn =   requireActivity().findViewById(R.id.btn_annulla222222)
            radioStar = requireActivity().findViewById(R.id.radio_group2)
            btn_canc =  requireActivity().findViewById(R.id.btn_annulla1)
            btn_save =  requireActivity().findViewById(R.id.btn_salva1)

            btn_canc.setOnClickListener{
                //TODO: cancellare tutte le selezioni dei filtri
                // non lo so -> filterFrameLayout.visibility = View.GONE
            }

            btn_save.setOnClickListener {
                filterGames(listOf(), listOf()) //TODO: aggiungere le cose selezionate
                filterFrameLayout.visibility = View.GONE
            }

            su_giu.setOnClickListener {
                val list: List<GameItem>

                if(filterGheimerzz.isEmpty()) {
                    if (!gheimerzzReversed) {
                        list = gheimerzz.reversed()
                        gheimerzzReversed = true
                    } else {
                        list = gheimerzz
                        gheimerzzReversed = false
                    }
                } else {
                    if (!gheimerzzReversed) {
                        list = filterGheimerzz.reversed()
                        gheimerzzReversed = true
                    } else {
                        list = filterGheimerzz
                        gheimerzzReversed = false
                    }
                }

                adapter =
                    bundle?.let {
                        CardAdapter(
                            listener,
                            homeViewModel,
                            list,
                            requireActivity(),
                            it.getString("email", "sesso"
                            )
                        )
                    }

                recyclerView.adapter = adapter
            }


            val nick_head: TextView = headerView.findViewById(R.id.nickname_header)
            val mail_head: TextView = headerView.findViewById(R.id.mail_header)

            nick_head.text = banana.getString("nickname", "BANANA").toString()
            mail_head.text = banana.getString("email", "BANANA").toString()
            val notification_count = homeViewModel.getNonReadNotificationCount(nick_head.text.toString())

            if(notification_count > 0)
                Utilities.generaNotificaNonSalvabile(
                    requireActivity(),
                    title = "${
                        if (notification_count > 99) "99+" else notification_count 
                    } notifiche non lette",
                    content = "",
                    CHANNEL_ID = MainActivity::class.java.simpleName,
                )
        } else {
            Log.e("HomeFragment", "Activity is null")
        }
    }

    private fun setRecyclerView(act: Activity) {

        recyclerView = act.findViewById(R.id.recycler_view)

        gameItems.addAll(homeViewModel.getSIMP())

        gameItems.forEach {el ->
            gheimerzz.add(
                GameItem(
                    game = el,
                    platform = homeViewModel.getGamePlatform(el.gameTitle),
                    category = homeViewModel.getGameCategory(el.gameTitle),
                )
            )
        }

        adapter =
            bundle?.let { CardAdapter(listener, homeViewModel, gheimerzz, act, it.getString("email", "sesso")) }
        val gridLayout = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = gridLayout
        recyclerView.adapter = adapter
        val itemCount: Int = gameItems.size
    }

    override fun onItemClick(view: View, position: Int) {
        val act: Activity? = activity
        if(act != null){
            val intent = Intent(context, GameDetActivity::class.java)
            intent.putExtra("game_cover", gameItems[position].imageResource)
            intent.putExtra("game_title", gameItems[position].gameTitle)
            intent.putExtra("game_id", gameItems[position].gameId)
            this.startActivity(intent)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_fragment_menu, menu)

        val searchItem = menu.findItem(R.id.search_fragment_item)
        val filterItem = menu.findItem(R.id.filter_fragment_item)
        val searchView: SearchView = searchItem.actionView as SearchView



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterData(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterData(newText)
                return true
            }
        })

        filterItem.setOnMenuItemClickListener {
            filterFrameLayout.visibility = if(filterFrameLayout.isVisible) View.GONE else View.VISIBLE
            true
        }

    }


    private fun filterData(query: String) {

        val filteredList: MutableList<GameItem> = ArrayList()
        // Filtra la lista in base alla query di ricerca
        for (gameItem in gheimerzz) {
            if (gameItem.game.gameTitle.contains(query, ignoreCase = true)) {
                filteredList.add(gameItem)
            }
        }
        filterGheimerzz = filteredList

        // Aggiorna l'adapter con la lista filtrata
        adapter?.setFilter(filteredList)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun displayGame(){
        //val newGameList: MutableList<GameCardItem> = mutableListOf()
        //homeViewModel.getGameSimpleDet().observe(viewLifecycleOwner){
        //    gamlist ->
        //        if(gamlist.isNotEmpty()){
        //            for (el in gamlist){
        //                homeViewModel.getAllGameCategory(el.game_title).observe(viewLifecycleOwner) {
        //                    cattlist ->
        //                        if(cattlist.isNotEmpty()){
        //                            homeViewModel.getGamePlatform(el.game_title).observe(viewLifecycleOwner){
        //                                plattlist -> {
        //                                    if(plattlist.isNotEmpty()){
        //                                        adapter.setData()
        //                                    }
        //                                }
        //                            }
        //                        }
        //                    }
        //                if(l)
        //                adapter.setData(el.game_title, , homeViewModel.getGamePlatform(el.game_title)
//
        //            }
//
        //            list.forEach { e -> newGameList.add(GameCardItem(e.game_cover, e.game_title)) }
        //            //adapter = CardAdadpter(listener, newGameList, catItems, platItems, act)
        //        }
        //}
        //Log.w("GiuochiMale", newGameList.toString())
        this.filterData("")


    }


    override fun onResume() {
        super.onResume()

        val listener: OnItemListener = this
        adapter = CardAdapter(
            listener,
            homeViewModel,
            gheimerzz,
            requireActivity(),
            bundle!!.getString(
                "email",
                "sesso"
            )
        )
        recyclerView.adapter = adapter
    }

    private fun filterGames(categoryFilters: List<String>, platformFilter: List<String>): MutableList<GameItem> {
        /* TODO: ~ Implementare il fitro
                 ~ Mettere dei textview sotto i bottoni plat_btn e cat_btn in modo da poter
                   selezionare solo una categoria e una sola piattaforma (di più sarebbe il caos per ora)
                   oppure fare il filtro con le lambda
                 N.B.: creato List<
                                GameItem(
                                    GameCardItem,
                                    List<PlatformCardItem>,
                                    List<CategoryCardItem>
                                )>
                        filter con select gameItem that in lista plat/cat contains "elemento"
        */
        su_giu
        radioHead
        plat_btn
        cat_btn
        radioStar

        val filteredList: MutableList<GameItem> = ArrayList<GameItem>()
        // Filtra la lista in base al query di categorie/piattaforme
        for (gameItem in gheimerzz) {
        }
        return filteredList
    }




}



























