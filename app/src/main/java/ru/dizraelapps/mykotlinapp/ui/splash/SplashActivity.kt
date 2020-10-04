package ru.dizraelapps.mykotlinapp.ui.splash

import org.koin.android.viewmodel.ext.android.viewModel
import ru.dizraelapps.mykotlinapp.ui.base.BaseActivity
import ru.dizraelapps.mykotlinapp.ui.main.MainActivity

class SplashActivity: BaseActivity<Boolean?>() {

    override val viewModel: SplashViewModel by viewModel()

    override val layoutRes = null

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    fun startMainActivity(){
        MainActivity.start(this)
        finish()
    }
}