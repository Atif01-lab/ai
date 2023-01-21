package com.example.timetable.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.timetable.TimetableExceptionHandler;
import com.example.timetable.converter.ClassConverter;
import com.example.timetable.dto.ClassDTO;
import com.example.timetable.entity.Class;
import com.example.timetable.entity.Classroom;
import com.example.timetable.entity.Department;
import com.example.timetable.entity.Lecturer;
import com.example.timetable.entity.Student;
import com.example.timetable.exception.ClassroomNotAvailableException;
import com.example.timetable.repository.ClassRepository;
import com.example.timetable.repository.ClassroomRepository;
import com.example.timetable.repository.LecturerRepository;
import com.example.timetable.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClassService.class})
@ExtendWith(SpringExtension.class)
class ClassServiceTest {
    @MockBean
    private ClassConverter classConverter;

    @MockBean
    private ClassRepository classRepository;

    @Autowired
    private ClassService classService;

    @MockBean
    private ClassroomRepository classroomRepository;

    @MockBean
    private LecturerRepository lecturerRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private TimetableExceptionHandler timetableExceptionHandler;

    /**
     * Method under test: {@link ClassService#assignClassToLecturer(Long, Long)}
     */
    @Test
    void testAssignClassToLecturer() {
        when(classRepository.save((Class) any())).thenReturn(new Class());
        when(classRepository.findById((Long) any())).thenReturn(Optional.of(new Class()));
        Lecturer lecturer = new Lecturer();
        when(lecturerRepository.findById((Long) any())).thenReturn(Optional.of(lecturer));
        Optional<Class> actualAssignClassToLecturerResult = classService.assignClassToLecturer(123L, 123L);
        assertTrue(actualAssignClassToLecturerResult.isPresent());
        assertSame(lecturer, actualAssignClassToLecturerResult.get().getLecturer());
        verify(classRepository).save((Class) any());
        verify(classRepository).findById((Long) any());
        verify(lecturerRepository).findById((Long) any());
    }



    @Test
    void testGetClassesForLecturer() {
        when(classRepository.findAllByLecturerId((Long) any())).thenReturn(new ArrayList<>());
        assertTrue(classService.getClassesForLecturer(123L).isEmpty());
        verify(classRepository).findAllByLecturerId((Long) any());
    }




    @Test
    void testGetClassesForLecturer6() {
        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassroomId(123L);
        classDTO.setDate("2020-03-01");
        classDTO.setDepartmentId(123L);
        classDTO.setEnrolledStudents(new ArrayList<>());
        classDTO.setId(123L);
        classDTO.setIsCancelled(true);
        classDTO.setLecturerId(123L);
        classDTO.setTime("Time");
        when(classConverter.toDTO((Class) any())).thenReturn(classDTO);
        Class resultClass = mock(Class.class);
        when(resultClass.getIsCancelled()).thenReturn(false);

        ArrayList<Class> resultClassList = new ArrayList<>();
        resultClassList.add(resultClass);
        when(classRepository.findAllByLecturerId((Long) any())).thenReturn(resultClassList);
        assertEquals(1, classService.getClassesForLecturer(123L).size());
        verify(classConverter).toDTO((Class) any());
        verify(classRepository).findAllByLecturerId((Long) any());
        verify(resultClass).getIsCancelled();
    }


    @Test
    void testCancelClass() {
        when(classRepository.findAllByLecturerId((Long) any())).thenReturn(new ArrayList<>());
        ResponseEntity<?> actualCancelClassResult = classService.cancelClass(123L, 123L);
        assertNull(actualCancelClassResult.getBody());
        assertEquals(404, actualCancelClassResult.getStatusCodeValue());
        assertTrue(actualCancelClassResult.getHeaders().isEmpty());
        verify(classRepository).findAllByLecturerId((Long) any());
    }


    @Test
    void testCancelClass3() {
        when(classRepository.findAllByLecturerId((Long) any()))
                .thenThrow(new ClassroomNotAvailableException("An error occurred"));
        assertThrows(ClassroomNotAvailableException.class, () -> classService.cancelClass(123L, 123L));
        verify(classRepository).findAllByLecturerId((Long) any());
    }


    @Test
    void testCancelClass4() {
        ArrayList<Class> resultClassList = new ArrayList<>();
        Department department = new Department();
        Classroom classroom = new Classroom();
        ArrayList<Student> enrolledStudents = new ArrayList<>();
        resultClassList
                .add(new Class(123L, "Time", "2020-03-01", department, classroom, enrolledStudents, new Lecturer(), true));
        when(classRepository.save((Class) any())).thenReturn(new Class());
        when(classRepository.findAllByLecturerId((Long) any())).thenReturn(resultClassList);
        ResponseEntity<?> actualCancelClassResult = classService.cancelClass(123L, 123L);
        assertEquals("Class cancelled successfully ", actualCancelClassResult.getBody());
        assertEquals(200, actualCancelClassResult.getStatusCodeValue());
        assertTrue(actualCancelClassResult.getHeaders().isEmpty());
        verify(classRepository).save((Class) any());
        verify(classRepository).findAllByLecturerId((Long) any());
    }


    @Test
    void testCancelClass5() {
        ArrayList<Class> resultClassList = new ArrayList<>();
        Department department = new Department();
        Classroom classroom = new Classroom();
        ArrayList<Student> enrolledStudents = new ArrayList<>();
        resultClassList
                .add(new Class(1L, "Time", "2020-03-01", department, classroom, enrolledStudents, new Lecturer(), true));
        when(classRepository.save((Class) any())).thenReturn(new Class());
        when(classRepository.findAllByLecturerId((Long) any())).thenReturn(resultClassList);
        ResponseEntity<?> actualCancelClassResult = classService.cancelClass(123L, 123L);
        assertEquals("Class not found", actualCancelClassResult.getBody());
        assertEquals(400, actualCancelClassResult.getStatusCodeValue());
        assertTrue(actualCancelClassResult.getHeaders().isEmpty());
        verify(classRepository).findAllByLecturerId((Long) any());
    }


    @Test
    void testCancelClass7() {
        ArrayList<Class> resultClassList = new ArrayList<>();
        Department department = new Department();
        Classroom classroom = new Classroom();
        ArrayList<Student> enrolledStudents = new ArrayList<>();
        resultClassList
                .add(new Class(123L, "Time", "2020-03-01", department, classroom, enrolledStudents, new Lecturer(), true));
        when(classRepository.save((Class) any())).thenThrow(new ClassroomNotAvailableException("An error occurred"));
        when(classRepository.findAllByLecturerId((Long) any())).thenReturn(resultClassList);
        assertThrows(ClassroomNotAvailableException.class, () -> classService.cancelClass(123L, 123L));
        verify(classRepository).save((Class) any());
        verify(classRepository).findAllByLecturerId((Long) any());
    }


    @Test
    void testCreateClass() {
        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassroomId(123L);
        classDTO.setDate("2020-03-01");
        classDTO.setDepartmentId(123L);
        classDTO.setEnrolledStudents(new ArrayList<>());
        classDTO.setId(123L);
        classDTO.setIsCancelled(true);
        classDTO.setLecturerId(123L);
        classDTO.setTime("Time");
        when(classConverter.toDTO((Class) any())).thenReturn(classDTO);
        when(classConverter.toEntity((ClassDTO) any())).thenReturn(new Class());
        when(classRepository.save((Class) any())).thenReturn(new Class());
        when(classRepository.findAllByClassroom_Id((Long) any())).thenReturn(new ArrayList<>());

        ClassDTO classDTO1 = new ClassDTO();
        classDTO1.setClassroomId(123L);
        classDTO1.setDate("2020-03-01");
        classDTO1.setDepartmentId(123L);
        classDTO1.setEnrolledStudents(new ArrayList<>());
        classDTO1.setId(123L);
        classDTO1.setIsCancelled(true);
        classDTO1.setLecturerId(123L);
        classDTO1.setTime("Time");
        assertSame(classDTO, classService.createClass(classDTO1));
        verify(classConverter).toDTO((Class) any());
        verify(classConverter).toEntity((ClassDTO) any());
        verify(classRepository).save((Class) any());
        verify(classRepository).findAllByClassroom_Id((Long) any());
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
        when(classConverter.toDTO((Class) any())).thenReturn(classDTO);
        when(classConverter.toEntity((ClassDTO) any())).thenReturn(new Class());
        when(classRepository.save((Class) any())).thenThrow(new ClassroomNotAvailableException("An error occurred"));
        when(classRepository.findAllByClassroom_Id((Long) any()))
                .thenThrow(new ClassroomNotAvailableException("An error occurred"));

        ClassDTO classDTO1 = new ClassDTO();
        classDTO1.setClassroomId(123L);
        classDTO1.setDate("2020-03-01");
        classDTO1.setDepartmentId(123L);
        classDTO1.setEnrolledStudents(new ArrayList<>());
        classDTO1.setId(123L);
        classDTO1.setIsCancelled(true);
        classDTO1.setLecturerId(123L);
        classDTO1.setTime("Time");
        assertThrows(ClassroomNotAvailableException.class, () -> classService.createClass(classDTO1));
        verify(classConverter).toEntity((ClassDTO) any());
        verify(classRepository).findAllByClassroom_Id((Long) any());
    }



    @Test
    void testGetAllClassesForStudent() {
        when(classRepository.findAllClassesByStudentId((Long) any())).thenReturn(new ArrayList<>());
        assertTrue(classService.getAllClassesForStudent(123L).isEmpty());
        verify(classRepository).findAllClassesByStudentId((Long) any());
    }


    @Test
    void testGetAllClassesForStudent3() {
        when(classRepository.findAllClassesByStudentId((Long) any()))
                .thenThrow(new ClassroomNotAvailableException("An error occurred"));
        assertThrows(ClassroomNotAvailableException.class, () -> classService.getAllClassesForStudent(123L));
        verify(classRepository).findAllClassesByStudentId((Long) any());
    }


    @Test
    void testGetAllClassesForStudent4() {
        Class resultClass = new Class();
        resultClass.setIsCancelled(true);

        ArrayList<Class> resultClassList = new ArrayList<>();
        resultClassList.add(resultClass);
        when(classRepository.findAllClassesByStudentId((Long) any())).thenReturn(resultClassList);
        assertTrue(classService.getAllClassesForStudent(123L).isEmpty());
        verify(classRepository).findAllClassesByStudentId((Long) any());
    }



    @Test
    void testGetAllClassesForStudent6() {
        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassroomId(123L);
        classDTO.setDate("2020-03-01");
        classDTO.setDepartmentId(123L);
        classDTO.setEnrolledStudents(new ArrayList<>());
        classDTO.setId(123L);
        classDTO.setIsCancelled(true);
        classDTO.setLecturerId(123L);
        classDTO.setTime("Time");
        when(classConverter.toDTO((Class) any())).thenReturn(classDTO);
        Class resultClass = mock(Class.class);
        when(resultClass.getIsCancelled()).thenReturn(false);

        ArrayList<Class> resultClassList = new ArrayList<>();
        resultClassList.add(resultClass);
        when(classRepository.findAllClassesByStudentId((Long) any())).thenReturn(resultClassList);
        assertEquals(1, classService.getAllClassesForStudent(123L).size());
        verify(classConverter).toDTO((Class) any());
        verify(classRepository).findAllClassesByStudentId((Long) any());
        verify(resultClass).getIsCancelled();
    }



    @Test
    void testEnrollStudentToClass2() {
        when(classRepository.findById((Long) any())).thenReturn(Optional.of(new Class()));
        when(studentRepository.findById((Long) any())).thenThrow(new ClassroomNotAvailableException("An error occurred"));
        assertThrows(ClassroomNotAvailableException.class, () -> classService.enrollStudentToClass(123L, 123L));
        verify(classRepository).findById((Long) any());
        verify(studentRepository).findById((Long) any());
    }


    @Test
    void testEnrollStudentToClass4() {
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student());
        Department department = new Department();
        Classroom classroom = new Classroom();
        ArrayList<Student> enrolledStudents = new ArrayList<>();

        Class resultClass = new Class(123L, "Time", "2020-03-01", department, classroom, enrolledStudents, new Lecturer(),
                true);
        resultClass.setEnrolledStudents(studentList);
        Optional<Class> ofResult = Optional.of(resultClass);
        when(classRepository.findById((Long) any())).thenReturn(ofResult);
        when(studentRepository.findById((Long) any())).thenReturn(Optional.of(new Student()));
        assertThrows(ClassroomNotAvailableException.class, () -> classService.enrollStudentToClass(123L, 123L));
        verify(classRepository).findById((Long) any());
        verify(studentRepository).findById((Long) any());
    }

    @Test
    void testEnrollStudentToClass5() {
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student());
        Department department = new Department();
        Classroom classroom = new Classroom(123L, 10, 10, 3, new Department());

        ArrayList<Student> enrolledStudents = new ArrayList<>();

        Class resultClass = new Class(123L, "Time", "2020-03-01", department, classroom, enrolledStudents, new Lecturer(),
                true);
        resultClass.setEnrolledStudents(studentList);
        Optional<Class> ofResult = Optional.of(resultClass);
        when(classRepository.save((Class) any())).thenReturn(new Class());
        when(classRepository.findById((Long) any())).thenReturn(ofResult);
        when(studentRepository.findById((Long) any())).thenReturn(Optional.of(new Student()));
        assertSame(resultClass, classService.enrollStudentToClass(123L, 123L));
        verify(classRepository).save((Class) any());
        verify(classRepository).findById((Long) any());
        verify(studentRepository).findById((Long) any());
    }

    @Test
    void testEnrollStudentToClass8() {
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student());
        Department department = new Department();
        Classroom classroom = new Classroom(123L, 10, 10, 3, new Department());

        ArrayList<Student> enrolledStudents = new ArrayList<>();

        Class resultClass = new Class(123L, "Time", "2020-03-01", department, classroom, enrolledStudents, new Lecturer(),
                true);
        resultClass.setEnrolledStudents(studentList);
        Optional<Class> ofResult = Optional.of(resultClass);
        when(classRepository.save((Class) any())).thenThrow(new ClassroomNotAvailableException("An error occurred"));
        when(classRepository.findById((Long) any())).thenReturn(ofResult);
        when(studentRepository.findById((Long) any())).thenReturn(Optional.of(new Student()));
        assertThrows(ClassroomNotAvailableException.class, () -> classService.enrollStudentToClass(123L, 123L));
        verify(classRepository).save((Class) any());
        verify(classRepository).findById((Long) any());
        verify(studentRepository).findById((Long) any());
    }



    @Test
    void testRemoveStudentFromClass2() {
        when(classRepository.findById((Long) any())).thenReturn(Optional.of(new Class()));
        when(studentRepository.findById((Long) any())).thenThrow(new ClassroomNotAvailableException("An error occurred"));
        assertThrows(ClassroomNotAvailableException.class, () -> classService.removeStudentFromClass(123L, 123L));
        verify(classRepository).findById((Long) any());
        verify(studentRepository).findById((Long) any());
    }

    @Test
    void testRemoveStudentFromClass3() {
        Department department = new Department();
        Classroom classroom = new Classroom();
        ArrayList<Student> enrolledStudents = new ArrayList<>();
        when(classRepository.findById((Long) any())).thenReturn(Optional
                .of(new Class(123L, "Time", "2020-03-01", department, classroom, enrolledStudents, new Lecturer(), true)));
        when(studentRepository.findById((Long) any())).thenReturn(Optional.of(new Student()));
        assertThrows(ClassroomNotAvailableException.class, () -> classService.removeStudentFromClass(123L, 123L));
        verify(classRepository).findById((Long) any());
        verify(studentRepository).findById((Long) any());
    }


    @Test
    void testRemoveStudentFromClass4() {
        Class resultClass = mock(Class.class);
        when(resultClass.getEnrolledStudents()).thenThrow(new ClassroomNotAvailableException("An error occurred"));
        Optional<Class> ofResult = Optional.of(resultClass);
        when(classRepository.findById((Long) any())).thenReturn(ofResult);
        when(studentRepository.findById((Long) any())).thenReturn(Optional.of(new Student()));
        assertThrows(ClassroomNotAvailableException.class, () -> classService.removeStudentFromClass(123L, 123L));
        verify(classRepository).findById((Long) any());
        verify(resultClass).getEnrolledStudents();
        verify(studentRepository).findById((Long) any());
    }


    @Test
    void testCheckLecturer2() {
        when(classRepository.findById((Long) any())).thenReturn(Optional.of(new Class()));
        when(lecturerRepository.findById((Long) any()))
                .thenThrow(new ClassroomNotAvailableException("An error occurred"));
        assertThrows(ClassroomNotAvailableException.class, () -> classService.checkLecturer("42", 123L));
        verify(classRepository).findById((Long) any());
        verify(lecturerRepository).findById((Long) any());
    }


    @Test
    void testCheckLecturer4() {
        Lecturer lecturer = new Lecturer();
        lecturer.setId(123L);

        Class resultClass = new Class();
        resultClass.setLecturer(lecturer);
        Optional<Class> ofResult = Optional.of(resultClass);
        when(classRepository.findById((Long) any())).thenReturn(ofResult);
        when(lecturerRepository.findById((Long) any())).thenReturn(Optional.of(new Lecturer()));
        assertFalse(classService.checkLecturer("42", 123L));
        verify(classRepository).findById((Long) any());
        verify(lecturerRepository).findById((Long) any());
    }


    @Test
    void testCheckLecturer6() {
        Lecturer lecturer = new Lecturer();
        lecturer.setId(123L);

        Class resultClass = new Class();
        resultClass.setLecturer(lecturer);
        Optional<Class> ofResult = Optional.of(resultClass);
        when(classRepository.findById((Long) any())).thenReturn(ofResult);

        Lecturer lecturer1 = new Lecturer();
        lecturer1.setId(123L);
        Optional<Lecturer> ofResult1 = Optional.of(lecturer1);
        when(lecturerRepository.findById((Long) any())).thenReturn(ofResult1);
        assertTrue(classService.checkLecturer("42", 123L));
        verify(classRepository).findById((Long) any());
        verify(lecturerRepository).findById((Long) any());
    }


    @Test
    void testCheckLecturer7() {
        Lecturer lecturer = new Lecturer();
        lecturer.setId(123L);

        Class resultClass = new Class();
        resultClass.setLecturer(lecturer);
        Optional<Class> ofResult = Optional.of(resultClass);
        when(classRepository.findById((Long) any())).thenReturn(ofResult);
        Lecturer lecturer1 = mock(Lecturer.class);
        when(lecturer1.getId()).thenThrow(new ClassroomNotAvailableException("An error occurred"));
        Optional<Lecturer> ofResult1 = Optional.of(lecturer1);
        when(lecturerRepository.findById((Long) any())).thenReturn(ofResult1);
        assertThrows(ClassroomNotAvailableException.class, () -> classService.checkLecturer("42", 123L));
        verify(classRepository).findById((Long) any());
        verify(lecturerRepository).findById((Long) any());
        verify(lecturer1).getId();
    }



}

