package com.example.scopedstoragetest

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

val FOLDER =
    (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
            + File.separator + "example")

const val REQUEST_CODE_STORE = 1000

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            checkStoragePermission(REQUEST_CODE_STORE) {
                storeTestFile()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_STORE -> {
                if (verifyPermissions(grantResults)) {
                    storeTestFile()

                } else {
                    Toast.makeText(this, "permissions not granted", Toast.LENGTH_SHORT).show()
                }
            }

            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun storeTestFile() {
        val success = storeFile("test.txt", "foo bar")
        Toast.makeText(this, if (success) "ok" else "error", Toast.LENGTH_SHORT).show()
    }

    private fun storeFile(filename: String, content: String): Boolean {
        return try {
            val file = File(FOLDER, filename).apply { parentFile.mkdirs() }
            FileOutputStream(file).use {
                DataOutputStream(it).use {
                    it.writeBytes(content)
                }
            }

            true

        } catch (ignored: IOException) {
            false
        }
    }
}
