package com.apps.kunalfarmah.furnituremagic

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var phone: EditText? = null
    var otp: EditText? = null
    var get_otp: Button? = null
    var submit: Button? = null
    var resend: Button? = null
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var TAG = "SIGN_IN"
    var auth: FirebaseAuth? = null
    var storedVerificationId: String? = null
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    var mTextField: TextView? = null
    lateinit var code: String
    var user: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phone = findViewById(R.id.number)
        otp = findViewById(R.id.otp)
        get_otp = findViewById(R.id.send_OTP)
        get_otp!!.alpha = 0.2f

        submit = findViewById(R.id.submit)
        resend = findViewById(R.id.resend)
        mTextField = findViewById(R.id.timer)

        get_otp!!.setOnClickListener(this)
        submit!!.setOnClickListener(this)
        resend!!.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // we want to login with OTP so nothing happens on auto verification
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                phone!!.visibility = GONE
                get_otp!!.visibility = GONE

                otp!!.visibility = VISIBLE
                submit!!.visibility = VISIBLE

                resend!!.visibility = VISIBLE
                resend!!.alpha = 0.1f

                mTextField!!.visibility = VISIBLE

                object : CountDownTimer(60000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        mTextField!!.setText("Resend OTP in: " + (millisUntilFinished) / 1000)
                    }

                    override fun onFinish() {
                        mTextField!!.visibility = GONE
                        resend!!.alpha = 1f
                    }
                }.start()

            }
        }

        phone!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.toString().length == 10) {
                    get_otp!!.alpha = 1f
                }
            }
        })


    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.send_OTP -> {
                var text = phone!!.text.toString()
                if (text.length < 10 || !isNumeric(text)) {
                    Toast.makeText(this, "Phone Number Invalid", Toast.LENGTH_SHORT).show()
                    return
                }
                text = "+91" + text
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    text, // Phone number to verify
                    60, // Timeout duration
                    TimeUnit.SECONDS, // Unit of timeout
                    this@MainActivity, // Activity (for callback binding)
                    callbacks
                )
            }

            R.id.submit -> {
                code = otp!!.text.toString()
                verifyPhoneNumberWithCode(storedVerificationId, code)
            }

            R.id.resend -> {
                resendVerificationCode("+91" + phone!!.text.toString(), resendToken)
            }
        }
    }

    private fun isNumeric(text: String?): Boolean {
        var t = 0
        while (t < 10) {
            if (!(text!!.get(t) in '0'..'9'))
                return false
            ++t
        }
        return true
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    user = task.result?.user
                    Toast.makeText(
                        this@MainActivity, "Welcome " + user!!.phoneNumber.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                    startActivity(Intent(this@MainActivity, ProductActivity::class.java))
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this, "The OTP was Invalid", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth!!.currentUser

        if (currentUser != null) {
            Toast.makeText(
                this,
                "Welcome " + currentUser!!.phoneNumber.toString(),
                Toast.LENGTH_SHORT
            ).show()
            finish()
            startActivity(Intent(this@MainActivity, ProductsListActivity::class.java))
        }


    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }

    // [START resend_verification]
    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks, // OnVerificationStateChangedCallbacks
            token
        ) // ForceResendingToken from callbacks
    }
}