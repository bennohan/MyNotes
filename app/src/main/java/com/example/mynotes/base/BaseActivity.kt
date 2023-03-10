package com.example.mynotes.base

import androidx.databinding.ViewDataBinding
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.base.viewmodel.CoreViewModel
import dagger.hilt.android.AndroidEntryPoint

open class BaseActivity<VB : ViewDataBinding, VM : CoreViewModel>(layoutRes: Int) : CoreActivity<VB, VM>(layoutRes) {
}