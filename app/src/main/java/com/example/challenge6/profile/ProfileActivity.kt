package com.example.challenge6.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.challenge6.databinding.ActivityProfileBinding
import com.example.challenge6.home.HomeActivity
import com.example.challenge6.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private var _binding : ActivityProfileBinding? = null
    private val binding get() =_binding!!
    private lateinit var viewModel: ProfileViewModel
    private var image_uri: Uri? = null
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            Log.d("URI_IMG", result.toString())
            binding.ivImage.setImageURI(result)
            image_uri = result!!
            viewModel.setImageUri(result)
        }
    private val REQUEST_CODE_PERMISSION = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding.ivImage.setOnClickListener {
            checkingPermissions()
        }
        logout()
        setProfileBackground()
        viewModel.getId().observe(this){
            update(it)
            viewModel.getUserById(it)
            setField()
        }
        val image = BitmapFactory.decodeFile(this.applicationContext.filesDir.path + File.separator +"profiles"+ File.separator +"img-profile.png")
        binding.ivImage.setImageBitmap(image)
    }
    private fun setField() {
        viewModel.user.observe(this) { data ->
            binding.apply {
                if (data != null) {
                    textInputEditTextUsername.setText(data.username.toString())
                    textInputEditTextName.setText(data.namaLengkap.toString())
                    textInputEditTextAlamat.setText(data.alamat.toString())
                    textInputEditTextStatus.setText(data.status.toString())
                }
            }
        }
    }

    private fun setProfileBackground() {
        val image =
            BitmapFactory.decodeFile(this.applicationContext.filesDir.path + File.separator + "blur_outputs" + File.separator + "IMG-BLURRED.png")
        binding.ivImage2.setImageBitmap(image)
    }

    private fun checkingPermissions() {
        if (isGranted(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,)
        ){
            openGallery()
        }
    }

    private fun openGallery() {
        intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }
    private fun update(id: Int) {
        binding.btnUpdate.setOnClickListener {
            val username = binding.textInputEditTextUsername.text.toString()
            val namaLengkap = binding.textInputEditTextName.text.toString()
            val alamat = binding.textInputEditTextAlamat.text.toString()
            val status = binding.textInputEditTextStatus.text.toString()
            viewModel.saveImage(image_uri.toString())
            if (image_uri != null) {
                saveImage()
            }
            viewModel.saveUsername(username)
            viewModel.updateUser(id, username, namaLengkap, alamat, status)
            viewModel.applyBlur()
            Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveImage() {
        val resolver = this.applicationContext.contentResolver
        val picture = BitmapFactory.decodeStream(
            resolver.openInputStream(Uri.parse(image_uri.toString())))
        saveImageProfile(this, picture)
        viewModel.applyBlur()
    }

    private fun saveImageProfile(applicationContext: Context, bitmap: Bitmap): Uri {
        val name = "img-profile.png"
        val outputDir = File(applicationContext.filesDir, "profiles")
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val outputFile = File(outputDir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
        } finally {
            out?.let {
                try {
                    it.close()
                } catch (ignore: IOException) {
                }

            }
        }
        return Uri.fromFile(outputFile)
    }


    private fun logout(){
        binding.btnLogout.setOnClickListener {
            viewModel.removeIsLoginStatus()
            viewModel.removeUsername()
            viewModel.removeId()
            viewModel.removeImage()
            viewModel.getDataStoreIsLogin().observe(this){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

}