package com.example.pizzaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //activity text
        val txtEmail: EditText = findViewById(R.id.registerEmail)
        val txtName: EditText = findViewById(R.id.registerPersonName)
        val txtLevel: EditText = findViewById(R.id.registerLevel)
        val txtPassword: EditText = findViewById(R.id.registerPassword)
        //instan vuton
        val btnRegister: Button = findViewById(R.id.buttonRegisterAccount)

        btnRegister.setOnClickListener{
            //objext class databes
            val databaseHelper = DatabaseHelper(this)
            //declare data
            val email:String = txtEmail.text.toString().trim()
            val name:String = txtName.text.toString().trim()
            val level:String = txtLevel.text.toString().trim()
            val password:String = txtPassword.text.toString().trim()

            //checkdata ->email didaftarkan / belum
            val data: String = databaseHelper.checkData(email)
            //jika belum
            if (data == null){
                //insertdata
                databaseHelper.addAccount(email, name, level, password)

                //show loginactivity
                val intentLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intentLogin)
            }else{
                //jika email terdaftar
                Toast.makeText(this@RegisterActivity,"Register Failed!" + "Your Email already registered",
                Toast.LENGTH_SHORT).show()
            }
        }
    }


}