package com.example.lordofthegames

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/* Struttura del db
 *
 * | Achievements |
 * |--------------|
 * | id           |
 * | nome         |
 * | descr        |
 * | count        |
 * | game         |
 *      N                 | Tag       |
 *      ^                 |-----------|
 *      1                 | id        |         | Categorie |
 * | Game   | 1--------N> | gioco     |         |-----------|
 * |--------|             | categoria | <N----1 | id        |
 * | id     | <N-----------------------------N> | nome      |
 * | nome   |                                   | tag       |
 * | img    |        | Note      |
 * | status |        |-----------|
 * | notes  | <1---1 | id        |
 *                   | title     |
 *                   | contenuto |
 *
 * Riassunto relazioni
 *
 *                           /  Game.id      -> Tag.game
 * Game < - > Categorie ----|
 *                           \  Categorie.id -> Tag.categorie
 *
 * Note.id -> Game.notes
 * Game.id -> Achievements.game
 *
 *
 */




class MainActivity : AppCompatActivity() {
<<<<<<< HEAD

=======
    var achievements: List<Achievement> = listOf(Achievement("Uccidi", "Uccidi il cattivo", "", 1, false), Achievement("Finisci", "Finisci il gioco", "", 1, false))
    var tag: List<Categories> = listOf(Categories("GDR"), Categories("FPS"))
    var games: List<Game> = listOf(
        Game("Spado spado uccidi uccidi", achievements, "ic__search_white_24", listOf(tag[0]), "", Notes("", "") ),
        Game("Sparo sparo uccidi uccidi", achievements, "ic_menu_24dp",        listOf(tag[1]), "", Notes("", "") ),
        Game("Matel Gear Rising: Revengence", achievements, "ic_t_pose", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Dark Souls 3", achievements, "ic_t_pose", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("MARVEL Spider-Man", achievements, "ic_t_pose", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Bloodborne", achievements, "ic_t_pose", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("God of War: Ragnarok", achievements, "ic_t_pose", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Horizon Zero Dawn: Forbidden West", achievements, "ic_t_pose", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN", achievements, "gabibbo", listOf(tag[1], tag[0]), "", Notes("", "") )
    )

    var simpleGrid: GridView? = null
    var logos = intArrayOf(
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground
    )
    var recyclerView: RecyclerView? = null
>>>>>>> parent of 3e1b4ed (sistemato il grid layout e il second)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            Utilities.insertFragment(this, HomeFragment(), HomeFragment::class.java.simpleName)
    }



}
