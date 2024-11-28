package com.ql2.myshop.ui.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ql2.myshop.BuildConfig
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentSettingBinding
import com.ql2.myshop.domain.UserAppSession
import com.ql2.myshop.ui.login.LoginActivity
import com.ql2.myshop.utils.AppDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment :  BaseFragment<FragmentSettingBinding>() {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    @Inject
    lateinit var userAppSession: UserAppSession

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stringBuilder = StringBuilder()
        stringBuilder.append(getString(R.string.app_name))
            .append("\n${BuildConfig.VERSION_NAME}").append(" ")
            .append(BuildConfig.FLAVOR)
            .append(BuildConfig.BUILD_TYPE)
            .append("\n${BuildConfig.BASE_API_URL}")
        binding.infoAppTextView.text = stringBuilder.toString()
        binding.logoutButton.setOnClickListener {
            AppDialog.displayConfirmDialog(
                requireContext(), R.string.dialog_logout_confirm_title,
                R.string.dialog_logout_confirm_message,
                R.string.ok, R.string.cancel
            ) { _, _ ->
                userAppSession.clearUser()
                startActivity(Intent(requireActivity(),LoginActivity::class.java))
                requireActivity().finish()
            }
        }

    }
}