package com.example.thevampire.deardiary.deardiary.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.thevampire.deardiary.databinding.ActivityMainBinding
import com.example.thevampire.deardiary.deardiary.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        if(viewModel.isUserLoggedIn()){
            startActivity(Intent(this,FeedActivity::class.java))
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.signInToolbar.ltoolbar)
        binding.signInToolbar.ltoolbar.title = "Log In"

        setupUI()
        setUpObservers()
    }

    private fun setUpObservers(){
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest {
                when(it){
                    is MainViewModel.LoginScreenUIState.LoginSuccess -> {
                        binding.progressBar.visibility = View.GONE
                        startActivity(Intent(this@MainActivity,FeedActivity::class.java))
                        finish()
                    }
                    is MainViewModel.LoginScreenUIState.Error ->{
                        binding.progressBar.visibility = View.GONE
                        Snackbar.make(binding.root,it.message,Snackbar.LENGTH_SHORT).show()
                    }

                    is MainViewModel.LoginScreenUIState.Loading ->{
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is MainViewModel.LoginScreenUIState.ResetAccount ->{
                        binding.progressBar.visibility = View.GONE
                        Snackbar.make(binding.root,it.message,Snackbar.LENGTH_SHORT).show()
                    }

                    else -> { binding.progressBar.visibility = View.GONE}
                }
            }
        }
    }

    private fun setupUI(){
        with(binding){
            loginButton.setOnClickListener {
                val email = editId.text.toString()
                val password = editPassword.text.toString()
                viewModel.loginUser(email, password) }

            textForgot.setOnClickListener {
                val email = editId.text.toString()
                viewModel.forgotPassword(email)
            }

            buttonRegister.setOnClickListener{ startActivity(Intent(this@MainActivity, RegisterActivity::class.java))}
        }
    }

}
