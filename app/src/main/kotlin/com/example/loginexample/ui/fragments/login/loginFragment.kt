package com.example.loginexample.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginexample.R
import com.example.loginexample.databinding.FragmentLoginBinding
import com.example.loginexample.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class loginFragment : Fragment(), StateListener {           // The name of the class "loginFragment" should be written in Pascal casing

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = viewModel
        viewModel.stateListener = this

        //Navigate to register fragment
        binding.linearLayoutRegister.setOnClickListener {
            findNavController().navigate(R.id.login_to_registration)
        }


        return binding.root
    }

    override fun onLoading() {
        binding.progressBarLogin.show()
    }

    override fun onSuccess(message: String?) {
        binding.progressBarLogin.hide()

        requireActivity().toast(message!!)
        log(message)

        findNavController().navigate(R.id.login_to_home)
    }

    override fun onError(message: String) {
        binding.progressBarLogin.hide()

        if (isAdded){
            requireActivity().toast(message)
            log(message)
        }
    }
}
