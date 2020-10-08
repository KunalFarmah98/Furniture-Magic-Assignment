package com.apps.kunalfarmah.furnituremagic

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.apps.kunalfarmah.furnituremagic.Room.Furniture
import com.apps.kunalfarmah.furnituremagic.ViewModels.FurnitureViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


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

    val MY_CAMERA_PERMISSION_CODE = 101
    val REQUEST_LOAD_IMAGE = 1001
    val REQUEST_TAKE_IMAGE = 1002
    val MY_STORAGE_PERMISSION_CODE = 102


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

        val options = resources.getStringArray(R.array.furniture)
        // access the spinner
        val spinner = findViewById<Spinner>(R.id.type)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, options
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
                if (price.equals("")) {
                    Toast.makeText(
                        this@ProductActivity,
                        "Please Provide a Price",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
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
                if (image == null) {
                    Toast.makeText(this@ProductActivity, "Please Add an Image", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                var furniture = Furniture(name, type, price, discPrice, image)

                furnitureViewModel!!.insertItem(furniture)
            }
        })


        img!!.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onClick(p0: View?) {
                val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
                val dialog = BottomSheetDialog(this@ProductActivity)
                dialog.setContentView(dialogView)
                dialog.show()
                val camera =
                    dialog.findViewById<CardView>(R.id.camera)
                camera!!.setOnClickListener {

                    dialog.cancel()
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(
                            arrayOf(Manifest.permission.CAMERA),
                            MY_CAMERA_PERMISSION_CODE
                        )
                    } else {
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(
                            takePicture,
                            REQUEST_TAKE_IMAGE
                        )
                    }
                }

                val gallery =
                    dialog.findViewById<CardView>(R.id.gallery)
                gallery!!.setOnClickListener {
                    dialog.cancel()
                    val gallery = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(gallery, REQUEST_LOAD_IMAGE)
                }
                dialog.show()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when (requestCode) {
            1001 -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage: Uri? = imageReturnedIntent!!.data
                img!!.setImageURI(selectedImage)
                image = selectedImage.toString()
            }
            1002 -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage: Uri? = imageReturnedIntent!!.data
                img!!.setImageURI(selectedImage)
                image = selectedImage.toString()
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
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(
                    takePicture,
                    REQUEST_TAKE_IMAGE
                )
            } else {
                Toast.makeText(
                    this,
                    "Camera Permission is Required to Access the Camera",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else if (requestCode == MY_STORAGE_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                save!!.callOnClick()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.home) {
            finish()
            super.onBackPressed()
        }

        return true
    }
}