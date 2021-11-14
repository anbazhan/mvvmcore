package ru.anbazhan.library.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VM : BaseActivityViewModel, B : ViewDataBinding> : AppCompatActivity() {

    abstract val layoutIdRes: Int

    abstract val viewModelBRVarId: Int

    abstract val viewModel: VM

    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()

        binding = DataBindingUtil.setContentView(this, layoutIdRes)
        binding.setVariable(viewModelBRVarId, viewModel)
        binding.lifecycleOwner = this
    }

    protected open fun observeViewModel() {
        lifecycle.addObserver(viewModel)
    }
}