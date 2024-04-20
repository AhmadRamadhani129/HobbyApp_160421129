package com.example.hobbyapp_160421129.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.example.hobbyapp_160421129.R
import com.example.hobbyapp_160421129.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = (supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment).navController
        NavigationUI.setupActionBarWithNavController(this, navController)
        binding.bottomNav.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.registerFragment -> {
                    binding.bottomNav.isInvisible = true
                }
                else->{
                    binding.bottomNav.isInvisible = false
                }
            }
        }
    }

}