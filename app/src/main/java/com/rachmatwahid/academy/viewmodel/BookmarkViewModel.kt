package com.rachmatwahid.academy.viewmodel

import androidx.lifecycle.ViewModel
import com.rachmatwahid.academy.data.CourseEntity
import com.rachmatwahid.academy.utils.DataDummy

class BookmarkViewModel: ViewModel() {

    fun getBookmarks(): List<CourseEntity> = DataDummy.generateDummyCourses()
}