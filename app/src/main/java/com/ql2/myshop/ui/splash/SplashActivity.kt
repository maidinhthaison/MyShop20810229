package com.ql2.myshop.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.ql2.myshop.base.BaseActivity
import com.ql2.myshop.databinding.ActivitySplashBinding
import com.ql2.myshop.domain.UserAppSession
import com.ql2.myshop.main.MainActivity
import com.ql2.myshop.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var userAppSession: UserAppSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userSession = userAppSession.getUser()

        if (userSession?.rememberMe == true) {
            Timber.d(">>>Username ${userSession.username} - Password ${userSession.password}")
            //go to main activity
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            //go to login activity
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }

    override fun initBindingObject(inflater: LayoutInflater): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)


}