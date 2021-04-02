package com.example.worldcinemademex

import android.app.VoiceInteractor
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.android.volley.AsyncRequestQueue
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.worldcinemademex.network.Urls
import kotlinx.android.synthetic.main.activity_register_activit_y.*
import org.json.JSONObject

class RegisterActivitY : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_activit_y)

        requestQueue = Volley.newRequestQueue(this)

        registerBtn.setOnClickListener {
            val firstName = firstname.text.toString()
            val lastName = lastname.text.toString()
            val email = e_mail.text.toString()
            val pass = pass.text.toString()
            val repeatPass = repeatpass.text.toString()

            if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || pass.isEmpty() || repeatPass.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Не правильно заполнен Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != repeatPass) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val requestJson = JSONObject()
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("email", email)
                .put("password", pass )

            val request = JsonObjectRequest(
                Request.Method.POST,
                Urls.REGISTER_URL,
                requestJson,

                { response ->
                    Toast.makeText(this, "${response["token"]}", Toast.LENGTH_SHORT).show()
                },

                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    error.printStackTrace()
                }

            )

            requestQueue.add(request)
        }

        alreadyhaveaccount.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}