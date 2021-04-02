package com.example.worldcinemademex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)

        signInBtn.setOnClickListener {
            val email = e_mail.text.toString()
            val pass = password.text.toString()

            if( email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email заполнен не корректно", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = JsonObjectRequest(
                    Request.Method.POST,
                    "http://192.168.43.158/auth/login",

                    JSONObject()
                            .put("email", email)
                            .put("password", pass),

                    { response ->
                        Toast.makeText(this, "${response["token"]}", Toast.LENGTH_SHORT).show()
                    },

                    { error ->
                        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    }
            )

            requestQueue.add(request)
        }

        registrationBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivitY::class.java)
            startActivity(intent)

            finish()
        }
    }
}