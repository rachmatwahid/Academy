package com.rachmatwahid.academy.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.rachmatwahid.academy.data.CourseEntity
import com.rachmatwahid.academy.data.ModuleEntity
import com.rachmatwahid.academy.data.source.AcademyRepository
import com.rachmatwahid.academy.utils.DataDummy
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailCourseViewModelTest {

    private lateinit var viewModel: DetailCourseViewModel
    private val dummyCourse = DataDummy.generateDummyCourses()[0]
    private val courseId = dummyCourse.courseId
    private val dummyModules = DataDummy.generateDummyModules(courseId)


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Mock
    private lateinit var courseObserver: Observer<CourseEntity>

    @Mock
    private lateinit var modulesObserver: Observer<ArrayList<ModuleEntity>>

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel(academyRepository)
        viewModel.setSelectedCourse(courseId)
    }

    @Test
    fun getCourse() {
        val course = MutableLiveData<CourseEntity>()
        course.value = dummyCourse

        `when`(academyRepository.getCourseWithModules(courseId)).thenReturn(course)
        val courseEntity = viewModel.getCourse().value as CourseEntity
        verify(academyRepository).getCourseWithModules(courseId)
        assertNotNull(courseEntity)
        assertEquals(dummyCourse.courseId, courseEntity.courseId)
        assertEquals(dummyCourse.deadline, courseEntity.deadline)
        assertEquals(dummyCourse.description, courseEntity.description)
        assertEquals(dummyCourse.imagePath, courseEntity.imagePath)
        assertEquals(dummyCourse.title, courseEntity.title)

        viewModel.getCourse().observeForever(courseObserver)
        verify(courseObserver).onChanged(dummyCourse)
    }

    @Test
    fun getModules() {
        val module = MutableLiveData<ArrayList<ModuleEntity>>()
        module.value = dummyModules

        `when`(academyRepository.getAllModulesByCourse(courseId)).thenReturn(module)
        val moduleEntities = viewModel.getModules().value
        verify(academyRepository).getAllModulesByCourse(courseId)
        assertNotNull(moduleEntities)
        assertEquals(7, moduleEntities?.size)

        viewModel.getModules().observeForever(modulesObserver)
        verify(modulesObserver).onChanged(dummyModules)
    }
}