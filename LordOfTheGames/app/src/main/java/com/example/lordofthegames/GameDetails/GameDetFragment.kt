package com.example.lordofthegames.GameDetails

import android.R.attr
import android.app.Activity
import android.content.Intent
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
import com.example.lordofthegames.recyclerView.CategoryCardAdapter
import com.example.lordofthegames.recyclerView.CategoryCardItem
import com.example.lordofthegames.recyclerView.OnItemListener
import com.example.lordofthegames.recyclerView.PlatformCardItem


class GameDetFragment: Fragment(), OnItemListener  {
    // Imposta MY_REQUEST_CODE a un valore univoco
    private val MY_REQUEST_CODE = 12345

    // Imposta RESULT_OK a un valore diverso da RESULT_CANCELED
    private val RESULT_OK = 1
    private lateinit var imagePath: String
    private var bundle: Bundle? = null
    private lateinit var gameDetViewModel: GameDetViewModel
    private lateinit var achievementList: List<Achievement?>
    private lateinit var categoryList: List<Categories?>
    private lateinit var platformList: List<Platform?>
    private var adapter: CategoryCardAdapter? = null
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var recyclerViewPlatform: RecyclerView
    private lateinit var recyclerViewAchievement: RecyclerView
    private val catItems: MutableList<CategoryCardItem> = listOf(CategoryCardItem("GDR"), CategoryCardItem("Terza persona"), CategoryCardItem("JRPG"), CategoryCardItem("JRPG"), CategoryCardItem("JRPG"), CategoryCardItem("JRPG"), CategoryCardItem("JRPG")) as MutableList<CategoryCardItem>
    private val platItems: MutableList<PlatformCardItem> = listOf(PlatformCardItem("PS4"), PlatformCardItem("STEAM"), PlatformCardItem("EPIC"), PlatformCardItem("XBOX ONE"), PlatformCardItem("Game Pass")) as MutableList<PlatformCardItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_game_details, container, false)
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
        recyclerViewCategory    = view.findViewById(R.id.recycler_view_game_details_category)
        recyclerViewPlatform    = view.findViewById(R.id.recycler_view_game_details_platform)
        recyclerViewAchievement = view.findViewById(R.id.recycler_view_game_details_platform)
        adapter = CategoryCardAdapter(this, catItems)
        Log.e("onCreateView", savedInstanceState.toString())
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val linearLayoutManagerCategory = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val linearLayoutManagerPlatform = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            recyclerViewCategory.layoutManager = linearLayoutManagerCategory
            recyclerViewPlatform.layoutManager = linearLayoutManagerPlatform

            recyclerViewCategory.setHasFixedSize(true)
            recyclerViewPlatform.setHasFixedSize(true)

            recyclerViewCategory.adapter = adapter
            recyclerViewPlatform.adapter = adapter


            //Utilities.setUpToolBar( activity as AppCompatActivity, arguments?.getString("game_title").toString() )

            //Utilities.setUpDrawer(activity.findViewById(R.id.game_det_drawer), activity, activity.supportActionBar)

            val selectedImage: ImageView = view.findViewById(R.id.selectedImage)

            imagePath = arguments?.getString("game_cover").toString()

            var drawable: Drawable? = null

            if (imagePath.contains("ic_")){
                drawable = ContextCompat.getDrawable(requireActivity(), requireActivity().resources.getIdentifier(imagePath, "drawable", requireContext().packageName))
            } else if(imagePath.contains("gabibbo")) {
                drawable = ContextCompat.getDrawable(requireActivity(), requireActivity().resources.getIdentifier("ic_gabibbo_test", "mipmap", requireContext().packageName))
            } else if(imagePath.contains("yee")){
                drawable = ContextCompat.getDrawable(requireActivity(), requireActivity().resources.getIdentifier("ic_yeee_foreground", "mipmap", requireContext().packageName))
            }

            selectedImage.setImageDrawable(drawable)

        }
    }






    override fun onItemClick(position: Int) {
        Log.w("Belandi", "Belandi")
    }

}