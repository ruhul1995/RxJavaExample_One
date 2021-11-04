package com.example.rxjavaexample_one

class DataSource {

    companion object{

        fun createTasksList(): List<Task> {
            val task: MutableList<Task> = ArrayList()
            task.add(Task("Read a Book", true, 3))
            task.add(Task("Watch TV", false, 2))
            task.add(Task("Play computer games", true, 1))
            task.add(Task("Speak on phone", false, 0))
            task.add(Task("Dance a little", true, 4))
            return task
        }
    }
}