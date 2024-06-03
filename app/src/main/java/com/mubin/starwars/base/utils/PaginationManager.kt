package com.mubin.starwars.base.utils

import com.mubin.starwars.data.model.PaginationInfoModel

/**
 * Pagination Manager for Ami Probashi.
 * All APIs regarding Pagination must be declared here.
 */
object PaginationManager {
    const val PAGINATION_DEFAULT_LIMIT = 10
    const val PAGINATION_DEFAULT_PAGE = 1

    /**
     * To identify that if the APIs has more pages to load or not by using new mode [PaginationInfoModel]
     *
     * @param paginationInfo Nullable [PaginationInfoModel].
     *
     * @return [Boolean].
     */
    fun hasMoreItems(paginationInfo: PaginationInfoModel?): Boolean {
        var hasNextPage = false
        if (paginationInfo == null) {
            return hasNextPage
        }
        try {
            if (paginationInfo.currentPage!! < paginationInfo.lastPage!!) {
                hasNextPage = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            logThis(e)
        }
        return hasNextPage
    }

    /**
     * To identify that if the APIs has more pages to load or not.
     *
     * @param paginationInfo Nullable [PaginationInfoModel].
     *
     * @return [Boolean].
     */
   /* fun hasMoreItems(paginationInfo: PaginationInfoModel?): Boolean {
        var hasNextPage = false
        try {
            paginationInfo?.let {
                if (it.page.isDigitOnly() && it.totalPages.isDigitOnly()) {
                    if (it.page.toInt() < it.totalPages.toInt()) {
                        hasNextPage = true
                    }
                }
            }
        } catch (e: Exception) {
            logThis(e)
        }
        return hasNextPage
    }*/

    private fun String.isDigitOnly(): Boolean = this.matches("[0-9]+".toRegex())
}