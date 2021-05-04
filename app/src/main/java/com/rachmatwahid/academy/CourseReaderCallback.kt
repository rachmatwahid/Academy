package com.rachmatwahid.academy

interface CourseReaderCallback {
    fun moveTo(position: Int, moduleId: String)
}