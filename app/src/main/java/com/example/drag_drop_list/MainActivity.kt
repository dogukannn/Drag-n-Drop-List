package com.example.drag_drop_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

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
        val i=1
        val jsonFileString = getJsonDataFromAsset(applicationContext, "Kitap$i.json")

        /*val jObject = JSONArray(jsonFileString)

        Log.i("data",ar1.toString())*/

        Log.w("data", jsonFileString)

        val gson = Gson()
        val listPersonType = object : TypeToken<List<Item>>() {}.type

        var items: List<Item> = gson.fromJson(jsonFileString, listPersonType)
        items.forEachIndexed { idx, item -> Log.i("data", "> Item $idx:\n$item") }



        }



    fun toActiv2(view: View){
        val intent = Intent(this,ExampleLevel::class.java)

        var bundle = Bundle();
        bundle.putSerializable("value", exampleListTrue as Serializable);
        intent.putExtras(bundle);

        startActivity(intent)

    }




}
