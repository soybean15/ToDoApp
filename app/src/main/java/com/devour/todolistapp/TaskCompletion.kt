package com.devour.todolistapp

internal fun findCompletion(group:GroupWithItems):CompletionRate{

    val totalItems = group.items.size
    val activeItems = group.items.count { !it.completed }
    val completedItems = group.items.count { it.completed }

    val activeRate = (100* activeItems/totalItems).toFloat()
    val completeRate = (100 * completedItems/totalItems).toFloat()

    return CompletionRate(activeRate,completeRate)

}

data class CompletionRate (val activeRate:Float, val completeRate:Float)