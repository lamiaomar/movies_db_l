package com.example.movies_db


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies_db.overview.MoviesApiGenreId
import com.example.movies_db.overview.MoviesViewModel
import com.example.movies_db.overview.PhotoGridAdapter
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.flow.collectLatest


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val sharedViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.sortByLetter) {
            sharedViewModel.sortByAlphabeticalOrder()
        }

        if (id == R.id.sortByRate) {
            sharedViewModel.sortByVoteAverage()
        }

        if (id == R.id.sortByRelaseYear) {
            sharedViewModel.sortByReleaaseDate()
        }

        if(id == R.id.fillterToAction){
            sharedViewModel.getMoviesWithFilter(MoviesApiGenreId.ACTION)
        }

        if(id == R.id.fillterToAnimation){
            sharedViewModel.getMoviesWithFilter(MoviesApiGenreId.ANIMATION)
        }

        if(id == R.id.fillterToComedy){
            sharedViewModel.getMoviesWithFilter(MoviesApiGenreId.COMEDY)
        }

        if(id == R.id.fillterToDrama){
            sharedViewModel.getMoviesWithFilter(MoviesApiGenreId.DRAMA)
        }

        if(id == R.id.fillterToFamily){
            sharedViewModel.getMoviesWithFilter(MoviesApiGenreId.FAMILY)
        }
        return super.onOptionsItemSelected(item)
    }
}



