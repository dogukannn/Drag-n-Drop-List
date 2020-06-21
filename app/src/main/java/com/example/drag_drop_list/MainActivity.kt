package com.example.drag_drop_list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    var exampleListTrue = ArrayList<ExampleItem>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        //trying
        exampleListTrue.add(ExampleItem(R.drawable.ic_android, "Nato kuruldu.", "1949"))
        exampleListTrue.add(ExampleItem(R.drawable.ic_android, "Varşova Paktı kuruldu.", "1955"))
        exampleListTrue.add(ExampleItem(R.drawable.ic_android, "Türkiye Kıbrıs'a asker çıkarttı.", "1974"))
        exampleListTrue.add(ExampleItem(R.drawable.ic_android, "Berlin Duvarının yıkıldı.", "1989"))
        exampleListTrue.add(ExampleItem(R.drawable.ic_android, "Sovyetler Birliği yıkıldı.", "1991"))

        }



    fun toActiv2(view:android.view.View){
        val intent = Intent(this,ExampleLevel::class.java)

        var bundle = Bundle();
        bundle.putSerializable("value", exampleListTrue as Serializable);
        intent.putExtras(bundle);

        startActivity(intent)

    }




}
