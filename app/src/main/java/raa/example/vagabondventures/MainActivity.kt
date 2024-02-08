package raa.example.vagabondventures

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import raa.example.articles.ArticlesScreen
import raa.example.calendar.CalendarScreen
import raa.example.chat.ChatScreen
import raa.example.setting.SettingScreen
import raa.example.timerscreen.ui.AddPersonFragment
import raa.example.timerscreen.ui.TimerScreen
import raa.example.vagabondventures.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TimerScreen.OpenAddPersonFragment {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            replaceFragment(TimerScreen.newInstance())
        }

        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hideSystemUI()
        bindBottomNavBar()
    }

    private fun hideSystemUI(){
        val decorView = this.window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            decorView.windowInsetsController?.hide(WindowInsets.Type.systemBars())
        }

    }

    private fun bindBottomNavBar() {
        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.dmb_timer -> {
                    replaceFragment(TimerScreen.newInstance())
                }

                R.id.calendar -> {
                    replaceFragment(CalendarScreen.newInstance())
                }

                R.id.posts -> {
                    replaceFragment(ArticlesScreen.newInstance())
                }

                R.id.chat -> {
                    replaceFragment(ChatScreen.newInstance())
                }

                R.id.setting -> {
                    replaceFragment(SettingScreen.newInstance())
                }
            }

            return@setOnItemSelectedListener true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }

    override fun openFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment_container, AddPersonFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}