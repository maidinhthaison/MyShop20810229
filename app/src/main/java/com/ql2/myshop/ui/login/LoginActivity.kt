package com.ql2.myshop.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseActivity
import com.ql2.myshop.databinding.ActivityLoginBinding
import com.ql2.myshop.domain.local.ConfigServer
import com.ql2.myshop.domain.local.UserAppSession
import com.ql2.myshop.domain.model.login.UserModel
import com.ql2.myshop.main.MainActivity
import com.ql2.myshop.ui.config.ConfigActivity
import com.ql2.myshop.utils.AppDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    @Inject
    lateinit var userAppSession: UserAppSession

    @Inject
    lateinit var configServer: ConfigServer

    private lateinit var launcher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListeners()
        binding.loginButton.setOnClickListener {
            if (isValidate()) {
                val username = binding.usernameEditText.text?.trim().toString()
                val password = binding.passwordEditText.text?.trim().toString()
                val rememberMe= binding.rememberMeCheckbox.isChecked
                if (username == UserAppSession.MOCK_USERNAME && password ==  UserAppSession.MOCK_PASSWORD){
                    userAppSession.saveUser(UserModel(username, password, rememberMe))
                    startActivity(Intent(this, MainActivity::class.java))
                    this.finish()
                }else {

                    AppDialog.displayErrorMessage(
                        this, R.string.dialog_login_error_title,
                        R.string.dialog_login_error_message,
                        R.string.ok
                    ) { _, _ -> }
                }

            }

        }
        launcher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                Toast.makeText(this, "Config saved", Toast.LENGTH_SHORT).show()
                /*val data = result.data
                configServer.saveConfig(ConfigModel(data?.getStringExtra(CONFIG_SERVER).toString()
                    , data?.getStringExtra(CONFIG_PORT).toString()
                ))*/
                val configModel = configServer.getConfig()
                Timber.d(configModel?.server.plus(":${configModel?.port}"))
            }
        }
        binding.imvSetting.setOnClickListener {
            val intent =  Intent(this, ConfigActivity::class.java)
            launcher.launch(intent)
        }
    }

    override fun initBindingObject(inflater: LayoutInflater): ActivityLoginBinding =
        ActivityLoginBinding.inflate(layoutInflater)

    private fun isValidate(): Boolean =
        validateUsername() && validatePassword()


    private fun setupListeners() {
        binding.usernameEditText.addTextChangedListener(TextFieldValidation(binding.usernameEditText))
        binding.passwordEditText.addTextChangedListener(TextFieldValidation(binding.passwordEditText))

    }

    /**
     * applying text watcher on each text field
     */
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.usernameEditText -> {
                    validateUsername()
                }

                R.id.passwordEditText -> {
                    validatePassword()
                }

            }
        }
    }

    /**
     * 1) field must not be empty
     */
    private fun validateUsername(): Boolean {
        return if (binding.usernameEditText.text.toString().trim().isEmpty()) {
            binding.tilUsername.error = "Required Field!"
            binding.usernameEditText.requestFocus()
            false
        } else {
            binding.tilUsername.isErrorEnabled = false
            true
        }
    }

    /**
     * 1) field must not be empty
     */
    private fun validatePassword(): Boolean {

        return if (binding.passwordEditText.text.toString().trim().isEmpty()) {
            binding.tilPassword.error = "Required Field!"
            binding.passwordEditText.requestFocus()
            false
        } else {
            binding.tilPassword.isErrorEnabled = false
            true
        }
    }

    companion object {
        const val CONFIG_SERVER = "config_server"
        const val CONFIG_PORT = "config_port"
    }
}