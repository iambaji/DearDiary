package com.example.thevampire.deardiary.deardiary.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import com.example.thevampire.deardiary.R
import com.example.thevampire.deardiary.deardiary.utils.isValidEmail
import com.example.thevampire.deardiary.deardiary.utils.showMessage
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {


    var firebaseauth = FirebaseAuth.getInstance()
    lateinit var progress_bar : ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(findViewById(R.id.register_toolbar))
        supportActionBar?.title = "Register User"

        progress_bar = findViewById(R.id.progress_bar)



    }

    fun updateProfile(user : FirebaseUser,name : String) : UserProfileChangeRequest
    {
        val requestProfileUpdate = UserProfileChangeRequest.Builder()
        requestProfileUpdate.setDisplayName(name)
        return requestProfileUpdate.build()
    }

    fun register(v : View)
    {
//        var error : String? = null
//        var eerror : String? = null
//        val name = edit_name.text.trim().toString()
//        val email = edit_email.text.trim().toString()
//        val pass = edit_password.text.trim().toString()
//        if(name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty())
//        {
//          eerror = if(email.isValidEmail())  null else "enter valid email address"
//          error = if(pass.length>6) null else "password must be above 6 char"
//           firebaseauth.createUserWithEmailAndPassword(email.toString(),pass.toString()).addOnCompleteListener {
//
//               if(it.isSuccessful)
//               {
//                   var user = it.result.user
//                   val updaterequest = updateProfile(user,name)
//                   user.updateProfile(updaterequest)
//                   it.result.user.sendEmailVerification().addOnSuccessListener {
//                       showMessage(v,"Verification email has been sent to your mail id")
//                   }
//
//
//                   startActivity(Intent(this,MainActivity::class.java))
//                   finish()
//               }
//               else
//               {
//                   showMessage(v,"Account Creation Failed!")
//               }
//
//           }
//
//
//
//
//        }
//        else
//        {
//            error = "you need to fill all fields"
//        }
//        if (error!=null) showMessage(register_activity,error.toString())
//        if(eerror!= null) showMessage(register_activity,eerror.toString())
    }
}
