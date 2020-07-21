package com.example.drag_drop_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class RandomizedLevel : AppCompatActivity() {
    var count = 0
    var l = 0
    var allItems = ArrayList<ExampleItem>()
    var exampleList = ArrayList<ExampleItem>()
    var exampleListOrdered = ArrayList<ExampleItem>()
    val act = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_randomized_level)
        supportActionBar?.hide()
        //gpg sign try5
        var bundle = this.intent.extras
        allItems= intent.getSerializableExtra("value") as ArrayList<ExampleItem>

        /*exampleListOrdered.addAll(exampleList)
        while (exampleListOrdered == exampleList) {
            exampleList.shuffle()
        }*/
        allItems.shuffle()
        count = allItems.size

        exampleList.add(allItems[l])
        exampleList.add(allItems[l+1])
        exampleList.add(allItems[l+2])
        var a = (0..(count-1)).random()
        while((a == l) || (a == (l+1)) || (a == (l+2))){
            a = (0..(count-1)).random()
        }
        var b = (0..(count-1)).random()
        while((b == l) || (b == (l+1)) || (b == (l+2)) || (b == a)){
            b = (0..(count-1)).random()
        }
        exampleList.add(allItems[a])
        exampleList.add(allItems[b])
        l = 3


        recycler_view.adapter = RandomizedLevelAdapter(exampleList,this)
        recycler_view.layoutManager = ExLayout(this)
        recycler_view.setHasFixedSize(true)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }

    var simpleCallback = object :
        ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,0  ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {

            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition

            Collections.swap(exampleList,fromPosition,toPosition)
            recyclerView.adapter?.notifyItemMoved(fromPosition,toPosition)

            return false
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            TODO("Not yet implemented")
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            //Log.w("drag is ended","ups")


        }
    }
    val itemTouchHelper = ItemTouchHelper(simpleCallback)

    fun startDragging(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }
    fun checkLevel(v: View){
        var x1 = exampleList[0].text2.toInt() < exampleList[1].text2.toInt()
        var x2 = exampleList[1].text2.toInt() < exampleList[2].text2.toInt()
        var x3 = exampleList[2].text2.toInt() < exampleList[3].text2.toInt()
        var x4 = exampleList[3].text2.toInt() < exampleList[4].text2.toInt()
        if(x1 && x2 && x3 && x4)
        {

            exampleList.clear()
            exampleList.add(allItems[l])
            exampleList.add(allItems[l+1])
            exampleList.add(allItems[l+2])
            var a = (0..(count-1)).random()
            while((a == l) || (a == (l+1)) || (a == (l+2))){
                a = (0..(count-1)).random()
            }
            var b = (0..(count-1)).random()
            while((b == l) || (b == (l+1)) || (b == (l+2)) || (b == a)){
                b = (0..(count-1)).random()
            }
            exampleList.add(allItems[a])
            exampleList.add(allItems[b])
            l += 3

            recycler_view.adapter = RandomizedLevelAdapter(exampleList,act)

            /*val intent = Intent(recyclerView.context,Activity2::class.java)
            startActivity(intent)
            finish()*/
        }

    }
}
