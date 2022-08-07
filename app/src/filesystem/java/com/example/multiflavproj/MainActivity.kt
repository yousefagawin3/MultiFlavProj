package com.example.multiflavproj

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.multiflavproj.Util.computeEquation
import com.example.multiflavproj.databinding.ActivityMainBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addInputBtn.setOnClickListener {
            startChooseImageIntentForResult()
        }
    }

    private fun startChooseImageIntentForResult() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(
//            Intent.createChooser(intent, "Select Picture"),
//            REQUEST_CHOOSE_IMAGE
//        )
        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            imageUri = result.data!!.data
            tryReloadAndDetectInImage()
        }
    }

    private fun tryReloadAndDetectInImage() {
        try {
            if (imageUri == null) {
                return
            } else {
                val image: InputImage = InputImage.fromFilePath(this, imageUri!!)

                val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                recognizer.process(image)
                    .addOnSuccessListener {
                        Log.e(TAG, "addOnSuccessListener -- ${it.text}")

                        binding.inputTv.text = getString(R.string.input_value, Util.getEquation(it.text))
                        binding.resultTv.text = getString(R.string.result_value, Util.getEquation(it.text).computeEquation().toString())
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "addOnFailureListener -- $it")
                        binding.inputTv.text = getString(R.string.input_value, it.message)
                        binding.resultTv.text = getString(R.string.input_value, it.message)
                    }
            }
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            imageUri = null
        }
    }

    companion object {
        private const val TAG = "com.example.multiflavproj.MainActivity"
    }
}