package com.rachmatwahid.academy.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rachmatwahid.academy.data.ContentEntity
import com.rachmatwahid.academy.data.CourseEntity
import com.rachmatwahid.academy.data.ModuleEntity
import com.rachmatwahid.academy.data.source.remote.RemoteDataSource
import com.rachmatwahid.academy.data.source.remote.response.ContentResponse
import com.rachmatwahid.academy.data.source.remote.response.CourseResponse
import com.rachmatwahid.academy.data.source.remote.response.ModuleResponse

class AcademyRepository private constructor(private val remoteDataSource: RemoteDataSource) : AcademyDataSource {

    companion object {
        @Volatile
        private var instance: AcademyRepository? = null
        fun getInstance(remoteData: RemoteDataSource): AcademyRepository =
            instance ?: synchronized(this) {
                instance ?: AcademyRepository(remoteData).apply { instance = this }
            }
    }

    override fun getAllCourses(): LiveData<ArrayList<CourseEntity>> {
        val courseResult = MutableLiveData<ArrayList<CourseEntity>>()
        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback {
            override fun onAllCoursesReceived(courseResponse: ArrayList<CourseResponse>) {
                val courseList = ArrayList<CourseEntity>()
                for (response in courseResponse) {
                    val course = CourseEntity(response.id,
                        response.title,
                        response.description,
                        response.date,
                        false,
                        response.imagePath)
                    courseList.add(course)
                }
                courseResult.postValue(courseList)
            }
        })
        return courseResult
    }

    override fun getBookmarkedCourses(): LiveData<ArrayList<CourseEntity>> {
        val courseResults = MutableLiveData<ArrayList<CourseEntity>>()
        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback {
            override fun onAllCoursesReceived(courseResponse: ArrayList<CourseResponse>) {
                val courseList = ArrayList<CourseEntity>()
                for (response in courseResponse) {
                    val course = CourseEntity(response.id,
                        response.title,
                        response.description,
                        response.date,
                        false,
                        response.imagePath)
                    courseList.add(course)
                }
                courseResults.postValue(courseList)
            }
        })
        return courseResults
    }

    override fun getCourseWithModules(courseId: String): LiveData<CourseEntity> {
        val courseResult = MutableLiveData<CourseEntity>()
        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback {
            override fun onAllCoursesReceived(courseResponse: ArrayList<CourseResponse>) {
                lateinit var course: CourseEntity
                for (response in courseResponse) {
                    if (response.id == courseId) {
                        course = CourseEntity(response.id,
                            response.title,
                            response.description,
                            response.date,
                            false,
                            response.imagePath)
                    }
                }
                courseResult.postValue(course)
            }
        })
        return courseResult
    }

    override fun getAllModulesByCourse(courseId: String): LiveData<ArrayList<ModuleEntity>> {
        val moduleResults = MutableLiveData<ArrayList<ModuleEntity>>()
        remoteDataSource.getModules(courseId, object : RemoteDataSource.LoadModulesCallback {
            override fun onAllModulesReceived(moduleResponse: ArrayList<ModuleResponse>) {
                val moduleList = ArrayList<ModuleEntity>()
                for(response in moduleResponse) {
                    val course = ModuleEntity(response.moduleId,
                        response.courseId,
                        response.title,
                        response.position,
                        false)
                    moduleList.add(course)
                }
                moduleResults.postValue(moduleList)
            }
        })
        return moduleResults
    }

    override fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity> {
        val moduleResults = MutableLiveData<ModuleEntity>()
        remoteDataSource.getModules(courseId, object : RemoteDataSource.LoadModulesCallback {
            override fun onAllModulesReceived(moduleResponse: ArrayList<ModuleResponse>) {
                lateinit var module: ModuleEntity
                for (response in moduleResponse) {
                    if (response.moduleId == moduleId) {
                        module = ModuleEntity(
                            response.moduleId,
                            response.courseId,
                            response.title,
                            response.position,
                            false
                        )
                        remoteDataSource.getContent(
                            moduleId,
                            object : RemoteDataSource.LoadContentCallback {
                                override fun onContentReceived(contentResponse: ContentResponse) {
                                    module.contentEntity = ContentEntity(contentResponse.content)
                                    moduleResults.postValue(module)
                                }
                            })
                        break
                    }
                }
            }
        })
        return moduleResults
    }
}