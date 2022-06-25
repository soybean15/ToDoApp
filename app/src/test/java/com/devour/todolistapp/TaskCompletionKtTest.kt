package com.devour.todolistapp

import org.junit.Assert.*

import org.junit.Test

class TaskCompletionKtTest {

    @Test
    fun findCompletionTest() {
        val items = mutableListOf<Item>(Item("Bread", true),
        Item("Milk", false))

        val group = Group("TestGroup", items)

        val result = findCompletion(group)

        assertEquals(result.activeRate, 50f)
        assertEquals(result.completeRate, 50f)

    }
}