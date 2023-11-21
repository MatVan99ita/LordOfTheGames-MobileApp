package com.example.lordofthegames.home

//import com.example.lordofthegames.Database.LOTGDAO
//import com.example.lordofthegames.Database.LOTGRepository
//import com.example.lordofthegames.db_entities.Game

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.camera.core.impl.utils.ContextUtil.getApplicationContext
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.Database.LotgViewModel
import com.example.lordofthegames.GameDetails.GameDetActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.ViewModel.UserViewModel
import com.example.lordofthegames.db_entities.User
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        ///userViewModel.addItem(User("", "", ""))
        return inflater.inflate(R.layout.fragment_home, container, false)
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
            //    applicationContext,
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
        val catItems: MutableList<CategoryCardItem> = listOf(CategoryCardItem("GDR"), CategoryCardItem("Terza persona"), CategoryCardItem("JRPG") ) as MutableList<CategoryCardItem>
        val platItems: MutableList<PlatformCardItem> = listOf(PlatformCardItem("PS5"), PlatformCardItem("XBOX"), PlatformCardItem("STEAM") ) as MutableList<PlatformCardItem>

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

    override fun onItemClick(position: Int) {
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

        /*val item = menu.findItem(R.id.app_bar_search)
        val searchView: SearchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            /**
             * Called when the user submits the query. This could be due to a key press on the keyboard
             * or due to pressing a submit button.
             * @param query the query text that is to be submitted
             * @return true if the query has been handled by the listener, false to let the
             * SearchView perform the default action.
             */
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            /**
             * Called when the query text is changed by the user.
             * @param newText the new content of the query text field.
             * @return false if the SearchView should perform the default action of showing any
             * suggestions if available, true if the action was handled by the listener.
             */
            override fun onQueryTextChange(newText: String): Boolean {
                //adapter.getFilter().filter(newText);
                return true
            }
        }) // */

    }
}



























