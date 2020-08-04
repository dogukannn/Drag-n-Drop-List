package com.example.drag_drop_list

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.recycler_view
import kotlinx.android.synthetic.main.activity_randomized_level.*
import java.util.*
import kotlin.collections.ArrayList


//todo clean the code
class RandomizedLevel : AppCompatActivity() {
    var count = 0
    var countAll = 0
    var l = 0
    var categoryNumber = 0
    var mastery = 0
    var allItems = ArrayList<ExampleItem>()
    var exampleList = ArrayList<ExampleItem>()
    var exampleListOrdered = ArrayList<ExampleItem>()
    val act = this
    var buttonHint : Button? = null
    var buttonConf : Button? = null
    var buttonMast : Button? = null
    var buttonNext : Button? = null

    val intnt = Intent()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_randomized_level)
        supportActionBar?.hide()

        //todo get mastery percentage for showing
        var bundle = this.intent.extras
        allItems= intent.getSerializableExtra("value") as ArrayList<ExampleItem>
        count = intent.getSerializableExtra("count") as Int
        categoryNumber = intent.getSerializableExtra("category") as Int
        mastery = intent.getSerializableExtra("mst") as Int

        intnt.putExtra("mst",mastery)
        setResult(categoryNumber,intnt)

        buttonHint = button3
        buttonConf = button6
        buttonMast = button11
        buttonNext = button12


        allItems.shuffle()
        countAll = allItems.size


        for (x in 1..((count+1)/2) )
        {
            exampleList.add(allItems[l])
            l += 1
        }
        var b = ArrayList<Int>()
        for (x in 1..(count - ((count+1)/2)))
        {
            var a = (0 until countAll).random()
            while((a < l  && a >= (l - ((count+1)/2))) || a in b){
                a = (0..(countAll-1)).random()
            }
            Log.w("warning","a = $a, l = $l")
            exampleList.add(allItems[a])
            b.add(a)
        }
        b.clear()

        recycler_view.adapter = RandomizedLevelAdapter(exampleList,this)
        recycler_view.layoutManager = ExLayout(this,count)
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
        /*var x1 = exampleList[0].text2.toInt() < exampleList[1].text2.toInt()
        var x2 = exampleList[1].text2.toInt() < exampleList[2].text2.toInt()
        var x3 = exampleList[2].text2.toInt() < exampleList[3].text2.toInt()
        var x4 = exampleList[3].text2.toInt() < exampleList[4].text2.toInt()*/

        var order = true

        for(x in 0..(count-2))
        {
            if (exampleList[x].text2.toInt() < exampleList[x+1].text2.toInt())
            {
                continue
            }
            else
            {
                order = false
            }
        }

        if(order)
        {
            //todo send completed level count with the difficulty multiplier (count)
            if(mastery < countAll *5){
                mastery += count
            }
            val mxd = ((mastery *75)/ (countAll*5))

            intnt.putExtra("mst",mastery)
            setResult(categoryNumber,intnt)

            for(x in 0 until count) {
                recycler_view.layoutManager?.findViewByPosition(x)?.setBackgroundColor(Color.rgb(119,235,30))
            }
            buttonMast?.visibility = View.VISIBLE
            buttonMast?.text = "$mxd %"
            buttonNext?.visibility = View.VISIBLE
            buttonHint?.visibility = View.INVISIBLE
            buttonConf?.visibility = View.INVISIBLE

        }
        else {
            val mxd = ((mastery *75)/ (countAll*5))

            intnt.putExtra("mst",mastery)
            setResult(categoryNumber,intnt)

            for(x in 0..(count-1)){
                exampleListOrdered.add(exampleList[x])
            }
            exampleListOrdered.sortBy { it.text2 }

            for(x in 0..(count-1)) {
                if(exampleList[x] == exampleListOrdered[x]){
                    recycler_view.layoutManager?.findViewByPosition(x)?.setBackgroundColor(Color.rgb(119,235,30))

                }
                else {
                    recycler_view.layoutManager?.findViewByPosition(x)?.setBackgroundColor(Color.rgb(240,98,22))
                }
            }

            buttonMast?.visibility = View.VISIBLE
            buttonMast?.text = "$mxd %"
            buttonNext?.visibility = View.VISIBLE
            buttonHint?.visibility = View.INVISIBLE
            buttonConf?.visibility = View.INVISIBLE

            exampleListOrdered.clear()
        }

    }

    fun goNextLevel(v: View) {

        if(l + 2 > (countAll-1)) //checking if we have any items to show
        {
            AlertDialog.Builder(this)
                .setTitle("Thank you!")
                .setMessage("You have seen all of the items from this category. Do you want to shuffle and continue?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        allItems.shuffle()
                        l = 0
                        goNextLevel(v)
                    }) // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Go Back", {dialog, which ->
                        finish()
                    })
                .setIcon(R.drawable.ic_assignment_turned_in_black_24dp)
                .show()

            return
        }

        exampleList.clear()
        for (x in 1..((count+1)/2) )
        {
            exampleList.add(allItems[l])
            l += 1
        }
        var b = ArrayList<Int>()
        for (x in 1..(count - ((count+1)/2)))
        {
            var a = (0 until countAll).random()
            while((a < l  && a >= (l - ((count+1)/2))) || a in b){
                a = (0..(countAll-1)).random()
            }
            Log.w("warning","a = $a, l = $l")
            exampleList.add(allItems[a])
            b.add(a)
        }
        b.clear()
        /*exampleList.add(allItems[l])
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
        l += 3*/

        recycler_view.adapter = RandomizedLevelAdapter(exampleList,act)
        buttonMast?.visibility = View.INVISIBLE
        buttonNext?.visibility = View.INVISIBLE

        buttonHint?.visibility = View.VISIBLE
        buttonConf?.visibility = View.VISIBLE
    }


}
