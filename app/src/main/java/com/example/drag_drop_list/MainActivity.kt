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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    val exampleList = generateDummyList(5)
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

        Collections.shuffle(exampleList)

        //var tmp = exampleListTrue[0]
        //exampleListTrue[0] = exampleListTrue[3]
        //exampleListTrue[3] = tmp


        recycler_view.adapter = ExampleAdapter(exampleList,this)
        recycler_view.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }

            override fun getLayoutDirection(): Int {
                return LayoutDirection.LTR
            }

            override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                //return true
                 return  super.checkLayoutParams(lp) && lp!!.height == (getItemSize()/10)*9
            }

            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return setProperItemSize(super.generateDefaultLayoutParams())
            }

            override fun generateLayoutParams(lp: ViewGroup.LayoutParams): RecyclerView.LayoutParams {
                return setProperItemSize(super.generateLayoutParams(lp))
            }

            private fun setProperItemSize(lp: RecyclerView.LayoutParams): RecyclerView.LayoutParams {
                val itemSize = getItemSize()
                lp.height = (itemSize/10) * 9
                lp.bottomMargin = itemSize / 10
                return lp
            }

            private fun getItemSize(): Int {
                val pageSize = height

                return (pageSize.toFloat() / 5).roundToInt()
            }
        }

        recycler_view.setHasFixedSize(true)
        itemTouchHelper.attachToRecyclerView(recycler_view)






    }
    var simpleCallback = object :ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,0  ) {
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
            if(exampleList == exampleListTrue)
            {
                val intent = Intent(recyclerView.context,Activity2::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    val itemTouchHelper = ItemTouchHelper(simpleCallback)

    fun startDragging(viewHolder: RecyclerView.ViewHolder) {
        print("omg")
        itemTouchHelper.startDrag(viewHolder)
    }

    private  fun generateDummyList(size: Int): List<ExampleItem> {
        val list = ArrayList<ExampleItem>()

        //val item = ExampleItem(R.drawable.ic_android, "Berlin Duvarının yıkılışı.", "1989")
        list += ExampleItem(R.drawable.ic_android, "Berlin Duvarının yıkıldı.", "1989")
        list += ExampleItem(R.drawable.ic_android, "Türkiye Kıbrıs'a asker çıkarttı.", "1974")
        list += ExampleItem(R.drawable.ic_android, "Nato kuruldu.", "1949")
        list += ExampleItem(R.drawable.ic_android, "Sovyetler Birliği yıkıldı.", "1991")
        list += ExampleItem(R.drawable.ic_android, "Varşova Paktı kuruldu.", "1955")

        return list
    }

    fun toActiv2(view:android.view.View){
        val intent = Intent(this,Activity2::class.java)
        startActivity(intent)

    }




}
