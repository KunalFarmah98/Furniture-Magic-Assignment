package com.apps.kunalfarmah.furnituremagic.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import com.apps.kunalfarmah.furnituremagic.R
import com.apps.kunalfarmah.furnituremagic.Room.Furniture
import com.apps.kunalfarmah.furnituremagic.ViewModels.FurnitureViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ProductActivity : AppCompatActivity() {

    var name: String? = null
    var type: String? = null
    var price: String? = null
    var discPrice: String? = null
    var name_Et: EditText? = null
    var price_Et: EditText? = null
    var disc_Et: EditText? = null
    var img: ImageView? = null
    var back: Button? = null
    var save: Button? = null
    var furnitureViewModel: FurnitureViewModel? = null
    var image: String? = null
    var mCurrentPhotoPath: String? = null
    var camera: CardView? = null
    var gallery: CardView? = null
    var photoURI: Uri? = null
    var user: FirebaseUser? = null

    val MY_CAMERA_PERMISSION_CODE = 101
    val REQUEST_LOAD_IMAGE = 1001
    val REQUEST_TAKE_IMAGE = 1002
    val MY_STORAGE_PERMISSION_CODE = 102

    val TAG = "New Offer"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        supportActionBar!!.setTitle(getString(R.string.New_Offer))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        name_Et = findViewById(R.id.name)
        price_Et = findViewById(R.id.price)
        disc_Et = findViewById(R.id.disc_price)
        img = findViewById(R.id.image)

        back = findViewById(R.id.back)
        save = findViewById(R.id.save)

        furnitureViewModel = FurnitureViewModel(application)
        user = FirebaseAuth.getInstance().currentUser

        val options = resources.getStringArray(R.array.furniture)
        // access the spinner
        val spinner = findViewById<Spinner>(R.id.type)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                R.layout.spinner_item, options
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(
                        this@ProductActivity,
                        "Please Select a type", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        back!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                onBackPressed()
            }
        })

        save!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                name = name_Et!!.text.toString()
                price = price_Et!!.text.toString()
                discPrice = disc_Et!!.text.toString()
                type = spinner!!.selectedItem.toString()

                if (name.equals("")) {
                    Toast.makeText(
                        this@ProductActivity,
                        "Please Provide a Name",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (type.equals("Type")) {
                    Toast.makeText(this@ProductActivity, "Please Select a Type", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                if (price.equals("")) {
                    Toast.makeText(
                        this@ProductActivity,
                        "Please Provide a Price",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (image == null) {
                    Toast.makeText(this@ProductActivity, "Please Add an Image", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                var furniture = Furniture(name, type, price, discPrice, image, user!!.uid)

                furnitureViewModel!!.insertItem(furniture)

                finishAffinity()
                startActivity(Intent(this@ProductActivity, ProductsListActivity::class.java))
            }
        })


        img!!.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onClick(p0: View?) {
                val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
                val dialog = BottomSheetDialog(this@ProductActivity)
                dialog.setContentView(dialogView)
                dialog.show()
                camera = dialog.findViewById<CardView>(R.id.camera)
                camera!!.setOnClickListener {
                    dialog.cancel()
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(
                            arrayOf(Manifest.permission.CAMERA),
                            MY_CAMERA_PERMISSION_CODE
                        )
                    } else if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_STORAGE_PERMISSION_CODE
                        )
                    } else {
                        dispatchTakePictureIntent()
                    }
                }
                gallery = dialog.findViewById<CardView>(R.id.gallery)
                gallery!!.setOnClickListener {
                    dialog.cancel()
                    val gallery = Intent(
                        Intent.ACTION_OPEN_DOCUMENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(gallery, REQUEST_LOAD_IMAGE)
                }
                dialog.show()
            }
        })

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create the File where the photo should go
        var photoFile: File? = null
        try {
            photoFile = createImageFile()
        } catch (ex: IOException) {
            Log.d(TAG, ex.message.toString())
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(
                this,
                "com.apps.kunalfarmah.fileprovider",
                photoFile
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, REQUEST_TAKE_IMAGE)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        val image = File.createTempFile(
            imageFileName,  // prefix
            ".jpg",  // suffix
            storageDir // directory
        )
        mCurrentPhotoPath = "file:" + image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when (requestCode) {
            REQUEST_LOAD_IMAGE -> if (resultCode == Activity.RESULT_OK) {
                var uri = imageReturnedIntent!!.data
                img!!.setImageURI(uri)
                image = uri.toString()
            }
            REQUEST_TAKE_IMAGE -> if (resultCode == Activity.RESULT_OK) {

                img!!.setImageURI(photoURI)
                image = photoURI.toString()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                camera!!.callOnClick()
            } else {
                Toast.makeText(
                    this,
                    "Camera Permission is Required to Access the Camera",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else if (requestCode == MY_STORAGE_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                camera!!.callOnClick()
            } else {
                Toast.makeText(
                    this,
                    "Storage Permission is Required to Store the Items",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_offer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.skip) {
            finish()
            startActivity(Intent(this, ProductsListActivity::class.java))
        }
        if (item.itemId == R.id.home) {
            onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return super.onSupportNavigateUp()
    }
}