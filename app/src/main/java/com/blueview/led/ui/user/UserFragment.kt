package com.blueview.led.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blueview.led.R

class UserFragment: Fragment() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModel=ViewModelProvider(this)[UserViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_user,container,false)
        userViewModel.text.observe(viewLifecycleOwner,Observer{

        })
        return root
    }
}