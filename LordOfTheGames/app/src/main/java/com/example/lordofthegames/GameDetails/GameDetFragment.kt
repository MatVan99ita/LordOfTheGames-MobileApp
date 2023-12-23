package com.example.lordofthegames.GameDetails

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.lordofthegames.Database.LOTGDatabase
import com.example.lordofthegames.R
import com.example.lordofthegames.ViewModel.GameDetViewModel
import com.example.lordofthegames.ViewModel.GameDetViewModel2
import com.example.lordofthegames.db_entities.Achievement
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Platform
import com.example.lordofthegames.recyclerView.AchievementCardAdapter
import com.example.lordofthegames.recyclerView.AchievementCardItem
import com.example.lordofthegames.recyclerView.CategoryCardAdapter
import com.example.lordofthegames.recyclerView.CategoryCardItem
import com.example.lordofthegames.recyclerView.OnItemListener
import com.example.lordofthegames.recyclerView.PlatformCardAdapter
import com.example.lordofthegames.recyclerView.PlatformCardItem


class GameDetFragment: Fragment(), OnItemListener  {
    // Imposta MY_REQUEST_CODE a un valore univoco
    private val MY_REQUEST_CODE = 12345

    // Imposta RESULT_OK a un valore diverso da RESULT_CANCELED
    private val RESULT_OK = 1
    private lateinit var imagePath: String
    private var bundle: Bundle? = null
    private lateinit var gameDetViewModel: GameDetViewModel2
    private lateinit var achievementList: List<Achievement?>
    private lateinit var categoryList: List<Categories?>
    private lateinit var platformList: List<Platform?>

    private var categoryCardAdapter: CategoryCardAdapter? = null
    private var platformCardAdapter: PlatformCardAdapter? = null
    private var achievementCardAdapter: AchievementCardAdapter? = null

    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var recyclerViewPlatform: RecyclerView
    private lateinit var recyclerViewAchievement: RecyclerView

    private lateinit var frameLayout: FrameLayout
    private lateinit var btnFLAnnulla: Button
    private lateinit var btnFLSalva: Button
    private var selectedItem: AchievementCardItem? = null


    private lateinit var achievementImgEdit: ImageView
    private lateinit var achievementTitleEdit: TextView
    private lateinit var achievementDescription: TextView
    private lateinit var maxNum: TextView
    private lateinit var editText: EditText

    private val catItems: MutableList<CategoryCardItem> = listOf(
        CategoryCardItem("GDR"),
        CategoryCardItem("Terza persona"),
        CategoryCardItem("JRPG"),
        CategoryCardItem("JRPG"),
        CategoryCardItem("JRPG"),
        CategoryCardItem("JRPG"),
        CategoryCardItem("JRPG")
    ) as MutableList<CategoryCardItem>

    val platItems: MutableList<PlatformCardItem> = listOf(
        PlatformCardItem("PS4", Color.rgb(19, 44, 116)),
        PlatformCardItem("STEAM", Color.rgb(41, 41, 41)),
        PlatformCardItem("EPIC", Color.rgb(58, 58, 56)),
        PlatformCardItem("XBOX ONE", Color.rgb(24, 128, 24)),
        PlatformCardItem("Game Pass", Color.rgb(24, 128, 24)),
        PlatformCardItem("Nintendo", Color.rgb(231, 8, 25))
    ) as MutableList<PlatformCardItem>

    val achieveItems: MutableList<AchievementCardItem> = listOf(
        AchievementCardItem("", "sesso", "JULIANA!! VIENI A VEDERE UN PO' QUESTO PISELLO", 0, 1, true),
        AchievementCardItem("", "sesso", "JULIANA!! VIENI A VEDERE UN PO' QUESTO PISELLO", 3, 10),
        AchievementCardItem("", "sesso", "JULIANA!! VIENI A VEDERE UN PO' QUESTO PISELLO", 0, 4),
        AchievementCardItem("", "sesso", "JULIANA!! VIENI A VEDERE UN PO' QUESTO PISELLO", 1, 1, true),
        AchievementCardItem("", "sesso", "JULIANA!! VIENI A VEDERE UN PO' QUESTO PISELLO", 1, 10),
        AchievementCardItem("", "sesso", "JULIANA!! VIENI A VEDERE UN PO' QUESTO PISELLO", 2, 8),
    ) as MutableList<AchievementCardItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_game_details, container, false)
        gameDetViewModel = ViewModelProvider(requireActivity())[GameDetViewModel2::class.java]
        //return super.onCreateView(inflater, container, savedInstanceState);
        val game_title = requireActivity().intent.getStringExtra("game_title").toString()

        this.gameDetViewModel.getGameDetails(game_title).observe(viewLifecycleOwner){ g -> Log.w("GIUOCO", g.toString()) }



        //achievementList = gameDetViewModel.getGameAchievement(game_title).value!!
        //categoryList = gameDetViewModel.getGameCategory(game_title).value!!
        //platformList = gameDetViewModel.getGamePlatform(game_title).value!!



        bundle = savedInstanceState


        recyclerViewCategory    = view.findViewById(R.id.recycler_view_game_details_category)
        recyclerViewPlatform    = view.findViewById(R.id.recycler_view_game_details_platform)
        recyclerViewAchievement = view.findViewById(R.id.recycler_view_game_details_achievement)

        frameLayout = view.findViewById(R.id.achievement_edit)
        btnFLAnnulla = view.findViewById(R.id.btn_annulla1)
        btnFLSalva = view.findViewById(R.id.btn_salva1)

        frameLayout.visibility = View.GONE

        achievementDescription = view.findViewById(R.id.achievement_description_edit)
        achievementTitleEdit = view.findViewById(R.id.achievement_title_edit)
        achievementImgEdit = view.findViewById(R.id.achievement_image_edit)
        editText = view.findViewById(R.id.numberPicker)
        maxNum = view.findViewById(R.id.achievement_max_edit)


        categoryCardAdapter = CategoryCardAdapter(this, catItems)
        platformCardAdapter = PlatformCardAdapter(this, platItems)
        achievementCardAdapter = AchievementCardAdapter(this, achieveItems, requireActivity())

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
            val linearLayoutManagerAchievement = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            recyclerViewCategory.layoutManager = linearLayoutManagerCategory
            recyclerViewPlatform.layoutManager = linearLayoutManagerPlatform
            recyclerViewAchievement.layoutManager = linearLayoutManagerAchievement

            recyclerViewCategory.setHasFixedSize(true)
            recyclerViewPlatform.setHasFixedSize(true)
            recyclerViewAchievement.setHasFixedSize(true)

            recyclerViewCategory.adapter = categoryCardAdapter
            recyclerViewPlatform.adapter = platformCardAdapter
            recyclerViewAchievement.adapter = achievementCardAdapter


            /** TODO:
             *      edit del gioco(<-to be spiegare)
             *      fare in modo che compaia il framelayout con il tipo di edit desiderato quando premuti 2 bottoni diversi
             *      bloccaggio della scrollview quando aperta
             *      bloccare tutta la view sotto quando aperto l'achievement/game edit
             *      inserire vari input event funzionanti
             *      disattivare l'onclick delle recycler
             * */

            //numberPicker.minValue=0
            //numberPicker.maxValue=10

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

        btnFLAnnulla.setOnClickListener { frameLayout.visibility = View.GONE }
        btnFLSalva.setOnClickListener {
            if(selectedItem != null){
                selectedItem!!.actual_count = "${editText.text}".toInt()
            }
        }


    }


    fun checkAchievementCompletation(achievement: AchievementCardItem, status: Int){
        if(status >= achievement.total_count){
            achievement.actual_count = achievement.total_count
            achievement.completed = true
            //disable achievement edit
        }
    }



    override fun onItemClick(view: View, position: Int) {

        Log.w("Belandi", "Belandi ${view.resources.getResourceEntryName(view.id)}")
        when(view.id){

            //Achievement
            R.id.single_card2 -> {
                frameLayout.visibility = View.VISIBLE
                val item = achieveItems[position]
                selectedItem = item
                achievementTitleEdit.text = item.name
                achievementDescription.text = item.descr
                maxNum.text = "/${item.total_count}"
                editText.text = Editable.Factory.getInstance().newEditable("${item.actual_count}")
            //
            }
            //Category
            R.id.category_item -> Log.w("Belandi", "b")

            //Platform
            R.id.platform_item -> Log.w("Belandi", "c")

        }
    }

}


