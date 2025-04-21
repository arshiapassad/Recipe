package com.example.recipe.ui.favorite

import academy.nouri.s4_recipeapp.adapter.FavoriteAdapter
import academy.nouri.s4_recipeapp.viewmodel.FavoriteViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.R
import com.example.recipe.databinding.FragmentFavoriteBinding
import com.example.recipe.databinding.FragmentSplashBinding
import com.example.recipe.ui.recipe.RecipeFragmentDirections
import com.example.recipe.utils.isVisible
import com.example.recipe.utils.setupRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    //Binding
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    //Other
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Load favorites
            viewModel.readFavoriteData.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    emptyTxt.isVisible(false, favoriteList)
                    //Data
                    favoriteAdapter.setData(it)
                    favoriteList.setupRecyclerView(LinearLayoutManager(requireContext()), favoriteAdapter)
                    //Click
                    favoriteAdapter.setOnItemClickListener { id ->
                        val action = RecipeFragmentDirections.actionToDetail(id)
                        findNavController().navigate(action)
                    }
                } else {
                    emptyTxt.isVisible(true, favoriteList)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}