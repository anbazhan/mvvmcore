package ru.anbazhan.library.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import ru.anbazhan.library.base.ViewModelFactory
import javax.inject.Inject

abstract class BaseActivity<VM : ActivityViewModel, B : ViewDataBinding> : AppCompatActivity() {

    abstract val viewModelType: Class<VM>

    abstract val layoutIdRes: Int

    abstract val viewModelBRVarId: Int

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<VM>

    protected val viewModel: VM by lazy {
        ViewModelProvider(this as AppCompatActivity, viewModelFactory).get(viewModelType)
    }

    protected lateinit var binding: B

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()

        lifecycle.addObserver(viewModel)

        binding = DataBindingUtil.setContentView(this, layoutIdRes)
        binding.setVariable(viewModelBRVarId, viewModel)
        binding.lifecycleOwner = this
    }
}