package com.amacdong.usertaskcontrol

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amacdong.usertaskcontrol.ui.componentes.interfaces.OnCustomDialogLoading
import com.amacdong.usertaskcontrol.ui.componentes.interfaces.OnCustomKOScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnCustomDialogLoading, OnCustomKOScreen {

    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbarApplication)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        val topLevelDestinations = hashSetOf<Int>()
        topLevelDestinations.add(R.id.userProfileFragment)
        topLevelDestinations.add(R.id.farmFragment)
        topLevelDestinations.add(R.id.newTaskFragment)
        topLevelDestinations.add(R.id.listTasksFragment)
        topLevelDestinations.add(R.id.pendingTasksFragment)
        topLevelDestinations.add(R.id.completedTasksFragment)

        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations)
            .build()

        setupActionBar(navController, appBarConfiguration)

        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            onRestoreDefaultScreen()
            when (destination.id) {
                R.id.userProfileFragment -> {
                    toolbar?.visibility = View.GONE
                    bottomNavMenu.visibility = View.VISIBLE
                }
                R.id.loginFragment ->{
                    toolbar?.visibility = View.GONE
                    bottomNavMenu.visibility = View.GONE
                }
                R.id.farmFragment ->{
                    toolbar?.visibility = View.VISIBLE
                    bottomNavMenu.visibility = View.VISIBLE
                }
                R.id.listTasksFragment -> {
                    toolbar?.visibility = View.VISIBLE
                    bottomNavMenu.visibility = View.VISIBLE
                }
                R.id.newTaskFragment -> {
                    toolbar?.visibility = View.VISIBLE
                    bottomNavMenu.visibility = View.GONE
                }
                else -> {
                    toolbar?.visibility = View.VISIBLE
                }
            }
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }

            Log.d("NavigationActivity", "Navigated to $dest")
        }

    }

    override val isShowingCustomDialogLoading: Boolean
        get() = false

    override fun hideComponent() {
    }

    override fun showSpinner() {
        customKOScreen.visibility = View.VISIBLE
        customKOScreen.showSpinner()
    }

    override fun dismissSpinner() {
        customKOScreen.dismissSpinner()
        customKOScreen.visibility = View.GONE
    }

    override fun onShowKOScreen(
        imgName: String,
        imgHeight: Int,
        imgWidth: Int,
        errorMessage: String,
        sizeTextError: Float
    ) {
        customKOScreen.onShowKOScreen(imgName, imgHeight, imgWidth, errorMessage, sizeTextError)
        customKOScreen.visibility = View.VISIBLE
    }

    override fun onRestoreDefaultScreen() {
        customKOScreen.onRestoreDefaultScreen()
        customKOScreen.visibility = View.GONE
    }

    override fun onShowKOScreen() {
        customKOScreen.onShowKOScreen()
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavMenu)
        bottomNav?.setupWithNavController(navController)
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }
}
