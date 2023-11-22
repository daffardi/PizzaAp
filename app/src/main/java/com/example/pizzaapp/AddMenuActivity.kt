package com.example.pizzaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class AddMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)
        //hide title
        getSupportActionBar()?.hide()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?
    ): View? {
    //inflate the layout
        val view = inflater.inflate(R.layout.fragment_makanan, container,false)
        val rvmakanan: RecyclerView = view.findViewById(R.id.recyclerMakanan)
        rvmakanan.layoutManager = LinearLayoutManager(activity)
        rvmakanan.adapter = MenuAdapter()

        //instance button add menu
        val buttonAdd : Button = view.findViewById(R.id.buttonAddMenu)
        //event saat buttton tsb diklik
        buttonAdd.setOnClickListener( requireActivity().run{
            startActivity().run{
                startActivity(Intent(this,AddMenuActivity::class.java))
                finish()
            }
        }
        )
    }

}