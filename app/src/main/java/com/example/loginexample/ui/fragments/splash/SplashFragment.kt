package com.example.loginexample.ui.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginexample.R
import com.example.loginexample.databinding.FragmentSplashBinding
import com.example.loginexample.ui.fragments.login.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        viewModel.isUserLoggedIn().observe(viewLifecycleOwner) { loggedIn ->
            if (loggedIn) findNavController().navigate(R.id.splash_to_home)
            else findNavController().navigate(R.id.splash_to_login)
        }
    }
}