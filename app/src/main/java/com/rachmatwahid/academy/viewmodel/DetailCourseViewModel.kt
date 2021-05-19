package com.rachmatwahid.academy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rachmatwahid.academy.data.CourseEntity
import com.rachmatwahid.academy.data.ModuleEntity
import com.rachmatwahid.academy.data.source.AcademyRepository

class DetailCourseViewModel(private val academyRepository: AcademyRepository): ViewModel() {

    private lateinit var courseId: String

    fun setSelectedCourse(courseId: String) {
        this.courseId = courseId
    }

    fun getCourse(): LiveData<CourseEntity> = academyRepository.getCourseWithModules(courseId)

    fun getModules(): LiveData<ArrayList<ModuleEntity>> = academyRepository.getAllModulesByCourse(courseId)

}