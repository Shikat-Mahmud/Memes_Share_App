package com.example.memessharingapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var memeImageView: ImageView
    private lateinit var shareButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memeImageView = findViewById(R.id.memeImageView)
        shareButton = findViewById(R.id.shareButton)
        nextButton = findViewById(R.id.nextButton)

        // Fetch the meme from the API
        fetchMeme()

        // Set up the click listeners for the share and next buttons
        shareButton.setOnClickListener {
            // Share the meme with friends
            shareMeme()
        }

        nextButton.setOnClickListener {
            // Generate the next meme
            fetchMeme()
        }
    }

    private fun fetchMeme() {
        // Create a Volley request to fetch the meme from the API
        val request = StringRequest(
            Method.GET,
            "https://meme-api.com/gimme",
            { response ->
                // Parse the JSON response and get the meme URL
                val memeUrl = JSONObject(response).getString("url")

                // Load the meme image into the ImageView
                Glide.with(this).load(memeUrl).into(memeImageView)
            },
            { error ->
                // Handle the error
                Toast.makeText(this, "There is an error!", Toast.LENGTH_LONG).show()
            }
        )

        // Add the request to the Volley queue
        Volley.newRequestQueue(this).add(request)
    }

    private fun shareMeme() {
        // Create an Intent to share the meme image
        val intent = Intent(Intent.ACTION_SEND)
        val memeUri: Uri = Uri.parse(memeImageView.drawable.toString())

        intent.type = "image/jpeg/jpg"
        intent.putExtra(Intent.EXTRA_STREAM, memeUri)

        // Start the Intent to share the meme
        startActivity(intent)

    }
}