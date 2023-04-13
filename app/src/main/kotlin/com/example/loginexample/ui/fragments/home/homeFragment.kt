package com.example.loginexample.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.loginexample.R
import com.example.loginexample.databinding.FragmentHomeBinding
import com.example.loginexample.util.StateListener
import com.example.loginexample.util.log
import com.example.loginexample.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class homeFragment : Fragment(), StateListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        viewModel.loggedInUser.observe(viewLifecycleOwner) { user ->
            binding.textViewUsername.text = user.firstName!!
        }
    }

    override fun onLoading() {
    }

    override fun onSuccess(message: String?) {
        log(message!!)
    }

    override fun onError(message: String) {

        requireActivity().toast(message)
        log(message)
    }

}