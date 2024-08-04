package com.example.lordofthegames.GameDetails

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.ViewModel.GameDetViewModel2
import com.example.lordofthegames.db_entities.Achievement
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Platform
import com.example.lordofthegames.recyclerView.AchievementCardAdapter
import com.example.lordofthegames.recyclerView.AchievementCardItem
import com.example.lordofthegames.recyclerView.CardAdapter
import com.example.lordofthegames.recyclerView.CategoryCardAdapter
import com.example.lordofthegames.recyclerView.OnItemListener
import com.example.lordofthegames.recyclerView.PlatformCardAdapter
import com.squareup.picasso.Picasso


class GameDetFragment: Fragment(), OnItemListener  {
    // Imposta MY_REQUEST_CODE a un valore univoco
    private val MY_REQUEST_CODE = 12345

    // Imposta RESULT_OK a un valore diverso da RESULT_CANCELED
    private val RESULT_OK = 1
    private lateinit var imagePath: String

    //####################################################################
    private lateinit var short_descr: TextView
    private lateinit var long_descr: TextView
    private lateinit var game_img: ImageView

    private lateinit var frame_button_plus_one: Button


    //####################################################################

    private var bundle: Bundle = Bundle()
    private lateinit var gameDetViewModel: GameDetViewModel2
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
    private lateinit var positionT: TextView
    private var selectedItem: AchievementCardItem? = null

    private lateinit var sgrull: ScrollView
    private lateinit var achievementImgEdit: ImageView
    private lateinit var gd_game_cover: ImageView
    private lateinit var achievementTitleEdit: TextView
    private lateinit var achievementDescription: TextView
    private lateinit var maxNum: TextView
    private lateinit var editText: EditText
    private lateinit var spinner_GS: Spinner
    private var gameStatus: String = "Not played"
    private lateinit var banana: SharedPreferences

    private val statuss = listOf(
        "Not played",
        "Playing",
        "Played",
        "Abandoned",
        "Wanted to play",
    )

    private var achieve: List<AchievementCardItem?> = mutableListOf()
    private lateinit var user_ref: String
    private lateinit var game_title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user_ref =
            requireActivity()
                .getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                .getString("email", "sesso")
            .toString()
        setHasOptionsMenu(true)
        banana = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_game_details, container, false)
        gameDetViewModel = ViewModelProvider(requireActivity())[GameDetViewModel2::class.java]
        //return super.onCreateView(inflater, container, savedInstanceState);
        this.game_title = requireActivity().intent.getStringExtra("game_title").toString()

        achieve = gameDetViewModel.getGameAchievement(game_title, user_ref)

        val plat = gameDetViewModel.getGamePlatform(game_title)
        val cate = gameDetViewModel.getGameCategory(game_title)




        sgrull = view.findViewById(R.id.sgrullatina)

        recyclerViewCategory    = view.findViewById(R.id.recycler_view_game_details_category)
        recyclerViewPlatform    = view.findViewById(R.id.recycler_view_game_details_platform)
        recyclerViewAchievement = view.findViewById(R.id.recycler_view_game_details_achievement)

        frameLayout = view.findViewById(R.id.achievement_edit)
        btnFLAnnulla = view.findViewById(R.id.btn_annulla1)
        btnFLSalva = view.findViewById(R.id.btn_salva_frml)
        short_descr = view.findViewById(R.id.description_short)
        long_descr = view.findViewById(R.id.game_description)
        game_img = view.findViewById(R.id.selectedImage)
        gd_game_cover = view.findViewById(R.id.game_detail_cover)

        frame_button_plus_one = view.findViewById(R.id.btn_plus)

        frameLayout.visibility = View.GONE

        spinner_GS = view.findViewById(R.id.spinner)

        val spinnerAdapter = ArrayAdapter<String>(requireContext(), R.layout.row)
        spinnerAdapter.addAll(statuss)
        spinner_GS.adapter = spinnerAdapter


        achievementDescription = view.findViewById(R.id.FL_achievement_description_edit)
        achievementTitleEdit = view.findViewById(R.id.FL_achievement_title_edit)
        achievementImgEdit = view.findViewById(R.id.achievement_image_edit)
        editText = view.findViewById(R.id.numberPicker)
        maxNum = view.findViewById(R.id.achievement_max_edit)
        positionT = view.findViewById(R.id.FL_position)

        platformCardAdapter = PlatformCardAdapter(this, plat)

        categoryCardAdapter = CategoryCardAdapter(this, cate)
        achievementCardAdapter = AchievementCardAdapter(this,
            achieve as List<AchievementCardItem>, requireActivity())

        return view
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val game = gameDetViewModel.getGameDetails(game_title)

            gameStatus = banana.getString("mail", "null")
                ?.let { gameDetViewModel.getGameStatus(game.game_id, it) }.toString()

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

            short_descr.text = game.game_description
            long_descr.text = game.game_description


            spinner_GS.setSelection(
                if (gameStatus != "Not played") statuss.indexOf(gameStatus) else 0
            )

            spinner_GS.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    pos: Int,
                    id: Long
                ) {
                    val item = parent.getItemAtPosition(pos).toString()

                    if(item != gameStatus){
                        gameStatus = item
                        if(!gameDetViewModel.ugExist(user_ref, game.game_id)){
                            val j = gameDetViewModel.ugCreate(
                                user_ref = user_ref,
                                game_id = game.game_id,
                                game_status = gameStatus
                            )
                            if (j > 0L) {
                                //toast positivo
                                Utilities.showaToast(requireContext(), "Gioco aggiunto alla lista $gameStatus")
                            } else {
                                //toast negativo
                                Utilities.showaToast(requireContext(), "Errore nell'aggiunta")
                            }
                        } else {
                            if(gameStatus != "Not played") {
                                val i = gameDetViewModel.updateGameStatus(
                                    gameStatus,
                                    game.game_id,
                                    arguments?.getString("email").toString()
                                )

                                if (i > 0) {
                                    //toast positivo
                                    Utilities.showaToast(requireContext(), "Gioco aggiornato")
                                } else {
                                    //toast negativo
                                    Utilities.showaToast(requireContext(), "Errore nell'aggiornamento")
                                }
                            }
                        }

                    }

                    val picasso = Picasso.Builder(activity as Context)
                        .loggingEnabled(true) // Abilita il logging per il debug
                        .build()
                    picasso
                        .load(game.game_cover)
                        .into(gd_game_cover)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

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
            val num: Int = if ("${editText.text}".toInt() > selectedItem!!.total_count) selectedItem!!.total_count else "${editText.text}".toInt()
            val prev = selectedItem!!.actual_count
            if(selectedItem != null){
                selectedItem!!.actual_count = num
            }
            achievementCardAdapter!!.updateActualCount(Integer.valueOf(positionT.text.toString()), num)

            recyclerViewAchievement.adapter = achievementCardAdapter

            var i = -1
            var j: Long = -1L
            if(!gameDetViewModel.uaExist(user_ref, selectedItem!!.achieve_id) ) {

                 Log.e("CAZZOHOSCRITTO", "achieve_id = ${selectedItem!!.achieve_id}, \n"+
                                                   "user_ref = $user_ref, \n"+
                                                   "actual = ${selectedItem!!.actual_count}"
                 )
                j = gameDetViewModel.createUserAchievementRecord (
                    achieve_id = selectedItem!!.achieve_id,
                    user_ref = user_ref,
                    actual = selectedItem!!.actual_count,
                    status = if(selectedItem!!.actual_count == selectedItem!!.total_count) 1 else 0
                )
            } else {
                if(selectedItem!!.actual_count >= selectedItem!!.total_count) {
                    i = gameDetViewModel.completeAchievement (
                        achieve_id = selectedItem!!.achieve_id,
                        user_ref = user_ref
                    )
                } else {
                    i = gameDetViewModel.updateAchievement (
                        achieve_id = selectedItem!!.achieve_id,
                        actual = selectedItem!!.actual_count,
                        user_ref = user_ref
                    )
                }

            }




            if (i >= 0 || j >= 0L) {
                Utilities.showaToast(requireContext(), "Achievement aggiornato")
            } else {
                Utilities.showaToast(requireContext(), "Errore update")
            }
            frameLayout.visibility = View.GONE
        }


    }


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.game_det_top_bar, menu)


    }




    override fun onItemClick(view: View, position: Int) {

        when(view.id){

            //Achievement
            R.id.single_card2, R.id.achievement_item_button_edit -> {
                if(!frameLayout.isVisible){
                    frameLayout.visibility = View.VISIBLE

                    val item = achieve[position]
                    selectedItem = item

                    if(item != null)
                    {
                        positionT.text = "$position"
                        achievementTitleEdit.text = item.name
                        achievementDescription.text = item.descr
                        maxNum.text = "/${item.total_count}"
                        editText.text =
                            Editable.Factory.getInstance().newEditable("${item.actual_count}")
                        frame_button_plus_one.setOnClickListener {
                            if (item.actual_count < item.total_count) {
                                item.actual_count++
                                editText.text =
                                    Editable.Factory.getInstance()
                                        .newEditable("${item.actual_count}")
                            }
                        }
                    }
                }
            }
        }
    }


}


