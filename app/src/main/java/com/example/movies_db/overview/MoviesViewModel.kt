package com.example.movies_db.overview

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.example.movies_db.CharacterPagingSource
import com.example.movies_db.network.MoviesApi
import com.example.movies_db.network.MoviesPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import kotlin.math.max

enum class MoviesApiStatus { LOADING, ERROR, DONE }

enum class MoviesApiStars { NINE, SEVEN, FIVE }

enum class MoviesApiGenreId(var genreId: Int) {
    ACTION(28),
    ANIMATION(16),
    COMEDY(35),
    DRAMA(18),
    FAMILY(10751)
}

class MoviesViewModel : ViewModel() {

    private var _currentPosition = MutableLiveData<Int?>()
    val currentPosition
        get() = _currentPosition

    private val _results = MutableLiveData<List<MoviesPhoto?>>()
    val results: LiveData<List<MoviesPhoto?>> = _results

    var resultPage = MutableLiveData<PagingData<MoviesPhoto>>()

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _postre = MutableLiveData<String>()
    val poster: LiveData<String> = _postre

    var overview = MutableLiveData<String?>()

    var origina_language = MutableLiveData<String?>()

    var backdrop_path = MutableLiveData<String?>()

    var release_date = MutableLiveData<String?>()

    var popularity = MutableLiveData<String?>()

    var vote_average = MutableLiveData<Double?>()

    var vote_count = MutableLiveData<String?>()

    var _star = MutableLiveData<MoviesApiStars>()
    val star: LiveData<MoviesApiStars> = _star

    private val _status = MutableLiveData<MoviesApiStatus>()
    val status: LiveData<MoviesApiStatus> = _status

    var genreId = MutableLiveData<List<Int?>>()

    private val _genreType = MutableLiveData<MoviesApiGenreId>()
    val genreType: LiveData<MoviesApiGenreId> = _genreType

    var stringG = MutableLiveData<String?>("")

    var genreList = mutableListOf<String>()

    init {
       // getMoviesPhotos()
        getListData("1")


    }



    fun getListData(query: String){
     viewModelScope.launch {
         Pager(config = PagingConfig(
             pageSize = 20, maxSize = 500, enablePlaceholders = false
         ),
             pagingSourceFactory = {
                 CharacterPagingSource(
                     MoviesApi.retrofitService, query
                 )
             }).flow.cachedIn(viewModelScope).collectLatest {
             resultPage.value=it

         }
     }
     }






    fun displayMovieDescription(displayPosition: Int) {

        val item = results.value?.get(displayPosition)

        _postre.value = item?.posterPath
        overview.value = item?.overview
        _title.value = item?.title
        origina_language.value = item?.originalLanguage
        backdrop_path.value = item?.backdropPath
        release_date.value = item?.releaseDate

        popularity.value = item?.popularity.toString()
        vote_average.value = item?.voteAverage
        vote_count.value = item?.voteCount.toString()


        if (vote_average.value!! > 8.0) {
            _star.value = MoviesApiStars.NINE
        } else if (vote_average.value!! > 6.0) {
            _star.value = MoviesApiStars.SEVEN
        } else if (vote_average.value!! > 4.0) {
            _star.value = MoviesApiStars.FIVE
        }
    }

   fun getMoviesWithFilter(genreState : MoviesApiGenreId){
       _status.value = MoviesApiStatus.LOADING
       viewModelScope.launch {
           try {
               _results.value = MoviesApi.retrofitService.getMoviesWithFilter(genreState.genreId , 12).results

               _title.value = _results.value!![0]?.title
               _postre.value = _results.value!![0]?.posterPath


               _status.value = MoviesApiStatus.DONE
           } catch (e: Exception) {
               _status.value = MoviesApiStatus.ERROR
               _results.value = listOf()
           }

       }

   }

    fun getGenreId(displayPosition: Int) {

        genreId.value = results.value!![displayPosition]?.genreIds
        Log.e("TAG", "item res ${genreId.value?.size}")

        genreList.clear()
        for (item in genreId.value!!) {
            if (item == 28) {
                _genreType.value = MoviesApiGenreId.ACTION
                genreList.add("Action ")

            } else if (item == 12) {
                _genreType.value = MoviesApiGenreId.ANIMATION
                genreList.add("Adventure ")

            } else if (item == 16) {
                genreList.add("Animation ")

            } else if (item == 35) {
                _genreType.value = MoviesApiGenreId.COMEDY
                genreList.add("Comedy ")

            } else if (item == 80) {
                genreList.add("Crime ")

            } else if (item == 99) {
                genreList.add("Documentary ")

            } else if (item == 18) {
                _genreType.value = MoviesApiGenreId.DRAMA
                genreList.add("Drama ")

            } else if (item == 10751) {
                _genreType.value = MoviesApiGenreId.FAMILY
                genreList.add("Family ")

            }

        }

    }

    fun stringGenre() {
        for (item in genreList) {
            Log.e("TAG", "item $item")
            stringG.value += item + " "
            //  stringG.value!!.removeSuffix(",")

        }
    }

    fun sortByAlphabeticalOrder() {
        viewModelScope.launch {
            _results.value = MoviesApi.retrofitService.getPhotos(12)
                .results.sortedBy { it.originalTitle }
        }
    }

    fun sortByReleaaseDate() {

        viewModelScope.launch {
            _results.value = MoviesApi.retrofitService.getPhotos(12)
                .results.sortedByDescending { it.releaseDate }
        }
    }

    fun sortByVoteAverage() {
        viewModelScope.launch {
            _results.value = MoviesApi.retrofitService.getPhotos(12)
                .results.sortedByDescending {  it.voteAverage  }
//            sortedBy { it.voteAverage }
        }
    }

}


