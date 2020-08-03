package com.example.drag_drop_list

import android.content.Context
import android.util.LayoutDirection
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class ExLayout(private val context: Context,private val count:Int) : LinearLayoutManager(context) {
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

        return (pageSize.toFloat() / count).roundToInt()
    }

}