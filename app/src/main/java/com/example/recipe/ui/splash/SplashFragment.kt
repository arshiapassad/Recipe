package com.example.recipe.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.recipe.BuildConfig
import com.example.recipe.R
import com.example.recipe.databinding.FragmentRegisterBinding
import com.example.recipe.databinding.FragmentSplashBinding
import com.example.recipe.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    //Binding
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    //Other
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Dynamically background
            bgImg.load(R.drawable.bg_splash)
            //Application Version
            versionTxt.text = "${getString(R.string.version)} : ${BuildConfig.VERSION_NAME}"
            //Auto navigate
            lifecycleScope.launchWhenCreated {
                delay(2500)
                //Check user info
                viewModel.readData.asLiveData().observe(viewLifecycleOwner) {
                    findNavController().popBackStack(R.id.splashFragment, true)
                    if (it.username.isNotEmpty()) {
                        findNavController().navigate(R.id.actionToRecipe)
                    } else {
                        findNavController().navigate(R.id.actionToRegister)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}