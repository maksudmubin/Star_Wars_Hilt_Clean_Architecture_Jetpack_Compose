package com.mubin.starwars.base.utils

import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLazyLoading private constructor() {
    private var isScrolling = false
    private var currentItems = 0
    private var totalItems: Int = 0
    private var scrollOutItems: Int = 0

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: SpeedyLinearLayoutManager

    private var loadMoreListener: Listener? = null

    companion object {
        fun getInstance(): RecyclerViewLazyLoading {
            return RecyclerViewLazyLoading()
        }
    }

    fun registerScrollListener(recyclerView: RecyclerView, listener: Listener) {
        this.recyclerView = recyclerView
        if (recyclerView.layoutManager == null) {
            throw RuntimeException("RecyclerViewLazyLoading: Layout manager can not be null")
        }
        if (recyclerView.layoutManager!! is SpeedyLinearLayoutManager) {
            this.layoutManager = recyclerView.layoutManager!! as SpeedyLinearLayoutManager
            this.loadMoreListener = listener
            recyclerView.addOnScrollListener(rvScrollListener)
        } else {
            throw RuntimeException("RecyclerViewLazyLoading: SpeedyLinearLayoutManager must be attached to the RecyclerView")
        }
    }

    fun removeListener() {
        executeBodyOrReturnNull {
            recyclerView.removeOnScrollListener(rvScrollListener)
            this.loadMoreListener = null
        } ?: kotlin.run { this.loadMoreListener = null }
    }

    private val rvScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            handlerPostDelayed(500) {
                executeBodyOrReturnNull {
                    currentItems = layoutManager.childCount
                    totalItems = layoutManager.itemCount
                    scrollOutItems = layoutManager.findFirstVisibleItemPosition()
                    if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                        isScrolling = false
                        loadMoreListener?.loadMore()
                    }
                }
            }
        }
    }

    interface Listener {
        fun loadMore()
    }
}

fun RecyclerView.attachSpeedyLayoutManager(orientation: Int, reverseLayout: Boolean) {
    executeBodyOrReturnNull {
        this.layoutManager = SpeedyLinearLayoutManager(this.context, orientation, reverseLayout)
    }
}