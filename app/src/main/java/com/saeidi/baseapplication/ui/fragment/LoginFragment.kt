package com.saeidi.baseapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.ui.fragment.base.BaseFragment

class LoginFragment : BaseFragment() {

    init {
        setTitle(R.string.login)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
}