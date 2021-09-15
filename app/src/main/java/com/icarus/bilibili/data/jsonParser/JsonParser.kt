package com.icarus.bilibili.data.jsonParser

interface JsonParser<T> {
    fun getParserResult():T
}