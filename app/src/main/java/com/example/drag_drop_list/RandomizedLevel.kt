package com.example.drag_drop_list

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recycler_view
import kotlinx.android.synthetic.main.activity_randomized_level.*
import kotlinx.android.synthetic.main.example_item.*
import java.util.*
import kotlin.collections.ArrayList

class RandomizedLevel : AppCompatActivity() {
    var count = 0
    var l = 0
    var allItems = ArrayList<ExampleItem>()
    var exampleList = ArrayList<ExampleItem>()
    var exampleListOrdered = ArrayList<ExampleItem>()
    val act = this
    var buttonHint : Button? = null
    var buttonConf : Button? = null
    var buttonMast : Button? = null
    var buttonNext : Button? = null


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
        buttonHint = button3
        buttonConf = button6
        buttonMast = button11
        buttonNext = button12


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
            TODO("If I wanted to swipe...")
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

            for(x in 0..4) {
                recycler_view.layoutManager?.findViewByPosition(x)?.setBackgroundColor(Color.rgb(119,235,30))
            }
            buttonMast?.visibility = View.VISIBLE
            buttonNext?.visibility = View.VISIBLE
            buttonHint?.visibility = View.INVISIBLE
            buttonConf?.visibility = View.INVISIBLE

        }
        else {
            exampleListOrdered.add(exampleList[0])
            exampleListOrdered.add(exampleList[1])
            exampleListOrdered.add(exampleList[2])
            exampleListOrdered.add(exampleList[3])
            exampleListOrdered.add(exampleList[4])
            exampleListOrdered.sortBy { it.text2 }

            for(x in 0..4) {
                if(exampleList[x] == exampleListOrdered[x]){
                    recycler_view.layoutManager?.findViewByPosition(x)?.setBackgroundColor(Color.rgb(119,235,30))

                }
                else {
                    recycler_view.layoutManager?.findViewByPosition(x)?.setBackgroundColor(Color.rgb(240,98,22))
                }
            }

            buttonMast?.visibility = View.VISIBLE
            buttonNext?.visibility = View.VISIBLE
            buttonHint?.visibility = View.INVISIBLE
            buttonConf?.visibility = View.INVISIBLE

            exampleListOrdered.clear()
        }

    }

    fun goNextLevel(v: View) {

        if(l + 2 > (count-1)) //checking if we have any items to show
        {
            return
        }

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
        buttonMast?.visibility = View.INVISIBLE
        buttonNext?.visibility = View.INVISIBLE

        buttonHint?.visibility = View.VISIBLE
        buttonConf?.visibility = View.VISIBLE
    }


}
