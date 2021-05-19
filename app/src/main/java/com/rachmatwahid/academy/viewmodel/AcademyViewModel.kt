package com.rachmatwahid.academy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rachmatwahid.academy.data.CourseEntity
import com.rachmatwahid.academy.data.source.AcademyRepository

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getCourses(): LiveData<ArrayList<CourseEntity>> = academyRepository.getAllCourses()

}