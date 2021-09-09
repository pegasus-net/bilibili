package com.icarus.bilibili

abstract class Loader<T> {
    protected abstract fun load(offset: String?): List<T>
    var hasMore = true
    protected var offset: String? = null

    fun reload(): List<T> {
        hasMore = true
        offset = null
        return load()
    }

    fun load(): List<T> {
        return if (hasMore) {
            load(offset).filter(filter)
        } else {
            TODO("没有更多的数据可以加载了")
        }
    }

    var filter: ((T) -> Boolean) = { true }
}