package com.example.lordofthegames.home

//import com.example.lordofthegames.Database.LOTGDAO
//import com.example.lordofthegames.Database.LOTGRepository
//import com.example.lordofthegames.db_entities.Game

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.GameDetails.GameDetActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.recyclerView.CardAdapter
import com.example.lordofthegames.recyclerView.CategoryCardItem
import com.example.lordofthegames.recyclerView.CategoryCardViewHolder
import com.example.lordofthegames.recyclerView.GameCardItem
import com.example.lordofthegames.recyclerView.OnItemListener
import com.example.lordofthegames.recyclerView.PlatformCardItem


class HomeFragment: Fragment(), OnItemListener {


    private var gameItems: MutableList<GameCardItem> = listOf(
        GameCardItem("ic__search_white_24", "Spado spado uccidi uccidi"),
        GameCardItem("ic_menu_24dp",        "Sparo sparo uccidi uccidi"),
        GameCardItem("ic_t_pose",           "Matel Gear Rising: Revengence"),
        GameCardItem("ic_t_pose",           "Dark Souls 3"),
        GameCardItem("ic_t_pose",           "MARVEL Spider-Man"),
        GameCardItem("ic_t_pose",           "Bloodborne"),
        GameCardItem("ic_t_pose",           "God of War: Ragnarok"),
        GameCardItem("ic_t_pose",           "Gabibbo"), // */
        GameCardItem("yee",                 "Dark Souls 3"),
        GameCardItem("yee",                 "MARVEL Spider-Man"),
        GameCardItem("gabibbo",             "Dark Souls 3"),
        GameCardItem("gabibbo",             "MARVEL Spider-Man"),
        GameCardItem("gabibbo",             "Horizon Zero Dawn: Forbidden West"),
        GameCardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN"),
        GameCardItem("yee",                 "Gabibbo BELAAAAAAAAAN"),
        GameCardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN"),
        GameCardItem("yee",                 "Gabibbo BELAAAAAAAAAN"),
        GameCardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN"),
        GameCardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN"),
        GameCardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN"),
        GameCardItem("gabibbo",             "Dark Souls 3"),
        GameCardItem("yee",                 "MARVEL Spider-Man"),
        GameCardItem("gabibbo",             "Bloodborne"),
        GameCardItem("gabibbo",             "Dark Souls 3"),
        GameCardItem("yee",                 "MARVEL Spider-Man"),
        GameCardItem("gabibbo",             "Bloodborne"),
    ) as MutableList<GameCardItem>


    private var adapter: CardAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeViewModel: HomeViewModel

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
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: Activity? = activity
        if(activity != null) {
            setRecyclerView(activity)

            //this.homeViewModel.getCurrentUser("")?.observe(viewLifecycleOwner){ user -> print(user.toString()) }
            //val repository = UserRepo(UserDAO, activity.application)
            //val cardItems: LiveData<List<Game>> = repository.getGame()
            //print(cardItems)

            //val db: LOTGDatabase = Room.databaseBuilder(
            //    requireContext(),
            //    LOTGDatabase::class.java,
            //    "lotgdb"
            //).build()


            homeViewModel.getAllGameSimpleDet().value?.forEach { e -> Log.w("GIUCO", e.toString()) }
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
        val catItems: MutableList<CategoryCardItem> = listOf(CategoryCardItem("GDR"), CategoryCardItem("Terza persona"), CategoryCardItem("JRPG"), CategoryCardItem("JRPG"), CategoryCardItem("JRPG"), CategoryCardItem("JRPG"), CategoryCardItem("JRPG")) as MutableList<CategoryCardItem>
        val platItems: MutableList<PlatformCardItem> = listOf(PlatformCardItem("PS4", Color.rgb(19, 44, 116)), PlatformCardItem("STEAM", Color.rgb(41, 41, 41)), PlatformCardItem("EPIC", Color.rgb(58, 58, 56)), PlatformCardItem("XBOX ONE", Color.rgb(24, 128, 24)), PlatformCardItem("Game Pass", Color.rgb(24, 128, 24)), PlatformCardItem("Nintendo", Color.rgb(231, 8, 25))) as MutableList<PlatformCardItem>

        recyclerView = act.findViewById(R.id.recycler_view)
        val listener: OnItemListener = this
        adapter = CardAdapter(listener, gameItems, catItems, platItems, act)
        val gridLayout = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = gridLayout
        recyclerView.adapter = adapter
        val itemCount: Int = (adapter!!.itemCount) as Int

        for(i in 0 until itemCount){
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as? CategoryCardViewHolder
            viewHolder?.let {
                val catList: MutableList<TextView> = listOf<TextView>() as MutableList<TextView>
                val platList: MutableList<TextView> = listOf<TextView>() as MutableList<TextView>

                val listCat = it.itemView.findViewById<LinearLayout>(R.id.category_linear_home)
                listCat.removeAllViews()
                val listPlat = it.itemView.findViewById<LinearLayout>(R.id.platform_linear_home)
                listPlat.removeAllViews()
                // Ora puoi aggiungere TextView dinamicamente al LinearLayout

                catItems.forEach{ x ->
                    val t = TextView(requireContext())
                    t.text = x.category_name
                    viewHolder.catTitle.text = x.category_name
                    catList.add(t)
                }

                platItems.forEach{ x ->
                    val t = TextView(requireContext())
                    t.text = x.platFormName
                    platList.add(t)
                }

                catList.forEach{
                        el -> listCat.addView(el)
                }

                platList.forEach{
                        el -> listPlat.addView(el)
                }

            }
        }
    }

    override fun onItemClick(view: View, position: Int) {
        val act: Activity? = activity
        if(act != null){

            val intent = Intent(context, GameDetActivity::class.java)
            intent.putExtra("game_cover", gameItems[position].imageResource)
            intent.putExtra("game_title", gameItems[position].gameTitle)
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




}



























