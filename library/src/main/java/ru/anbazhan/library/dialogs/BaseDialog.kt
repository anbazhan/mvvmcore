package ru.anbazhan.library.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseDialog<VM : BaseDialogViewModel, B : ViewDataBinding> : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ru.anbazhan.library.base.ViewModelFactory<VM>

    protected abstract val layoutResId: Int
    protected abstract val viewModelType: Class<VM>
    protected abstract val viewModelBRVarId: Int

    protected val viewModel: VM by lazy {
        ViewModelProvider(this as DialogFragment, viewModelFactory).get(viewModelType)
    }
    protected lateinit var binding: B

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, theme)
        if (arguments == null) {
            arguments = Bundle()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.setVariable(viewModelBRVarId, viewModel)
        lifecycle.addObserver(viewModel)
        arguments?.let {
            viewModel?.reInit(it)
        } ?: run {
            viewModel.reInit(Bundle())
        }
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        viewModel.closeAction = { dismiss() }
        return dialog
    }

    protected fun observeViewModel() {}
    protected abstract fun inject()
}