package com.example.drag_drop_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

//todo clean the code
class Categories : AppCompatActivity() {

    var list0 = ArrayList<ExampleItem>()
    var categoryItems = ArrayList<ExampleItem>()
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
        val rGroup = findViewById<RadioGroup>(R.id.rgrup)

        val checkedRadioButton = findViewById<RadioButton>(rGroup.checkedRadioButtonId)
        val size = checkedRadioButton.tag.toString().toInt()

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




}
