package com.example.drag_drop_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.marcinmoskala.math.combinations
import java.io.Serializable

class Categories : AppCompatActivity() {

    var list0 = ArrayList<ExampleItem>()
    var categoryItems = ArrayList<ExampleItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        supportActionBar?.hide()

        //todo mastery system for categories


        //(1..200).toSet().combinations(3) :: TOO HEAVY TO USE
        list0.add(ExampleItem(R.drawable.ic_android, "Nato kuruldu.", "1949"))
        list0.add(ExampleItem(R.drawable.ic_android, "Varşova Paktı kuruldu.", "1955"))
        list0.add(ExampleItem(R.drawable.ic_android, "Türkiye Kıbrıs'a asker çıkarttı.", "1974"))
        list0.add(ExampleItem(R.drawable.ic_android, "Berlin Duvarının yıkıldı.", "1989"))
        list0.add(ExampleItem(R.drawable.ic_android, "Sovyetler Birliği yıkıldı.", "1991"))
        list0.add(ExampleItem(R.drawable.ic_android, "Nato kuruldu+1.", "1950"))
        list0.add(ExampleItem(R.drawable.ic_android, "Varşova Paktı kuruldu+1.", "1956"))
        list0.add(ExampleItem(R.drawable.ic_android, "Türkiye Kıbrıs'a asker çıkarttı+1.", "1975"))
        list0.add(ExampleItem(R.drawable.ic_android, "Berlin Duvarının yıkıldı+1.", "1990"))
        list0.add(ExampleItem(R.drawable.ic_android, "Sovyetler Birliği yıkıldı+1.", "1992"))





    }

    fun toLevel(v:View) {
        //todo send datas to randomized levels
        when(v.tag){
            0 -> {
                categoryItems = list0
            }
            1 -> {
                categoryItems = list0
            }
        }

        val intent = Intent(this,RandomizedLevel::class.java)

        var bundle = Bundle();
        bundle.putSerializable("value", list0 as Serializable);
        intent.putExtras(bundle);

        startActivity(intent)
    }

    fun operateLevels() {

    }



}
