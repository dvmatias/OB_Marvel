package com.cmdv.core.base

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cmdv.core.navigator.Navigator
import org.koin.android.ext.android.inject

private const val TAG = "ACTIVITY :: "

/**
 * Base Activity class. Every Activity in the app should extend (be a subclass) of this [BaseActivity] class. Subclassed
 * activities must implement data binding pattern.
 *
 * @param layoutResId Activity layout resource ID.
 * @param A Activity class type.
 * @param B Activity binding class type
 */
abstract class BaseActivity<in A, B>(
    @LayoutRes private val layoutResId: Int?
) : AppCompatActivity() where A : Activity, B : ViewDataBinding {
    /**
     * Navigator instance. Activity can navigate to another using this instance.
     */
    protected val navigator: Navigator by inject()
    /**
     * Navigator instance. Activity can navigate to another using this instance.
     */
    protected lateinit var binding: B
    /**
     * Implement this function if the activity has to take extra arguments when launched.
     */
    open fun getExtras() {}
    /**
     * Initialize the view. Executed in onCreate system callback instance.
     */
    abstract fun initView()
    /**
     * Declare observe instances. Executed in onCreate system callback instance.
     */
    protected abstract fun observe()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initialize()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun onActivityCreated(dataBinder: B) {
        binding = dataBinder
        binding.lifecycleOwner = this
    }

    /**
     * Init the Activity. This function is executed in onCreate instance.
     */
    private fun initialize() {
        getExtras()
        layoutResId?.let { id ->
            val dataBinder = DataBindingUtil.setContentView<B>(this, id)
            onActivityCreated(dataBinder)
        }
        logActivityClassName()
        initView()
        observe()
    }

    @Suppress("UNCHECKED_CAST")
    private fun logActivityClassName() {
        Log.d(TAG, (this as A)::class.java.simpleName)
    }
}