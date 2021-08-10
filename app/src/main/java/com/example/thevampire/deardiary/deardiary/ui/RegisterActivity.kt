package com.example.thevampire.deardiary.deardiary.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.thevampire.deardiary.R
import com.example.thevampire.deardiary.databinding.ActivityRegisterBinding
import com.example.thevampire.deardiary.deardiary.repositories.DiaryAccount
import com.example.thevampire.deardiary.deardiary.ui.viewmodels.RegisterViewModel
import com.example.thevampire.deardiary.deardiary.ui.viewmodels.UIState
import com.example.thevampire.deardiary.deardiary.utils.isValidEmail
import com.example.thevampire.deardiary.deardiary.utils.showMessage
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.flow.collectLatest


class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding

    val viewModel : RegisterViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.registerToolbar.ltoolbar)
        supportActionBar?.title = "Register User"

        setUpUI()
        setUpObservers()
    }

    private fun setUpObservers() = lifecycleScope.launchWhenStarted {
        viewModel.uiState.collectLatest {
            when(it){
                is UIState.AccountCreated ->{
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root,"Account Created, Verification Email Sent!",Snackbar.LENGTH_SHORT).show()
                }
                is UIState.Error ->{
                    binding.progressBar.visibility = View.GONE
                    if(it.message.isNotEmpty())
                        Snackbar.make(binding.root,it.message,Snackbar.LENGTH_SHORT).show()
                }
                is UIState.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUpUI(){
        with(binding){
            registerBtn.setOnClickListener {
                val name = editName.text.toString()
                val email = editEmail.text.toString()
                val password = editPassword.text.toString()
                viewModel.createUser(DiaryAccount(name,email,password))
            }
        }
    }


}
