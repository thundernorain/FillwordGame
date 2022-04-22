package com.thundernorain.fillwordgame.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.thundernorain.fillwordgame.R
import com.thundernorain.fillwordgame.presentation.fragments.MenuFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MenuFragment>(R.id.fragmentContainerView)
            }
        }
    }
}