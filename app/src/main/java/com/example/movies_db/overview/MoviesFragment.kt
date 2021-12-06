package com.example.movies_db.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies_db.R
import com.example.movies_db.databinding.FragmentMoviesBinding
import com.example.movies_db.network.MoviesPhoto
import com.example.movies_db.network.i
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MoviesFragment : Fragment() {

    lateinit var recyclerViewAdapter: PhotoGridAdapter

    lateinit var viewModel: MoviesViewModel
    lateinit var binding: FragmentMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)




    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentMoviesBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

//
//        viewModel.getListData().collectLatest {
//            rec
//        }

        binding.photosGrid.adapter = PhotoGridAdapter()

        return binding.root
    }

//    private fun initRecyclerView(){
//        photos_grid.apply {
//            Log.e("TAG" ,"1 $layoutManager")
//            layoutManager = GridLayoutManager(context,1)
//            Log.e("TAG" ,"2 $layoutManager")
//            val deccoration = DividerItemDecoration( context, DividerItemDecoration.VERTICAL)
//            addItemDecoration(deccoration)
//            recyclerViewAdapter = PhotoGridAdapter()
//
//            adapter = recyclerViewAdapter
//
//        }
//    }
//
//    fun  initViewModel() {
//
//        val viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
//        lifecycleScope.launchWhenCreated {
//            viewModel.getListData().collectLatest {
//                recyclerViewAdapter.submitData(it)
//            }
//        }
//        viewModel.getListData()
//
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.getListData("1").observe(viewLifecycleOwner){
//
//        }
        onDestroy()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stringG.value = ""
    }
}