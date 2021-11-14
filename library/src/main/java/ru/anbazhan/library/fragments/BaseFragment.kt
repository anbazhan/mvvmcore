package ru.anbazhan.library.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver

abstract class BaseFragment<VM : FragmentViewModel, B : ViewDataBinding> : Fragment() {

    companion object {
        const val IS_RE_INIT_NEED = "isReInitNeed"
    }

    protected lateinit var binding: B

    abstract val layoutResId: Int
    abstract val viewModelBRVarId: Int
    abstract val viewModel: VM

    protected var fragmentContainer: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        configureBinding()

        observeViewModel()
        viewModel.reInit(arguments)

        fragmentContainer = container

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.getBoolean(IS_RE_INIT_NEED)) {
                viewModel.reInit(it)
                it.putBoolean(IS_RE_INIT_NEED, false)
            }
        }
    }

    protected open fun configureBinding() {
        if (viewModelBRVarId > 0) {
            binding.setVariable(viewModelBRVarId, viewModel)
        }
    }

    protected open fun observeViewModel() {}
}