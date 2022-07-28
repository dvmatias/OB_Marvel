package com.cmdv.core.base

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

private const val TAG = "FRAGMENT :: "

/**
 * Base Fragment class. Every Fragment in the app should extend (be a subclass) of this [BaseFragment] class. Subclassed
 * fragments must implement data binding pattern.
 *
 * @param layoutResId Fragment layout resource ID.
 * @param F Fragment class type.
 * @param B Fragment binding class type.
 */
abstract class BaseFragment<in F, B>(
    @LayoutRes private val layoutResId: Int, @MenuRes private val menuResId: Int? = null
) : Fragment() where F : Fragment, B : ViewDataBinding {
    /**
     * Fragment layout binding.
     */
    protected lateinit var binding: B
    /**
     * Initialize the view. Executed in onStart system callback instance.
     */
    protected abstract fun initView()
    /**
     * Declare observe instances. Executed in onStart system callback instance.
     */
    open fun observe() {}

    // 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = this

        logFragmentClassName()
        return binding.root
    }

    // 2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (menuResId != null) setHasOptionsMenu(true)
    }

    // 3
    override fun onStart() {
        super.onStart()
        initView()
        observe()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (menuResId != null) inflater.inflate(menuResId, menu)
    }

    /**
     * Log helper function to display Fragment name.
     */
    @Suppress("UNCHECKED_CAST")
    private fun logFragmentClassName() {
        Log.d("  ->  $TAG", (this as F)::class.java.simpleName)
    }
}