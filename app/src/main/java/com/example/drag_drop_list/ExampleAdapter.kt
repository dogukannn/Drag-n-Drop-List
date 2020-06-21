package com.example.drag_drop_list

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.example_item.view.*

class ExampleAdapter(private val exampleList : List<ExampleItem>, private  var activity: ExampleLevel) : RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>() {

    var mRecyclerView: RecyclerView? = null


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        mRecyclerView = recyclerView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.example_item,
        parent, false)


        val viewHolder = ExampleViewHolder(itemView)
        viewHolder.itemView.setOnTouchListener {
                view, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                // 2. When we detect touch-down event, we call the
                //    startDragging(...) method we prepared above


                activity.startDragging(viewHolder)
            }
            return@setOnTouchListener true
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2

        //val containerHeight: Int? = mRecyclerView?.height

        //holder.itemView.minimumHeight = containerHeight!!.div(5)
    }

    override fun getItemCount() = exampleList.size

    class ExampleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageView : ImageView = itemView.image_view
        val textView1 : TextView = itemView.text_view1
        val textView2 : TextView = itemView.text_view2
    }
}