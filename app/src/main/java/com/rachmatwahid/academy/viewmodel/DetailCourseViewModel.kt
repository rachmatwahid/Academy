package com.rachmatwahid.academy.viewmodel

import androidx.lifecycle.ViewModel
import com.rachmatwahid.academy.data.CourseEntity
import com.rachmatwahid.academy.data.ModuleEntity
import com.rachmatwahid.academy.utils.DataDummy

class DetailCourseViewModel: ViewModel() {

    private lateinit var courseId: String

    fun setSelectedCourse(courseId: String) {
        this.courseId = courseId
    }

    fun getCourse(): CourseEntity {
        lateinit var course: CourseEntity
        val coursesEntities = DataDummy.generateDummyCourses()
        for (courseEntity in coursesEntities) {
            if (courseEntity.courseId == courseId) {
                course = courseEntity
            }
        }
        return course
    }

    fun getModules(): List<ModuleEntity> = DataDummy.generateDummyModules(courseId)

}