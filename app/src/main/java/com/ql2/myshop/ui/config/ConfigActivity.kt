package com.ql2.myshop.ui.config

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseActivity
import com.ql2.myshop.databinding.ActivityConfigBinding
import com.ql2.myshop.domain.local.ConfigServer
import com.ql2.myshop.domain.model.config.ConfigModel
import com.ql2.myshop.ui.login.LoginActivity
import com.ql2.myshop.utils.AppDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ConfigActivity : BaseActivity<ActivityConfigBinding>() {

    @Inject
    lateinit var configServer: ConfigServer

    override fun initBindingObject(inflater: LayoutInflater): ActivityConfigBinding =
        ActivityConfigBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpListener()
    }

    private fun setUpListener() {
        binding.serverEditText.addTextChangedListener(TextFieldValidation(binding.serverEditText))
        binding.portEditText.addTextChangedListener(TextFieldValidation(binding.portEditText))

        binding.saveButton.setOnClickListener {
            if(isValidate()){
                val server = binding.serverEditText.text?.trim().toString()
                val port = binding.portEditText.text?.trim().toString()
                configServer.saveConfig(ConfigModel(server, port))
                val intent = Intent(this, LoginActivity::class.java)
                setResult(RESULT_OK, intent)
                finish()
            }

        }
        binding.clearButton.setOnClickListener {
            AppDialog.displayErrorMessage(
                this, R.string.dialog_config_error_title,
                R.string.dialog_config_error_message,
                R.string.ok
            ) { _, _ -> }
        }
    }
    private fun isValidate(): Boolean =
        validateServer() && validatePort()
    /**
     * applying text watcher on each text field
     */
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.serverEditText -> {
                    validateServer()
                }

                R.id.portEditText -> {
                    validatePort()
                }

            }
        }
    }

    /**
     * 1) field must not be empty
     */
    private fun validateServer(): Boolean {
        return if (binding.serverEditText.text.toString().trim().isEmpty()) {
            binding.tilServer.error = "Required Field!"
            binding.serverEditText.requestFocus()
            false
        } else {
            binding.tilServer.isErrorEnabled = false
            true
        }
    }

    /**
     * 1) field must not be empty
     */
    private fun validatePort(): Boolean {

        return if (binding.portEditText.text.toString().trim().isEmpty()) {
            binding.tilPort.error = "Required Field!"
            binding.portEditText.requestFocus()
            false
        } else {
            binding.tilPort.isErrorEnabled = false
            true
        }
    }
}