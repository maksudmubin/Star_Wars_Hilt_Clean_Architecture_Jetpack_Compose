package com.mubin.starwars.base.utils

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

interface SwipeToRefreshListener {
    fun onSwipe(view : SwipeRefreshLayout)
}