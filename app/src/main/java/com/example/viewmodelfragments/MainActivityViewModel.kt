package com.example.viewmodelfragments

import androidx.lifecycle.*

class MainActivityViewModel() : ViewModel() {

    private val terms_by_order =
        listOf("F20", "W21", "S21",
            "F21", "W22", "S22",
            "F22", "W23", "S23",
            "F23", "W24", "S24",
            "F24", "W25")


    var courses : MutableLiveData<MutableList<MutableList<String>>>
    = MutableLiveData<MutableList<MutableList<String>>>()

    var displayedCourses : MutableLiveData<MutableList<MutableList<String>>>
    = MutableLiveData<MutableList<MutableList<String>>>()

    fun courseExists(code: String) : Boolean {
        val oldVal = courses.value ?: mutableListOf()
        var temp = false
        oldVal.forEach {
            if (it[0] == code) {
                temp = true
            }
        }
        return temp
    }

    fun editCourse(origList: MutableList<String>, newList: MutableList<String>) {
        val oldValue = courses.value ?: mutableListOf()
        oldValue.forEach {
            if (it == origList) {
                it[1] = newList[1]
                it[2] = newList[2]
                it[3] = newList[3]
            }
        }
        courses.value = oldValue
        displayedCourses.value = oldValue
    }

    fun addCourse(course: MutableList<String>) {
        val oldValue = courses.value ?: mutableListOf()
        oldValue.add(course)
        courses.value = oldValue
        displayedCourses.value = oldValue
    }

    fun delCourse(course: MutableList<String>) {
        val oldValue = courses.value ?: mutableListOf()
        oldValue.remove(course)
        courses.value = oldValue
        displayedCourses.value = oldValue
    }

    fun sortCourses(mode: Int) {
        /**
         * 0: A -> Z
         * 1: Old -> New
         * 2: High -> Low
         */
        val oldVal = courses.value ?: mutableListOf()
        when (mode) {
            0 -> {
                oldVal.sortBy { it[0] }
            }
            1 -> {
                oldVal.sortBy { terms_by_order.indexOf(it[2]) }
            }
            2 -> {
                oldVal.sortByDescending { it[3].toIntOrNull() ?: -1 }
            }
        }
        courses.value = oldVal
        displayedCourses.value = oldVal
    }

    fun filterCourses(mode: Int) {
        /**
         * 0: All courses
         * 1: CS Only
         * 2: MATH Only
         * 3: Other Only
         */
        var oldVal = displayedCourses.value ?: mutableListOf()
        when (mode) {
            0 -> {
                oldVal = courses.value ?: mutableListOf()
            }
            1 -> {
                oldVal = courses.value ?: mutableListOf()
                oldVal = oldVal.filter { it[0].startsWith("CS") }.toMutableList()
            }
            2 -> {
                oldVal = courses.value ?: mutableListOf()
                oldVal = oldVal.filter { it[0].startsWith("MATH") ||
                        it[0].startsWith("STAT") ||
                        it[0].startsWith("CO")}.toMutableList()
            }
            3 -> {
                oldVal = courses.value ?: mutableListOf()
                oldVal = oldVal.filter {
                    !it[0].startsWith("CS") &&
                            !it[0].startsWith("MATH")
                }.toMutableList()
            }
        }
        displayedCourses.value = oldVal
    }
}
