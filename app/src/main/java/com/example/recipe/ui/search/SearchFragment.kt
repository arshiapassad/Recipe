package com.example.recipe.ui.search

import academy.nouri.s4_recipeapp.viewmodel.SearchViewModel
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.R
import com.example.recipe.adapter.RecentAdapter
import com.example.recipe.databinding.FragmentSearchBinding
import com.example.recipe.databinding.FragmentSplashBinding
import com.example.recipe.ui.recipe.RecipeFragmentDirections
import com.example.recipe.utils.NetworkChecker
import com.example.recipe.utils.NetworkRequest
import com.example.recipe.utils.setupRecyclerView
import com.example.recipe.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class SearchFragment : Fragment() {
    //Binding
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var recentAdapter: RecentAdapter

    @Inject
    lateinit var networkChecker: NetworkChecker

    //Other
    private val viewModel: SearchViewModel by viewModels()
    private var isNetworkAvailable by Delegates.notNull<Boolean>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Keyboard listener
            requireActivity().window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
                val rect = Rect()
                requireActivity().window.decorView.getWindowVisibleDisplayFrame(rect)
                val height = requireActivity().window.decorView.height
                if (height - rect.bottom <= height * 0.1399)
                    rootMotion.transitionToStart()
                else
                    rootMotion.transitionToEnd()
            }
            //Check internet
            lifecycleScope.launchWhenStarted {
                networkChecker.checkNetworkAvailability().collect { state ->
                    initInternetLayout(state)
                    isNetworkAvailable = state
                }
            }
            //Search
            searchEdt.addTextChangedListener {
                if (it.toString().length>2 && isNetworkAvailable)
                    viewModel.callSearchApi(viewModel.searchQueries(it.toString()))
            }
        }
        //Show data
        loadRecentData()
    }

    private fun loadRecentData() {
        binding.apply {
            viewModel.searchData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                    }
                    is NetworkRequest.Success -> {

                        response.data?.let { data ->
                            if (data.results!!.isNotEmpty()) {
                                recentAdapter.setData(data.results)
                                initRecentRecycler()
                            }
                        }
                    }
                    is NetworkRequest.Error -> {
                        binding.root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun initRecentRecycler() {
        binding.searchList.setupRecyclerView(
            LinearLayoutManager(requireContext()),
            recentAdapter
        )
        //Click
        recentAdapter.setOnItemClickListener {
            gotoDetailPage(it)
        }
    }

    private fun gotoDetailPage(id: Int) {
        val action = RecipeFragmentDirections.actionToDetail(id)
        findNavController().navigate(action)
    }

    private fun initInternetLayout(isConnected: Boolean) {
        binding.internetLay.isVisible = isConnected.not()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}