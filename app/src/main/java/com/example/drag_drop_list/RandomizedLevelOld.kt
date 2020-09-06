package com.example.drag_drop_list

//import kotlinx.android.synthetic.main.activity_main.recycler_view


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_randomized_level.*
import java.util.*
import kotlin.collections.ArrayList


//todo clean the code
class RandomizedLevelOld : AppCompatActivity() {
    /*var count = 0
    var countAll = 0
    var l = 0
    var categoryNumber = 0
    var mastery = 0
    var allItems = ArrayList<Item>()
    var exampleList = ArrayList<Item>()
    var exampleListOrdered = ArrayList<Item>()
    val act = this
    var buttonHint: Button? = null
    var buttonConf: Button? = null
    var buttonMast: Button? = null
    var buttonNext: Button? = null
    var hints = ArrayList<Int>()
    val intnt = Intent()
    var hint_limit = 0
    var mpp : MediaPlayer = MediaPlayer()
    var mpn : MediaPlayer = MediaPlayer()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_randomized_level)
        supportActionBar?.hide()

        //todo get mastery percentage for showing
        var bundle = this.intent.extras
        allItems = intent.getSerializableExtra("value") as ArrayList<Item>
        count = intent.getSerializableExtra("count") as Int
        categoryNumber = intent.getSerializableExtra("category") as Int
        mastery = intent.getSerializableExtra("mst") as Int

        intnt.putExtra("mst", mastery)
        setResult(categoryNumber, intnt)

        buttonHint = button3
        buttonConf = button6
        buttonMast = button11
        buttonNext = button12



        hint_limit = (count - 1) / 2

        allItems.shuffle()
        countAll = allItems.size


        for (x in 1..((count + 1) / 2)) {
            exampleList.add(allItems[l])
            l += 1
        }
        var b = ArrayList<Int>()
        for (x in 1..(count - ((count + 1) / 2))) {
            var a = (0 until countAll).random()
            while ((a < l && a >= (l - ((count + 1) / 2))) || a in b) {
                a = (0..(countAll - 1)).random()
            }
            Log.w("warning", "a = $a, l = $l")
            exampleList.add(allItems[a])
            b.add(a)
        }
        b.clear()

        recycler_view.adapter = RandomizedLevelAdapter(exampleList, this)
        recycler_view.layoutManager = ExLayout(this, count)
        recycler_view.setHasFixedSize(true)
        itemTouchHelper.attachToRecyclerView(recycler_view)

        mpp = MediaPlayer.create(applicationContext,R.raw.positive)
        mpn = MediaPlayer.create(applicationContext,R.raw.negative)
    }

    var simpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
            0
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {

            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            if (fromPosition in hints) {
                return false
            }
            if (toPosition in hints) {

            } else {
                Collections.swap(exampleList, fromPosition, toPosition)
                //recyclerView.adapter?.notifyItemMoved(fromPosition,toPosition)

                if (fromPosition > toPosition) {
                    recycler_view.adapter?.notifyItemMoved(toPosition, fromPosition);
                    recycler_view.adapter?.notifyItemMoved(fromPosition - 1, toPosition);
                } else if (toPosition > fromPosition) {
                    recycler_view.adapter?.notifyItemMoved(fromPosition, toPosition);
                    recycler_view.adapter?.notifyItemMoved(toPosition - 1, fromPosition);
                }
            }


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
        if (viewHolder.adapterPosition in hints) {
            return
        }
        itemTouchHelper.startDrag(viewHolder)
    }

    fun checkLevel(v: View) {
        /*var x1 = exampleList[0].text2.toInt() < exampleList[1].text2.toInt()
        var x2 = exampleList[1].text2.toInt() < exampleList[2].text2.toInt()
        var x3 = exampleList[2].text2.toInt() < exampleList[3].text2.toInt()
        var x4 = exampleList[3].text2.toInt() < exampleList[4].text2.toInt()*/
        v.postDelayed(Runnable {
            var order = true

            for (x in 0..(count - 2)) {
                if (exampleList[x].year < exampleList[x + 1].year) {
                    continue
                } else {
                    order = false
                }
            }

            if (order) {
                //todo send completed level count with the difficulty multiplier (count)
                if (mastery < countAll * 5) {
                    mastery += count
                }
                if (mastery > countAll * 5) {
                    mastery = countAll * 5
                }
                val mxd = ((mastery * 75) / (countAll * 5))

                intnt.putExtra("mst", mastery)
                setResult(categoryNumber, intnt)

                var green = resources.getDrawable(R.drawable.button_bg_green,theme)

                for (x in 0 until count) {
                    val a = recycler_view.layoutManager?.findViewByPosition(x)?.findViewWithTag<ConstraintLayout>("2")
                    //a?.background = green
                    a?.findViewWithTag<TextView>("3")?.setTextColor(getResources().getColor(R.color.colorSelectedText,theme))
                    a?.findViewWithTag<TextView>("4")?.setTextColor(getResources().getColor(R.color.colorSelectedText,theme))
                    a?.findViewWithTag<TextView>("4")?.visibility = View.VISIBLE
                    val listz = arrayOf(a?.background,green)
                    val transitionDrawable = TransitionDrawable(listz)
                    transitionDrawable.setCrossFadeEnabled(true);
                    a?.setBackground(transitionDrawable);
                    transitionDrawable.startTransition(300)


                    mpp.start()


                }

                buttonMast?.visibility = View.VISIBLE
                buttonMast?.text = "$mxd %"
                buttonNext?.visibility = View.VISIBLE
                buttonHint?.visibility = View.INVISIBLE
                buttonConf?.visibility = View.INVISIBLE


            } else {

                mpn.start()

                val mxd = ((mastery * 75) / (countAll * 5))

                intnt.putExtra("mst", mastery)
                setResult(categoryNumber, intnt)

                exampleListOrdered.clear()
                for (x in 0..(count - 1)) {
                    exampleListOrdered.add(exampleList[x])
                }
                exampleListOrdered.sortBy { it.year }

                var green = resources.getDrawable(R.drawable.button_bg_green,theme)
                var red = resources.getDrawable(R.drawable.button_bg_red,theme)

                for (x in 0..(count - 1)) {
                    if (exampleList[x] == exampleListOrdered[x]) {
                        val a = recycler_view.layoutManager?.findViewByPosition(x)?.findViewWithTag<ConstraintLayout>("2")
                        //a?.background = green
                        a?.findViewWithTag<TextView>("3")?.setTextColor(getResources().getColor(R.color.colorSelectedText,theme))
                        a?.findViewWithTag<TextView>("4")?.setTextColor(getResources().getColor(R.color.colorSelectedText,theme))
                        a?.findViewWithTag<TextView>("4")?.visibility = View.VISIBLE
                        val listz = arrayOf(a?.background,green)
                        val transitionDrawable = TransitionDrawable(listz)
                        transitionDrawable.setCrossFadeEnabled(true);
                        a?.setBackground(transitionDrawable);
                        transitionDrawable.startTransition(300)


                    } else {
                        val a = recycler_view.layoutManager?.findViewByPosition(x)?.findViewWithTag<ConstraintLayout>("2")
                        //a?.background = red
                        a?.findViewWithTag<TextView>("3")?.setTextColor(getResources().getColor(R.color.colorSelectedText,theme))
                        a?.findViewWithTag<TextView>("4")?.setTextColor(getResources().getColor(R.color.colorSelectedText,theme))
                        a?.findViewWithTag<TextView>("4")?.visibility = View.VISIBLE
                        val listz = arrayOf(a?.background,red)
                        val transitionDrawable = TransitionDrawable(listz)
                        transitionDrawable.setCrossFadeEnabled(true);
                        a?.setBackground(transitionDrawable);
                        transitionDrawable.startTransition(300)
                    }
                }

                buttonMast?.visibility = View.VISIBLE
                buttonMast?.text = "$mxd %"
                buttonNext?.visibility = View.VISIBLE
                buttonHint?.visibility = View.INVISIBLE
                buttonConf?.visibility = View.INVISIBLE

                exampleListOrdered.clear()
            }
        }, 250)


    }

    fun goNextLevel(v: View) {
        v.postDelayed(Runnable {
            var term = false
            if (l + 2 > (countAll - 1)) //checking if we have any items to show
            {
                AlertDialog.Builder(this,R.style.AlertDialogz)

                    .setTitle("Thank you!")
                    .setMessage("You have seen all of the items from this category. Do you want to shuffle and continue?") // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, which ->
                            allItems.shuffle()
                            l = 0
                            hint_limit = (count - 1) / 2
                            hints.clear()
                            goNextLevel(v)
                        }) // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("Go Back", { dialog, which ->
                        finish()
                    })
                    .setIcon(R.drawable.ic_assignment_turned_in_black_24dp)
                    .show()
                    term = true

            }
            if(!term) {
                hints.clear()
                hint_limit = (count - 1) / 2
                exampleList.clear()
                for (x in 1..((count + 1) / 2)) {
                    exampleList.add(allItems[l])
                    l += 1
                }
                var b = ArrayList<Int>()
                for (x in 1..(count - ((count + 1) / 2))) {
                    var a = (0 until countAll).random()
                    while ((a < l && a >= (l - ((count + 1) / 2))) || a in b) {
                        a = (0..(countAll - 1)).random()
                    }
                    Log.w("warning", "a = $a, l = $l")
                    exampleList.add(allItems[a])
                    b.add(a)
                }
                b.clear()

                recycler_view.adapter = RandomizedLevelAdapter(exampleList, act)
                buttonMast?.visibility = View.INVISIBLE
                buttonNext?.visibility = View.INVISIBLE

                buttonHint?.visibility = View.VISIBLE
                buttonConf?.visibility = View.VISIBLE
            }
        }, 250)
    }

    fun hint(v: View) {
        if (hint_limit == 0) {
            AlertDialog.Builder(this,R.style.AlertDialogz)
                .setTitle("Ooops!")
                .setMessage("You have used all of your hints.") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->

                    }) // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(R.drawable.ic_remove_circle_outline_black_24dp)
                .show()

            return
        }
        var rnd = 0

        exampleListOrdered.clear()
        for (x in 0..(count - 1)) {
            exampleListOrdered.add(exampleList[x])
        }
        exampleListOrdered.sortBy { it.year }


        rnd = (0 until count).random()
        while (rnd in hints) {
            rnd = (0 until count).random()
        }

        var i = 0
        while (i < count) {
            if (exampleList[rnd] == exampleListOrdered[i]) {
                break
            }
            i++
        }
        hints.add(i)

        Collections.swap(exampleList, rnd, i)

        if (rnd > i) {
            recycler_view.adapter?.notifyItemMoved(i, rnd);
            recycler_view.adapter?.notifyItemMoved(rnd - 1, i);
        } else if (i > rnd) {
            recycler_view.adapter?.notifyItemMoved(rnd, i);
            recycler_view.adapter?.notifyItemMoved(i - 1, rnd);
        }

        var green = resources.getDrawable(R.drawable.button_bg_green,theme)
        val a = recycler_view.layoutManager?.findViewByPosition(rnd)?.findViewWithTag<ConstraintLayout>("2")
        //a?.background = green
        a?.findViewWithTag<TextView>("3")?.setTextColor(getResources().getColor(R.color.colorSelectedText,theme))
        a?.findViewWithTag<TextView>("4")?.setTextColor(getResources().getColor(R.color.colorSelectedText,theme))
        a?.findViewWithTag<TextView>("4")?.visibility = View.VISIBLE


        val listz = arrayOf(a?.background,green)
        val transitionDrawable = TransitionDrawable(listz)
        transitionDrawable.setCrossFadeEnabled(true);
        a?.setBackground(transitionDrawable);
        transitionDrawable.startTransition(400)



        i = 0
        hint_limit--
    }

*/
    }


