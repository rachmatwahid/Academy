package com.rachmatwahid.academy.data.source

import androidx.lifecycle.LiveData
import com.rachmatwahid.academy.data.CourseEntity
import com.rachmatwahid.academy.data.ModuleEntity

interface AcademyDataSource {

    fun getAllCourses(): LiveData<ArrayList<CourseEntity>>

    fun getBookmarkedCourses(): LiveData<ArrayList<CourseEntity>>

    fun getCourseWithModules(courseId: String): LiveData<CourseEntity>

    fun getAllModulesByCourse(courseId: String): LiveData<ArrayList<ModuleEntity>>

    fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity>
}