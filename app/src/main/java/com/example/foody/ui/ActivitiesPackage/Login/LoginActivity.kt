package com.example.foody.ui.ActivitiesPackage.Login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.foody.R
import com.example.foody.ui.ActivitiesPackage.Register.RegisterActivity
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
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    private lateinit var googleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN = 999
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnlogin: Button
    private lateinit var btnsignup: Button
    private lateinit var btnlogin2: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var name:String

    // to get the view model itself not an instance of it
    // using by delegate
    private lateinit var sharedViewModel: ProfileSharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.WelcomeActivityStyle)
        setContentView(R.layout.activity_login)
        val hide = supportActionBar?.hide()


        sharedViewModel = ProfileSharedViewModel()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.email_login)
        edtPassword = findViewById(R.id.password_login)
        btnlogin = findViewById(R.id.login_button)
        btnsignup = findViewById(R.id.signup_button_login)
        btnlogin2 = findViewById(R.id.login_button_login)

        val forget_passowrd = findViewById<TextView>(R.id.Forget_password_login)

        val google_signup = findViewById<CircleImageView>(R.id.google_image)
        google_signup.setOnClickListener {
            logingoogle()
        }

        forget_passowrd.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            val username = view.findViewById<EditText>(R.id.et_username)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
                forgot_password(username)
            })
            builder.setNegativeButton("close", DialogInterface.OnClickListener { _, _ -> })
            builder.show()


        }

        btnlogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password);
        }
        btnsignup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnlogin2.setOnClickListener {
            val intent1 = Intent(this, LoginActivity::class.java)
            startActivity(intent1)
            finish()
        }
        Not_a_member_login.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }


    }

    private fun forgot_password(username: EditText) {
        if (username.text.toString().isEmpty()) {
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            return
        }
        mAuth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Email not registered", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // get the name of the user and send it to the viewmodel
                         name="shrouk"
                             //task.result.user?.displayName.toString()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(
                        this@LoginActivity,
                        "Logging  in to your Account",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this@LoginActivity,
                        "Invalid Email or Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }



    private fun logingoogle() {
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
                    Log.d("LoginActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("LoginActivity", "Google sign in failed", e)
                }

            } else {
                Log.w("LoginActivity", exception.toString())

            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LoginActivity", "signInWithCredential:success")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LoginActivity", "signInWithCredential:failure", task.exception)

                }
            }
    }
}