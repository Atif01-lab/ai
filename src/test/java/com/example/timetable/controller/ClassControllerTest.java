package com.example.timetable.controller;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.timetable.converter.ClassConverter;
import com.example.timetable.dto.ClassDTO;
import com.example.timetable.entity.*;
import com.example.timetable.entity.Class;
import com.example.timetable.entity.Classroom;
import com.example.timetable.entity.Department;
import com.example.timetable.entity.Lecturer;
import com.example.timetable.entity.Student;
import com.example.timetable.repository.ClassRepository;
import com.example.timetable.repository.ClassroomRepository;
import com.example.timetable.repository.LecturerRepository;
import com.example.timetable.service.ClassService;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import com.example.timetable.service.LecturerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class ClassControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ClassService classService;

    @Autowired
    private LecturerRepository lecturerRepository;
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private Classroom classroom;

    @Autowired
    private Department department;

    @Autowired
    Lecturer lecturer;


    @BeforeEach
    public void setUp() {
        classroom = new Classroom();
        classroom.setId(1L);

        department = new Department();
        department.setId(1L);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();



    }

    @AfterEach
    public void tearDown() {
        classRepository.deleteAll();
    }

    @Test
    public void getClasses_shouldReturnOKStatus() throws Exception {
        Lecturer lecturer = new Lecturer();
        lecturer.setId(1L);
        lecturer.setDepartment(department);
        lecturer.setName("John Smith");
        lecturer.setEmail("lecturerEmail@gmail.com");
        lecturer.setPassword("password");
        lecturer.setToken("token");
        lecturer.setSubjects(null);
        lecturerRepository.save(lecturer);

        Class class1 = new Class();
        class1.setDate("01-01-2022");
        class1.setTime("9:00 AM");
        class1.setClassroom(classroom);
        class1.setIsCancelled(false);
        class1.setDepartment(department);
        class1.setLecturer(lecturer);
        class1.setEnrolledStudents(null);
        classRepository.save(class1);

        Class class2 = new Class();
        class2.setDate("02-02-2022");
        class2.setTime("10:00 AM");
        class2.setIsCancelled(false);
        class2.setDepartment(department);
        class2.setLecturer(lecturer);
        class2.setClassroom(classroom);
        class2.setEnrolledStudents(null);
        classRepository.save(class2);

        List<ClassDTO> classes = classService.getClassesForLecturer(1L);
        assertThat(classes.size(), equalTo(2));
        assertThat(classes.get(0).getTime(), equalTo("9:00 AM"));
        assertThat(classes.get(0).getDate(), equalTo("01-01-2022"));
        assertThat(classes.get(1).getDate(), equalTo("02-02-2022"));
        assertThat(classes.get(1).getTime(), equalTo("10:00 AM"));

    }

    @Test
    public void getClasses_with_InvalidData_shouldReturnNotFoundStatus() throws Exception {
        Lecturer lecturer = new Lecturer();
        lecturer.setId(1L);
        lecturer.setDepartment(department);
        lecturer.setName("John Smith");
        lecturer.setEmail("lecturer@gmial.com");
        lecturer.setPassword("password");
        lecturer.setToken("token");
        lecturer.setSubjects(null);
        lecturerRepository.save(lecturer);

        Class class1 = new Class();
        class1.setDate("01-01-2022");
        class1.setTime("9:00 AM");
        class1.setClassroom(classroom);
        class1.setIsCancelled(false);
        class1.setDepartment(department);
        class1.setLecturer(lecturer);
        class1.setEnrolledStudents(null);
        classRepository.save(class1);

        List<ClassDTO> classes = classService.getClassesForLecturer(2L);
        assertThat(classes.size(), equalTo(0));

    }



    @Test
    public void getClasses_shouldReturnNotFoundStatus() throws Exception {
        List<ClassDTO> classes = classService.getClassesForLecturer(3L);
        assertThat(classes, is(empty()));
    }

    @Test
    public void assignClassToLecturer_shouldReturnOKStatus() throws Exception {


        Lecturer lecturer = new Lecturer();
        lecturer.setId(1L);
        lecturer.setDepartment(department);
        lecturer.setName("John Smith");
        lecturer.setEmail("lecturerEmail@gmail.com");
        lecturer.setPassword("password");
        lecturer.setToken("token");
        lecturer.setSubjects(null);
        lecturerRepository.save(lecturer);

        Class class1 = new Class();
        class1.setDate("01-01-2022");
        class1.setTime("9:00 AM");
        class1.setClassroom(classroom);
        class1.setIsCancelled(false);
        class1.setDepartment(department);
        class1.setLecturer(lecturer);
        class1.setEnrolledStudents(null);
        classRepository.save(class1);

        //asseertion

        Optional<Class> classDTO = classService.assignClassToLecturer(1L, 1L);

        assertThat(classDTO.get().getLecturer().getId(), equalTo(1L));
        assertThat(classDTO.get().getLecturer().getName(), equalTo("John Smith"));
        assertThat(classDTO.get().getLecturer().getEmail(), equalTo("lecturerEmail@gmail.com"));

    }


    @Test
    void testAssignClassToLecturer4() {

        Department department = new Department();
        department.setId(123L);
        Department department1 = new Department();
        Classroom classroom = new Classroom();
        ArrayList<Student> enrolledStudents = new ArrayList<>();

        Class resultClass = new Class(123L, "Time", "2020-03-01", department1, classroom, enrolledStudents,
                new Lecturer(), true);
        resultClass.setDepartment(department);
        ClassService classService = mock(ClassService.class);
        when(classService.assignClassToLecturer((Long) any(), (Long) any())).thenReturn(Optional.of(resultClass));
        ResponseEntity<ClassDTO> actualAssignClassToLecturerResult = (new ClassController(classService,
                new ClassConverter())).assignClassToLecturer(123L, 123L);
        assertTrue(actualAssignClassToLecturerResult.hasBody());
        assertTrue(actualAssignClassToLecturerResult.getHeaders().isEmpty());
        assertEquals(200, actualAssignClassToLecturerResult.getStatusCodeValue());
        ClassDTO body = actualAssignClassToLecturerResult.getBody();
        assertEquals("Time", body.getTime());
        assertEquals(123L, body.getDepartmentId().longValue());
        assertEquals("2020-03-01", body.getDate());
        assertNull(body.getClassroomId());
        assertNull(body.getLecturerId());
        assertTrue(body.getIsCancelled());
        assertEquals(123L, body.getId().longValue());
        verify(classService).assignClassToLecturer((Long) any(), (Long) any());
    }



    @Test
    void testAssignClassToLecturer7() {

        Department department = new Department();
        department.setId(123L);
        Department department1 = new Department();
        Classroom classroom = new Classroom();
        ArrayList<Student> enrolledStudents = new ArrayList<>();

        Class resultClass = new Class(123L, "Time", "2020-03-01", department1, classroom, enrolledStudents,
                new Lecturer(), true);
        resultClass.setDepartment(department);
        ClassService classService = mock(ClassService.class);
        when(classService.assignClassToLecturer((Long) any(), (Long) any())).thenReturn(Optional.of(resultClass));

        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassroomId(123L);
        classDTO.setDate("2020-03-01");
        classDTO.setDepartmentId(123L);
        classDTO.setEnrolledStudents(new ArrayList<>());
        classDTO.setId(123L);
        classDTO.setIsCancelled(true);
        classDTO.setLecturerId(123L);
        classDTO.setTime("Time");
        ClassConverter classConverter = mock(ClassConverter.class);
        when(classConverter.toDTO((Class) any())).thenReturn(classDTO);
        ResponseEntity<ClassDTO> actualAssignClassToLecturerResult = (new ClassController(classService, classConverter))
                .assignClassToLecturer(123L, 123L);
        assertTrue(actualAssignClassToLecturerResult.hasBody());
        assertTrue(actualAssignClassToLecturerResult.getHeaders().isEmpty());
        assertEquals(200, actualAssignClassToLecturerResult.getStatusCodeValue());
        verify(classService).assignClassToLecturer((Long) any(), (Long) any());
        verify(classConverter).toDTO((Class) any());
    }


    @Test
    void testGetClasses2() {

        ClassService classService = mock(ClassService.class);
        when(classService.getClassesForLecturer((Long) any())).thenReturn(new ArrayList<>());
        ResponseEntity<List<ClassDTO>> actualClasses = (new ClassController(classService, new ClassConverter()))
                .getClasses(123L);
        assertNull(actualClasses.getBody());
        assertEquals(404, actualClasses.getStatusCodeValue());
        assertTrue(actualClasses.getHeaders().isEmpty());
        verify(classService).getClassesForLecturer((Long) any());
    }


    @Test
    void testGetClasses3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassroomId(123L);
        classDTO.setDate("2020-03-01");
        classDTO.setDepartmentId(123L);
        classDTO.setEnrolledStudents(new ArrayList<>());
        classDTO.setId(123L);
        classDTO.setIsCancelled(true);
        classDTO.setLecturerId(123L);
        classDTO.setTime("Time");

        ArrayList<ClassDTO> classDTOList = new ArrayList<>();
        classDTOList.add(classDTO);
        ClassService classService = mock(ClassService.class);
        when(classService.getClassesForLecturer((Long) any())).thenReturn(classDTOList);
        ResponseEntity<List<ClassDTO>> actualClasses = (new ClassController(classService, new ClassConverter()))
                .getClasses(123L);
        assertTrue(actualClasses.hasBody());
        assertEquals(200, actualClasses.getStatusCodeValue());
        assertTrue(actualClasses.getHeaders().isEmpty());
        verify(classService).getClassesForLecturer((Long) any());
    }


    @Test
    void testCancelClass2() {

        ClassService classService = mock(ClassService.class);
        when((ResponseEntity<Object>) classService.cancelClass((Long) any(), (Long) any())).thenReturn(null);
        assertNull((new ClassController(classService, new ClassConverter())).cancelClass(123L, 123L));
        verify(classService).cancelClass((Long) any(), (Long) any());
    }

    @Test
    void testCreateClass2() {
        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassroomId(123L);
        classDTO.setDate("2020-03-01");
        classDTO.setDepartmentId(123L);
        classDTO.setEnrolledStudents(new ArrayList<>());
        classDTO.setId(123L);
        classDTO.setIsCancelled(true);
        classDTO.setLecturerId(123L);
        classDTO.setTime("Time");
        ClassService classService = mock(ClassService.class);
        when(classService.createClass((ClassDTO) any())).thenReturn(classDTO);
        ClassController classController = new ClassController(classService, new ClassConverter());

        ClassDTO classDTO1 = new ClassDTO();
        classDTO1.setClassroomId(123L);
        classDTO1.setDate("2020-03-01");
        classDTO1.setDepartmentId(123L);
        classDTO1.setEnrolledStudents(new ArrayList<>());
        classDTO1.setId(123L);
        classDTO1.setIsCancelled(true);
        classDTO1.setLecturerId(123L);
        classDTO1.setTime("Time");
        ResponseEntity<ClassDTO> actualCreateClassResult = classController.createClass(classDTO1);
        assertTrue(actualCreateClassResult.hasBody());
        assertTrue(actualCreateClassResult.getHeaders().isEmpty());
        assertEquals(201, actualCreateClassResult.getStatusCodeValue());
        verify(classService).createClass((ClassDTO) any());
    }



    @Test
    void testRemoveStudent_successful() {

        ClassService classService = mock(ClassService.class);
        when(classService.removeStudentFromClass((Long) any(), (Long) any())).thenReturn(new Class());
        assertEquals("Student removed from class successfully",
                (new ClassController(classService, new ClassConverter())).removeStudent(123L, 123L));
        verify(classService).removeStudentFromClass((Long) any(), (Long) any());
    }

    @Test
    void testRemoveStudent_unsuccessful() {

        ClassService classService = mock(ClassService.class);
        when(classService.removeStudentFromClass((Long) any(), (Long) any())).thenReturn(null);
        assertEquals("Student not removed from class",
                (new ClassController(classService, new ClassConverter())).removeStudent(123L, 123L));
        verify(classService).removeStudentFromClass((Long) any(), (Long) any());
    }



    @Test
    void testEnrollStudent_successful() {

        ClassService classService = mock(ClassService.class);
        when(classService.enrollStudentToClass((Long) any(), (Long) any())).thenReturn(new Class());
        assertEquals("Student enrolled to class successfully",
                (new ClassController(classService, new ClassConverter())).enrollStudent(123L, 123L));
        verify(classService).enrollStudentToClass((Long) any(), (Long) any());
    }

    @Test
    void testEnrollStudent_unsuccessful() {

        ClassService classService = mock(ClassService.class);
        when(classService.enrollStudentToClass((Long) any(), (Long) any())).thenReturn(null);
        assertEquals("Student not enrolled to class",
                (new ClassController(classService, new ClassConverter())).enrollStudent(123L, 123L));
        verify(classService).enrollStudentToClass((Long) any(), (Long) any());
    }
//
//    @Test
//    public void cancelClass_shouldReturnOKStatus() throws Exception {
//
//        Lecturer lecturer = new Lecturer();
//        lecturer.setId(1L);
//        lecturer.setDepartment(department);
//        lecturer.setName("John Smith");
//        lecturer.setEmail("lecturerEmail@gmail.com");
//        lecturer.setPassword("password");
//        lecturer.setToken("token");
//        lecturer.setSubjects(null);
//        lecturerRepository.save(lecturer);
//
//        Class class1 = new Class();
//        class1.setId(1L);
//        class1.setDate("01-01-2022");
//        class1.setTime("9:00 AM");
//        class1.setClassroom(classroom);
//        class1.setIsCancelled(false);
//        class1.setDepartment(department);
//        class1.setLecturer(lecturer);
//        class1.setEnrolledStudents(null);
//        classRepository.save(class1);
//
//        String token = "lecturertoken1";
//
//
//           mockMvc.perform(patch("/api/v1/lecturer/1/1")
//                    .header("Authorization", token))
//    .andExpect(status().isOk());
//    }

    @Test
    public void createClass_shouldReturnCreatedStatus() throws Exception {


        String token = "admintoken1";
        String json = "{\"date\":\"05-01-2022\",\"time\":\"9:00 AM\",\"classroomId\":1,\"departmentId\":1,\"lecturerId\":1}";

        mockMvc.perform(post("/api/v1/admin/class/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header("Authorization", token))
                .andExpect(status().isCreated());
    }

}


