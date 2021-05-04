package com.rachmatwahid.academy

import com.rachmatwahid.academy.data.CourseEntity

interface BookmarkFragmentCallback {

    fun onShareClick(course: CourseEntity)
}
