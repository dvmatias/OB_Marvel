package com.cmdv.core.base

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<in F, B>(
    @LayoutRes private val layoutResId: Int, @MenuRes private val menuResId: Int? = null
) : Fragment() where F : Fragment, B : ViewDataBinding {

    protected lateinit var binding: B

    protected abstract fun initView()

    open fun observe() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (menuResId != null) setHasOptionsMenu(true)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = this

        logFragmentClassName((this as F)::class.java.simpleName)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observe()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (menuResId != null) inflater.inflate(menuResId, menu)
    }

    private fun logFragmentClassName(fragmentClassName: String) {
        Log.d("  ->  SCREEN :: ", fragmentClassName)
    }
}