package com.example.foody.ui.ActivitiesPackage.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.foody.R
import com.example.foody.ui.ActivitiesPackage.Login.LoginActivity
import com.example.foody.ui.MainActivity
import com.example.foody.ui.fragments.profile.ProfileFragment
import com.example.foody.ui.fragments.profile.ProfileSharedViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.auth.OAuthProvider


class RegisterActivity : AppCompatActivity() {


    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var edtEmail: EditText
    private var RC_SIGN_IN = 999
    private lateinit var edtPassword: EditText
    private lateinit var btnsignup2: Button
    private lateinit var btnlogin: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var edtname: EditText

    // to get the view model itself not an instance of it
    // using by delegate
    private lateinit var sharedViewModel: ProfileSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.WelcomeActivityStyle)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        sharedViewModel = ProfileSharedViewModel()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)




        mAuth = FirebaseAuth.getInstance()


        edtname = findViewById(R.id.Name_register)
        edtEmail = findViewById(R.id.email_register)
        edtPassword = findViewById(R.id.password_register)
        btnsignup2 = findViewById(R.id.signup_button_signup)
        btnlogin = findViewById(R.id.login_button_signup)


        val sign_up_btn = findViewById<Button>(R.id.signup_button)
        val register_login_textView = findViewById<TextView>(R.id.register_login_textView)
        val google_signup = findViewById<CircleImageView>(R.id.google_image)
        val facebook_signup = findViewById<CircleImageView>(R.id.facebook_image)
        val twitter_signup = findViewById<CircleImageView>(R.id.twitter_image)




        google_signup.setOnClickListener {
            signup2()
        }

        sign_up_btn.setOnClickListener {
            val name = edtname.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            // send the user name  to the viewmodel
            val bundle = Bundle()
            bundle.putString("username",name)
            val profileFragment = ProfileFragment()
            profileFragment.arguments = bundle
            signup(email, password, name)
        }

        register_login_textView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        login_button_signup.setOnClickListener {
            val intent1 = Intent(this, LoginActivity::class.java)
            startActivity(intent1)
            finish()

        }
        signup_button_signup.setOnClickListener {
            val intent2 = Intent(this, RegisterActivity::class.java)
            startActivity(intent2)
            finish()
        }
    }

    private fun signup(email: String, password: String, name: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registering your account",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Password should be at least 8 characters ",
                        Toast.LENGTH_SHORT
                    ).show()


                }
            }
    }

    private fun signup2() {
        val signUpIntent = googleSignInClient.signInIntent
        startActivityForResult(signUpIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("RegisterActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("RegisterActivity", "Google sign in failed", e)
                }

            } else {
                Log.w("RegisterActivity", exception.toString())

            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("RegisterActivity", "signInWithCredential:success")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("RegisterActivity", "signInWithCredential:failure", task.exception)

                }
            }
    }


}