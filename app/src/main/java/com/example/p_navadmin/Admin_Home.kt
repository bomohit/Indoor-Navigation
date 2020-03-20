package com.example.p_navadmin

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.admin_home.*
import kotlinx.android.synthetic.main.app_bar_main.*

class Admin_Home: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var storeDetailsFragment: StoreDetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_home)
        setSupportActionBar(toolbar)

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle (
            this,
            drawerLayout,
            toolbar,
            (R.string.open),
            (R.string.close)
        ) {

        }

        drawerToggle.isDrawerIndicatorEnabled =true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // now implement navigation item selected listener
        // the default fragment is StoreDetailsFragement
        storeDetailsFragment = StoreDetailsFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, storeDetailsFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
        // so this is our fragment code

    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        // now create our related fragment
        when (menuItem.itemId) {
            R.id.home ->  {
                storeDetailsFragment = StoreDetailsFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, storeDetailsFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }

}