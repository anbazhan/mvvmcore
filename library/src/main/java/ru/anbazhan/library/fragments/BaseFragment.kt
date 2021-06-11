package ru.anbazhan.library.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject


abstract class BaseFragment<VM : FragmentViewModel, B : ViewDataBinding> : Fragment() {

    companion object {
        const val IS_RE_INIT_NEED = "isReInitNeed"
    }

    @Inject
    lateinit var viewModelFactory: ru.anbazhan.library.base.ViewModelFactory<VM>

    protected lateinit var binding: B

    abstract val viewModelType: Class<VM>
    abstract val layoutResId: Int
    abstract val viewModelBRVarId: Int

    protected var fragmentContainer: ViewGroup? = null

    val viewModel: VM by lazy {
        ViewModelProvider(this as Fragment, viewModelFactory).get(viewModelType)
    }

    abstract fun inject()

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeViewModel()

        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        configureBinding()

        viewModel.reInit(arguments)

        fragmentContainer = container

        return binding.root
    }

    protected open fun configureBinding() {
        if (viewModelBRVarId > 0)
            binding.setVariable(viewModelBRVarId, viewModel)
    }

    protected open fun observeViewModel() {
        lifecycle.addObserver(viewModel as LifecycleObserver)
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
}