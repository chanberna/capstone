package com.example.unichatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etRollNumber: EditText
    private lateinit var etSid: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        // This call the parent constructor.
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class.
        setContentView(R.layout.activity_sign_up)
        // Initialize Firebase Auth.
        mAuth = FirebaseAuth.getInstance()

        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        etRollNumber = findViewById(R.id.et_roll_num)
        etSid = findViewById(R.id.et_sid)
        etPassword = findViewById(R.id.et_password)
        btnSignUp = findViewById(R.id.btn_sign_up)

        btnSignUp.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val roll_num = etRollNumber.text.toString()
            val sid = etSid.text.toString()
            val password = etPassword.text.toString()

            signUp(name, email, roll_num, sid, password)
        }
    }

    private fun signUp(name: String, email: String, roll_num: String, sid: String, password: String) {
        // Logic of creating user.
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, roll_num, sid, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUpActivity, "Please enter your information.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, roll_num: String, sid: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, roll_num, sid, uid))

    }

} // End
