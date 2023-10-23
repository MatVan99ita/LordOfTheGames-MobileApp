package com.example.lordofthegames.GameDetails

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Achievement
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Platform
import com.example.lordofthegames.recyclerView.CardAdapter
import com.example.lordofthegames.recyclerView.CategoryCardAdapter
import com.example.lordofthegames.recyclerView.CategoryCardItem
import com.example.lordofthegames.recyclerView.GameCardItem
import com.example.lordofthegames.recyclerView.OnItemListener
import com.example.lordofthegames.user_login.LoggedViewModel


class GameDetFragment: Fragment(), OnItemListener  {
    private lateinit var imagePath: String
    private var bundle: Bundle? = null
    private lateinit var gameDetViewModel: GameDetViewModel
    private lateinit var achievementList: List<Achievement?>
    private lateinit var categoryList: List<Categories?>
    private lateinit var platformList: List<Platform?>
    private var adapter: CategoryCardAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gameDetViewModel = ViewModelProvider(requireActivity())[GameDetViewModel::class.java]
        //return super.onCreateView(inflater, container, savedInstanceState);
        val game_title = requireActivity().intent.getStringExtra("game_title").toString()
        val gmEx = gameDetViewModel.gameExists(game_title).value

        if (gmEx != null){
            if(!gmEx) {
                achievementList = listOf(Achievement(1, "b", "c", game_ref = 1))
                categoryList = listOf(Categories(1, "b"), Categories(1, "b"), Categories(1, "b"), Categories(1, "b"))
                platformList = listOf(Platform(1, "b"))
            } else {
                achievementList = gameDetViewModel.getGameAchievement(game_title).value!!
                categoryList = gameDetViewModel.getGameCategory(game_title).value!!
                platformList = gameDetViewModel.getGamePlatform(game_title).value!!
            }

        }

        bundle = savedInstanceState

        Log.e("onCreateView", savedInstanceState.toString())
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: Activity? = activity
        if (activity != null) {
            //Utilities.setUpToolBar( activity as AppCompatActivity, arguments?.getString("game_title").toString() )

            //Utilities.setUpDrawer(activity.findViewById(R.id.game_det_drawer), activity, activity.supportActionBar)
            val selectedImage: ImageView = view.findViewById(R.id.selectedImage)

            imagePath = arguments?.getString("game_cover").toString()

            setRecyclerView(activity)
            var drawable: Drawable? = null

            if (imagePath.contains("ic_")){
                drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
            } else if(imagePath.contains("gabibbo")) {
                drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_gabibbo_test", "mipmap", activity.packageName))
            } else if(imagePath.contains("yee")){
                drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_yeee_foreground", "mipmap", activity.packageName))
            }

            selectedImage.setImageDrawable(drawable)

        }
    }

    private fun setRecyclerView(activity: Activity) {
        val catItems: MutableList<CategoryCardItem> = listOf(CategoryCardItem("GDR"), CategoryCardItem("Terza persona"), CategoryCardItem("JRPG") ) as MutableList<CategoryCardItem>
        val listener: OnItemListener = this
        //Non va quindi skippa male, si farà una lista di merda senza click

        recyclerView = activity.findViewById(R.id.recycler_view_game_det)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager
        val adapter = CategoryCardAdapter(catItems)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

}