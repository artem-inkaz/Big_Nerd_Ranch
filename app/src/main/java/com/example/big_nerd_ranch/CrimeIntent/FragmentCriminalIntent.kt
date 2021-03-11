package com.example.big_nerd_ranch.CrimeIntent

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.big_nerd_ranch.R
import com.example.big_nerd_ranch.model.Crime
import java.util.*
private const val TAG = "MainActivity"

class FragmentCriminalIntent: AppCompatActivity(),CrimeListFragment.CallBacks {

//    private lateinit var crime: Crime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criminal_intent)
//        crime = Crime()

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null){
            // 8.12
//            val fragment = CrimeFragment()
            // 9.4
            val fragment = CrimeListFragment.neInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit()
        }
    }

    override fun onCrimeSelected(crimeId: UUID){
        Log.d(TAG, "MainActivity.onCrimeSelected: $crimeId")
        val fragment = CrimeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}