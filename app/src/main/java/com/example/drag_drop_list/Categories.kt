package com.example.drag_drop_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_categories.*
import java.io.Serializable

//todo clean the code
class Categories : AppCompatActivity() {

    var list0 = ArrayList<Item>()
    var categoryItems = ArrayList<Item>()
    var mst0 = 0
    var masteries = Array<Int>(10,{i -> 0})



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        supportActionBar?.hide()


        //todo mastery system for categories
        //----------------------------------
        //can it savable with a array ?
        //or will pairs be enough? (can check individuals with playing with strings)


        val settings = applicationContext.getSharedPreferences("MASTERY", 0)
        masteries[0] = settings.getInt("mst0", 0)

        val jsonFileString = getJsonDataFromAsset(applicationContext, "19th.json")

        /*val jObject = JSONArray(jsonFileString)

        Log.i("data",ar1.toString())*/
        //Log.i("comb",(1..100).toSet().combinations(5).toString())
        Log.w("data", jsonFileString)

        val gson = Gson()
        val listPersonType = object : TypeToken<ArrayList<Item>>() {}.type

        list0 = gson.fromJson(jsonFileString, listPersonType)
        list0.forEachIndexed { idx, item -> Log.i("data", "> Item $idx:\n$item") }

        //(1..200).toSet().combinations(3) :: TOO HEAVY TO USE





    }

    fun toLevel(v:View) {
        var mst = 0
        var cat = 0
        Log.w("next","whops")
        when(v.tag.toString().toInt()){
            0 -> {
                categoryItems = list0
                mst = masteries[0]
                cat = 0
                Log.w("next","whops0")
            }
            1 -> {
                categoryItems = list0
                cat = 1
            }
        }
        var size = 5
        if(checkBox2.isChecked)
        {
            size = 7
        }

        val intent = Intent(this,RandomizedLevel::class.java)

        var bundle = Bundle();
        bundle.putSerializable("count",size);
        bundle.putSerializable("value", list0 as Serializable);
        bundle.putSerializable("category",cat)
        bundle.putSerializable("mst",mst)
        intent.putExtras(bundle);

        startActivityForResult(intent, 0);
    }

    fun operateLevels() {

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //todo get mastery data from levels
        if (requestCode == 0) {
            when(resultCode){
                0 -> {
                    masteries[0] = data?.getIntExtra("mst", masteries[0]) as Int
                    Log.w("DATATRANSFER","masteries0 ="+ masteries[0])
                }
                1 -> {

                }
            }
            //mst0 = data?.getIntExtra("mst0", mst0) as Int
            Log.w("intnt","int = $mst0")
        }

        //todo save the mastery data which comes from levels
        //or save it when we close the app
        val mastery = applicationContext.getSharedPreferences("MASTERY", 0)
        val editor = mastery.edit()
        editor.putInt("mst0",masteries[0])
        editor.apply()



        super.onActivityResult(requestCode, resultCode, data)
    }

    fun check1(v: View) {
      if(checkBox2.isChecked){
          checkBox2.toggle()
      }else{
          checkBox1.isChecked = true
      }
    }
    fun check2(v: View) {
        if(checkBox1.isChecked){
            checkBox1.toggle()
        }else{
            checkBox2.isChecked = true
        }
    }



}
