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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            Utilities.insertFragment(this, HomeFragment(), HomeFragment::class.java.simpleName)
    }



}
