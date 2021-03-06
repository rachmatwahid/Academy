package com.rachmatwahid.academy.data.source.remote

import android.os.Handler
import android.os.Looper
import com.rachmatwahid.academy.data.source.remote.response.ContentResponse
import com.rachmatwahid.academy.data.source.remote.response.CourseResponse
import com.rachmatwahid.academy.data.source.remote.response.ModuleResponse
import com.rachmatwahid.academy.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper){

    companion object {

        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    private val handler = Handler(Looper.getMainLooper())

    fun getAllCourses(callback: LoadCoursesCallback) {
        handler.postDelayed({
            callback.onAllCoursesReceived(jsonHelper.loadCourses())
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getModules(courseId: String, callback: LoadModulesCallback) {
        handler.postDelayed({
            callback.onAllModulesReceived(jsonHelper.loadModule(courseId))
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getContent(moduleId: String, callback: LoadContentCallback) {
        handler.postDelayed({
            callback.onContentReceived(jsonHelper.loadContent(moduleId))
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    interface LoadCoursesCallback {
        fun onAllCoursesReceived(courseResponse: ArrayList<CourseResponse>)
    }

    interface LoadModulesCallback {
        fun onAllModulesReceived(moduleResponse: ArrayList<ModuleResponse>)
    }

    interface LoadContentCallback {
        fun onContentReceived(contentResponse: ContentResponse)
    }
}