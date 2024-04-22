package com.mubin.starwars.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mubin.starwars.R
import com.mubin.starwars.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private var onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

            when (navController.currentDestination?.id) {
                R.id.characterFragment -> {
                    finish()
                }

                R.id.starshipsFragment, R.id.planetsFragment -> {
                    binding.bottomNav.selectedItemId = R.id.characterFragment
                }
            }

        }

    }

    private var destinationChangeListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->

            val showBottomNavDestinations = setOf(
                R.id.characterFragment, R.id.starshipsFragment, R.id.planetsFragment
            )

            onBackPressedCallback.isEnabled = showBottomNavDestinations.contains(destination.id)

            if (showBottomNavDestinations.contains(destination.id)) {
                binding.bottomNav.visibility = View.VISIBLE
            } else {
                binding.bottomNav.visibility = View.GONE
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        initNavigation()

    }

    private fun initNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(destinationChangeListener)
    }

}