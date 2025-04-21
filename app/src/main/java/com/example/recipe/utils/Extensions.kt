package com.example.recipe.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.Observer

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun RecyclerView.setupRecyclerView(myLayout: RecyclerView.LayoutManager, myAdapter: RecyclerView.Adapter<*>) {
    this.apply {
        layoutManager = myLayout
        setHasFixedSize(true)
        adapter = myAdapter
    }
}

fun TextView.setDynamicallyColor(color: Int) {
    //Start - Left = 0 || Top = 1 || End - Right = 2 || Bottom = 3
    this.compoundDrawables[1].setTint(ContextCompat.getColor(context, color))
    this.setTextColor(ContextCompat.getColor(context, color))
}

fun Int.minToHour(): String {
    val time: String
    val hours: Int = this / 60
    val minutes: Int = this % 60
    time = if (hours > 0) "${hours}h:${minutes}min" else "${minutes}min"
    return time
}
fun <T> LiveData<T>.onceObserve(owner: LifecycleOwner, observe: Observer<T>) {
    observe(owner, object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observe.onChanged(t)
        }
    })
}

fun View.isVisible(isShownLoading: Boolean, container: View) {
    if (isShownLoading) {
        this.isVisible = true
        container.isVisible = false
    } else {
        this.isVisible = false
        container.isVisible = true
    }
}