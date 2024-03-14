package com.example.lordofthegames.home

//import com.example.lordofthegames.Database.LOTGDAO
//import com.example.lordofthegames.Database.LOTGRepository
//import com.example.lordofthegames.db_entities.Game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.GameDetails.GameDetActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.recyclerView.CardAdapter
import com.example.lordofthegames.recyclerView.CategoryCardItem
import com.example.lordofthegames.recyclerView.GameCardItem
import com.example.lordofthegames.recyclerView.OnItemListener
import com.example.lordofthegames.recyclerView.PlatformCardItem


class HomeFragment: Fragment(), OnItemListener {

    private var gameItems: MutableList<GameCardItem> = mutableListOf()

    private val gameItemsss: MutableList<GameCardItem> = listOf(
        GameCardItem("ic__search_white_24", "Spado spado uccidi uccidi", 1),
        GameCardItem("ic_menu_24dp", "Sparo sparo uccidi uccidi", 2),
        GameCardItem("yee", "MARVEL Spider-Man", 15),
    ) as MutableList<GameCardItem>

    private val catItems: MutableList<CategoryCardItem> = listOf(
        CategoryCardItem("GDR"),
        CategoryCardItem("Terza persona"),
        CategoryCardItem("JRPG"),
    ) as MutableList<CategoryCardItem>

    private val platItems: MutableList<PlatformCardItem> = listOf(
        PlatformCardItem("PS4", Color.rgb(19, 44, 116)),
        PlatformCardItem("STEAM", Color.rgb(41, 41, 41)),
        PlatformCardItem("EPIC", Color.rgb(58, 58, 56)),
        PlatformCardItem("XBOX ONE", Color.rgb(24, 128, 24)),
        PlatformCardItem("Game Pass", Color.rgb(24, 128, 24)),
        PlatformCardItem("Nintendo", Color.rgb(231, 8, 25))
    ) as MutableList<PlatformCardItem>

    private var adapter: CardAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeViewModel: HomeViewModel

    private var categoriesdb: MutableList<CategoryCardItem> = mutableListOf()
    private var platformdb: MutableList<PlatformCardItem> = mutableListOf()

    private lateinit var filterFrameLayout: FrameLayout //TODO: AVERE IL DB PER POTER FILTRARE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        ///userViewModel.addItem(User("", "", ""))
        filterFrameLayout = view.findViewById(R.id.filter_home)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //homeViewModel = ViewModelProvider((activity as ViewModelStoreOwner?)!!)[HomeViewModel::class.java]
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
            //this.homeViewModel.getCurrentUser("")?.observe(viewLifecycleOwner){ user -> print(user.toString()) }
            //val repository = UserRepo(UserDAO, activity.application)

            //print(cardItems)
            //homeViewModel.getAllGameSimpleDet().value?.forEach { el ->
            //    Log.w("PORCADDIO", el.toString())
            //}

            //homeViewModel = ViewModelProvider((activity as ViewModelStoreOwner?)!!).get(
            //    HomeViewModel::class.java
            //)
            //(activity as LifecycleOwner?)?.let {
            //    homeViewModel.getAllGameSimpleDet()
            //        .observe(it) { el ->
            //            el?.forEach { e ->
            //                if (e != null) {
            //                    val p = gameListdb.add(GameCardItem(e.game_cover, e.game_title))
            //                    Log.w("LISTONE", p.toString())
            //                }
            //            }
            //            //adapter.setData(cardItems)
            //        }
            //}

            //val r = homeViewModel.getAllGame().value
            //Log.w("CIAONE", r?.size.toString())



            //Log.w("PORCADDIO", gameListdb.size.toString())

            //val db: LOTGDatabase = Room.databaseBuilder(
            //    requireContext(),
            //    LOTGDatabase::class.java,
            //    "lotgdb"
            //).build()

            //db.lotgdao().getAllGameSimpleDet().value?.forEach {
            //        e -> Log.e("PORCADDIO", e.toString())
            //}

            //val db: LOTGDatabase = Room.databaseBuilder(
            //    requireContext(),
            //    LOTGDatabase::class.java,
            //    "lotgdb"
            //).build()

            //val userViewModel by viewModels<UserViewModel> {
            //    UserViewModelFactory((application as UserApplication).repository)
            //}
            //userViewModel.addItem(User("", "", ""))
            //val d = db.userDao().getItems()

        } else {
            Log.e("HomeFragment", "Activity is null")
        }
    }

    private fun setRecyclerView(act: Activity) {

        recyclerView = act.findViewById(R.id.recycler_view)
        val listener: OnItemListener = this



        //val newGameList: MutableList<GameCardItem> = mutableListOf()
        //homeViewModel.getAllGameSimpleDet().observe(viewLifecycleOwner){
        //    list ->
        //    if(list.isNotEmpty()){
        //        list.forEach { e -> newGameList.add(GameCardItem(e.game_cover, e.game_title)) }
        //        //adapter = CardAdapter(listener, newGameList, catItems, platItems, act)
        //    }
        //}
//
//
        //(context as LifecycleOwner?)?.let {
        //    //QUesta è eseguitra dopo che è stato inizilizzato tutto quindi credo si possano generare i GameCardItem per ogni elemento ottenuto e poi sistemare l'adapter
        //    homeViewModel.getAllGameSimpleDet().observe(it) { el ->
        //        if(el.isNotEmpty()){
        //            el?.forEach { e ->
        //                Log.w("LISTONE", e.game_title)
        //                gameListdb.add(GameCardItem(e.game_cover, e.game_title))
        //            }
        //        } else {
        //            Log.w("LISTONE", "VUOTP")
        //        }
        //        //adapter.setData(cardItems)
        //    }
        //}
//
        //Log.w("Giuochi1", gameListdb.toString()); Log.w("Giuochi2", newGameList.toString())
//
//
        //gameItems.addAll(newGameList)
        //(context as LifecycleOwner?)?.let {
        //    homeViewModel.getSIMP()
        //}

        gameItems.addAll(homeViewModel.getSIMP())
        adapter = CardAdapter(listener, homeViewModel, homeViewModel.getSIMP(), act)
        val gridLayout = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = gridLayout
        recyclerView.adapter = adapter
        val itemCount: Int = gameItems.size

        /*if(itemCount > 0){
            for (i in 0 until itemCount) {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as? CategoryCardViewHolder
                viewHolder?.let {
                    val catList: MutableList<TextView> = listOf<TextView>() as MutableList<TextView>
                    val platList: MutableList<TextView> = listOf<TextView>() as MutableList<TextView>

                    val listCat = it.itemView.findViewById<LinearLayout>(R.id.category_linear_home)
                    listCat.removeAllViews()
                    val listPlat = it.itemView.findViewById<LinearLayout>(R.id.platform_linear_home)
                    listPlat.removeAllViews()
                    // Ora puoi aggiungere TextView dinamicamente al LinearLayout

                    catItems.forEach { x ->
                        val t = TextView(requireContext())
                        t.text = x.category_name
                        viewHolder.catTitle.text = x.category_name
                        catList.add(t)
                    }

                    platItems.forEach { x ->
                        val t = TextView(requireContext())
                        t.text = x.platFormName
                        platList.add(t)
                    }

                    catList.forEach { el ->
                        listCat.addView(el)
                    }

                    platList.forEach { el ->
                        listPlat.addView(el)
                    }

                }
            }
        }*/

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
                return false
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
        val filteredList: MutableList<GameCardItem> = ArrayList<GameCardItem>()
        // Filtra la lista in base alla query di ricerca
        for (game in gameItems) {
            if (game.gameTitle.contains(query, ignoreCase = true)) {
                filteredList.add(game)
            }
        }

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
        //            //adapter = CardAdapter(listener, newGameList, catItems, platItems, act)
        //        }
        //}
        //Log.w("GiuochiMale", newGameList.toString())
        this.filterData("")


    }


    override fun onResume() {
        super.onResume()

        val listener: OnItemListener = this
        adapter = CardAdapter(listener, homeViewModel, homeViewModel.getSIMP(), requireActivity())
        recyclerView.adapter = adapter
    }




}



























