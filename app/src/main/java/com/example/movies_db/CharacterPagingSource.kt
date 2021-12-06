package com.example.movies_db

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies_db.network.MoviesApiService
import com.example.movies_db.network.MoviesPhoto

class CharacterPagingSource (val apiService : MoviesApiService ,
                             val query : String) : PagingSource<Int, MoviesPhoto>() {


    override fun getRefreshKey(state: PagingState<Int, MoviesPhoto>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesPhoto> {
        val nextPage : Int = params.key ?: FIRST_PAGE_INDEX
//        val apiQuery = query + BASE_URL

        return try {
            val response = apiService.getPhotos(nextPage)
            val repos = response.page
            val nextKey =  if (repos.isEmpty()) {
                null
            }else{
                nextPage + (params.loadSize /20)
            }
//                val uri = Uri.parse(response?.page)
//                val nextPageQuery = uri.getQueryParameter("page")
//                nextPageNum = nextPageQuery?.toInt()

            LoadResult.Page(
                data = response.results ,
                prevKey = if (nextPage == FIRST_PAGE_INDEX ) null else nextPage - 1,
                nextKey = nextKey
            )

        }catch (e : Exception){
            LoadResult.Error(e)
        }


    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}