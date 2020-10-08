package com.apps.kunalfarmah.furnituremagic.Activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apps.kunalfarmah.furnituremagic.Adapters.FurnitureAdapter
import com.apps.kunalfarmah.furnituremagic.R
import com.apps.kunalfarmah.furnituremagic.Room.Furniture
import com.apps.kunalfarmah.furnituremagic.ViewModels.FurnitureViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class ProductsListActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    var recycler: RecyclerView? = null
    var mAdpter: FurnitureAdapter? = null
    var furnitureViewModel: FurnitureViewModel? = null
    var list: List<Furniture>? = null
    var add: FloatingActionButton? = null
    var empty: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_list)

        supportActionBar!!.setTitle(getString(R.string.all_offers))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        auth = FirebaseAuth.getInstance()
        recycler = findViewById(R.id.furniture_recycler)
        add = findViewById(R.id.add)
        empty = findViewById(R.id.no_items)

        recycler!!.setHasFixedSize(true)
        recycler!!.itemAnimator = DefaultItemAnimator()
        recycler!!.layoutManager = LinearLayoutManager(this)
        list = ArrayList<Furniture>()
        furnitureViewModel = FurnitureViewModel(application)

        furnitureViewModel!!.furnitures!!.observe(this, Observer<List<Furniture?>?> { items ->

            if (items.isNullOrEmpty()) {
                recycler!!.visibility = GONE
                empty!!.visibility = VISIBLE
            } else {
                recycler!!.visibility = VISIBLE
                empty!!.visibility = GONE
            }
            mAdpter = FurnitureAdapter(this@ProductsListActivity, items, furnitureViewModel)
            recycler!!.adapter = mAdpter
        })


        add!!.setOnClickListener {
            startActivity(Intent(this@ProductsListActivity, ProductActivity::class.java))
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.signOut) {
            auth!!.signOut()
            finishAffinity()
            startActivity(Intent(this@ProductsListActivity, MainActivity::class.java))
            return true
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