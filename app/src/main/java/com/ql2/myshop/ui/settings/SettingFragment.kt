package com.ql2.myshop.ui.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ql2.myshop.BuildConfig
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentSettingBinding
import com.ql2.myshop.domain.local.ConfigServer
import com.ql2.myshop.domain.local.SettingApp
import com.ql2.myshop.domain.local.UserAppSession
import com.ql2.myshop.domain.model.setting.SettingModel
import com.ql2.myshop.ui.login.LoginActivity
import com.ql2.myshop.utils.AppDialog
import com.ql2.myshop.utils.LIMIT_DEFAULT
import com.ql2.myshop.utils.TOP_LIMIT_DEFAULT
import com.ql2.myshop.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    @Inject
    lateinit var userAppSession: UserAppSession
    @Inject
    lateinit var settingApp: SettingApp
    @Inject
    lateinit var configServer: ConfigServer

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val configModel = configServer.getConfig()
        val server = if(configModel == null) BuildConfig.BASE_API_URL
                    else configServer.getServer(configModel)
        val stringBuilder = StringBuilder()
        stringBuilder.append(getString(R.string.app_name))
            .append("\n${BuildConfig.VERSION_NAME}").append(" ")
            .append(BuildConfig.FLAVOR)
            .append(BuildConfig.BUILD_TYPE)
            .append("\n$server")
        binding.infoAppTextView.text = stringBuilder.toString()

        binding.logoutButton.setOnClickListener {
            AppDialog.displayConfirmDialog(
                requireContext(), R.string.dialog_logout_confirm_title,
                R.string.dialog_logout_confirm_message,
                R.string.ok, R.string.cancel
            ) { _, _ ->
                userAppSession.clearUser()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
        // Switch
        if (binding.sortSwitch.isChecked) binding.tvSortStatus.text =
            getString(R.string.sort_status_asc)
        else binding.tvSortStatus.text = getString(R.string.sort_status_desc)
        binding.sortSwitch.setOnCheckedChangeListener { _, isChecked ->
            run {
                binding.tvSortStatus.text = if (isChecked) {
                    getString(R.string.sort_status_asc)
                } else {
                    getString(R.string.sort_status_desc)
                }
            }
        }
        //Limit Offset
        ArrayAdapter.createFromResource(
            requireContext(), R.array.product_limit,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            run {
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerLimit.adapter = adapter
            }
        }
        binding.spinnerLimit.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    val limit = parent?.getItemAtPosition(position).toString()
                    Timber.d("spinnerLimit: $limit")
                    binding.tvLimitOffset.text =
                        String.format(getString(R.string.label_limit_offset), limit)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        //Limit Dashboard
        ArrayAdapter.createFromResource(
            requireContext(), R.array.product_dashboard,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            run {
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerDashboardLimit.adapter = adapter
            }
        }
        binding.spinnerDashboardLimit.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    val limit = parent?.getItemAtPosition(position).toString()
                    Timber.d("spinnerDashboardLimit: $limit")
                    binding.tvLimitDashboard.text =
                        String.format(getString(R.string.label_limit_dashboard), limit)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        // Init value for spinner read from cached
        val settingModel = settingApp.getSetting()
        if (settingModel != null) {
            val limitPosition = settingModel.limit?.let { getLimitPosition(it) }
            val limitDashboardPosition = settingModel.limitDashboard?.let { getTopLimitPosition(it) }

            limitPosition?.let { binding.spinnerLimit.setSelection(it) }
            limitDashboardPosition?.let { binding.spinnerDashboardLimit.setSelection(it) }

            binding.tvLimitOffset.text = String.format(
                getString(R.string.label_limit_dashboard),
                binding.spinnerLimit.getItemAtPosition(limitPosition!!)
            )

            binding.tvLimitDashboard.text = String.format(
                getString(R.string.label_limit_dashboard),
                binding.spinnerDashboardLimit.getItemAtPosition(limitDashboardPosition!!)
            )

            binding.tvLimitOffset.text = String.format(
                getString(R.string.label_limit_dashboard),
                binding.spinnerLimit.getItemAtPosition(limitPosition)
            )

            binding.tvLimitDashboard.text = String.format(
                getString(R.string.label_limit_dashboard),
                binding.spinnerDashboardLimit.getItemAtPosition(limitDashboardPosition)
            )
        }else{
            val limitPosition = getLimitPosition(LIMIT_DEFAULT)
            val limitDashboardPosition = getTopLimitPosition(TOP_LIMIT_DEFAULT)

            binding.spinnerLimit.setSelection(limitPosition)
            binding.spinnerDashboardLimit.setSelection(limitDashboardPosition)

            binding.tvLimitOffset.text = String.format(
                getString(R.string.label_limit_dashboard),
                binding.spinnerLimit.getItemAtPosition(limitPosition)
            )

            binding.tvLimitDashboard.text = String.format(
                getString(R.string.label_limit_dashboard),
                binding.spinnerDashboardLimit.getItemAtPosition(limitDashboardPosition)
            )
        }
        // Save button
        binding.saveButton.setOnClickListener {
            settingApp.saveSetting(
                SettingModel(
                    sort = binding.tvSortStatus.text.toString(),
                    limit = binding.spinnerLimit.selectedItem.toString().toInt(),
                    limitDashboard = binding.spinnerDashboardLimit.selectedItem.toString().toInt()
                )
            )
            showToast(requireContext(), getString(R.string.save_setting_success))
        }
    }
    private fun getLimitPosition(value : Int) : Int{
        return context?.resources?.getStringArray(R.array.product_limit)?.indexOf("$value") ?: 0
    }
    private fun getTopLimitPosition(value : Int) : Int{
        return context?.resources?.getStringArray(R.array.product_dashboard)?.indexOf("$value") ?: 0
    }
}