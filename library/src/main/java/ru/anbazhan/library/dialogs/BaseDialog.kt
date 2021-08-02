package ru.anbazhan.library.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialog<VM : BaseDialogViewModel, B : ViewDataBinding> : DialogFragment() {

    protected abstract val layoutResId: Int
    protected abstract val viewModelBRVarId: Int

    abstract val viewModel: VM
    protected lateinit var binding: B

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
}