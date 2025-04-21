package com.example.recipe.ui.recipe


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.nouri.s4_recipeapp.utils.*
import academy.nouri.s4_recipeapp.viewmodel.RecipeViewModel
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.recipe.R
import com.example.recipe.adapter.PopularAdapter
import com.example.recipe.adapter.RecentAdapter
import com.example.recipe.databinding.FragmentRecipeBinding
import com.example.recipe.models.recipe.ResponseRecipes
import com.example.recipe.utils.Constants
import com.example.recipe.utils.NetworkRequest
import com.example.recipe.utils.isVisible
import com.example.recipe.utils.onceObserve
import com.example.recipe.utils.setupRecyclerView
import com.example.recipe.utils.showSnackBar
import com.example.recipe.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/*loadingPopularList.isVisible(true, popularList)
* loadingRecipesList.isVisible(true, recipesList)*/
@AndroidEntryPoint
class RecipeFragment : Fragment() {
    //Binding
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var popularAdapter: PopularAdapter

    @Inject
    lateinit var recentAdapter: RecentAdapter

    //Other
    private val viewModel: RecipeViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val args: RecipeFragmentArgs by navArgs()
    private var autoScrollIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Show username
        lifecycleScope.launch { showUsername() }
        //Call data
        callPopularData()
        //lifecycleScope.launch { }
        callRecentData()

        //Load data
        loadPopularData()
        loadRecentData()
    }

    //---Popular---//
    private fun callPopularData() {
        initPopularRecycler()
        viewModel.readPopularFromDb.onceObserve(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                database[0].response.results?.let { result ->
                    //setupLoading(false, binding.popularList)
                    fillPopularAdapter(result.toMutableList())
                }
            } else {
                viewModel.callPopularApi(viewModel.popularQueries())
            }
        }
    }


    private fun loadPopularData() {
        binding.apply {
            viewModel.popularData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        loadingPopularList.isVisible(true, popularList)
                    }

                    is NetworkRequest.Success -> {
                        loadingPopularList.isVisible(false, popularList)
                        response.data?.let { data ->
                            if (data.results!!.isNotEmpty()) {
                                fillPopularAdapter(data.results.toMutableList())
                            }
                        }
                    }

                    is NetworkRequest.Error -> {
                        loadingPopularList.isVisible(false, popularList)
                        binding.root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun fillPopularAdapter(result: MutableList<ResponseRecipes.Result>) {
        popularAdapter.setData(result)
        autoScrollPopular(result)
    }

    private fun initPopularRecycler() {
        val snapHelper = LinearSnapHelper()
        binding.popularList.setupRecyclerView(
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
            popularAdapter
        )
        //Snap
        snapHelper.attachToRecyclerView(binding.popularList)
        //Click
        popularAdapter.setOnItemClickListener {
            gotoDetailPage(it)
        }
    }

    private fun autoScrollPopular(list: List<ResponseRecipes.Result>) {
        lifecycleScope.launchWhenCreated {
            repeat(Constants.REPEAT_TIME) {
                delay(Constants.DELAY_TIME)
                if (autoScrollIndex < list.size) {
                    autoScrollIndex += 1
                } else {
                    autoScrollIndex = 0
                }
                binding.popularList.smoothScrollToPosition(autoScrollIndex)
            }
        }
    }

    //---Recent---//
    private fun callRecentData() {
        initRecentRecycler()
        viewModel.readRecentFromDb.onceObserve(viewLifecycleOwner) { database ->
            if (database.isNotEmpty() && database.size > 1 && !args.isUpdateData) {
                database[1].response.results?.let { result ->
                    //setupLoading(false, binding.recipesList)
                    recentAdapter.setData(result)
                }
            } else {
                lifecycleScope.launch {
                    viewModel.callRecentApi(viewModel.recentQueries())
                }
            }
        }
    }

    private fun loadRecentData() {
        binding.apply {
            viewModel.recentData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        loadingRecipesList.isVisible(true, recipesList)
                    }
                    is NetworkRequest.Success -> {
                        loadingRecipesList.isVisible(false, recipesList)
                        response.data?.let { data ->
                            if (data.results!!.isNotEmpty()) {
                                recentAdapter.setData(data.results)
                            }
                        }
                    }
                    is NetworkRequest.Error -> {
                        loadingRecipesList.isVisible(false, recipesList)
                        binding.root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun initRecentRecycler() {
        binding.recipesList.setupRecyclerView(
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


    @SuppressLint("SetTextI18n")
    suspend fun showUsername() {
        registerViewModel.readData.collect {
            binding.usernameTxt.text = "${getString(R.string.hello)}, ${it.username} ${getEmojiByUnicode()}"
        }
    }

    private fun getEmojiByUnicode(): String {
        return String(Character.toChars(0x1f44b))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}