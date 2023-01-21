package com.example.timetable.filter;

import com.example.timetable.entity.Admin;
import com.example.timetable.entity.Lecturer;
import com.example.timetable.entity.Student;
import com.example.timetable.service.AdminService;
import com.example.timetable.service.ClassService;
import com.example.timetable.service.LecturerService;
import com.example.timetable.service.StudentService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@WebFilter(urlPatterns = {"/*"})
public class AuthorisationFilter implements Filter{


    @Autowired
    ClassService classService;

    @Autowired
    AdminService adminService;

    @Autowired
    LecturerService lecturerService;

    @Autowired
    StudentService studentService;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String requestURI = request.getRequestURI().toLowerCase();

        if (requestURI.contains("login"))
        {
            // No authorisation required
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if (adminIsAuthorised(request))
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if (lecturerIsAuthorised(request))
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if (studentIsAuthorised(request))
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else
        {
            response.sendError(401, "You are not authorised to access this resource");
        }

}


private boolean adminIsAuthorised(HttpServletRequest request)
{
    String requestURI = request.getRequestURI().toLowerCase();
    System.out.println("ADMIN REQUEST URI: " + requestURI);
    String token = request.getHeader("AUTHORIZATION");

    Admin admin = adminService.checkToken(token);

    if (admin != null && requestURI.contains("/admin/"))
        return true;
    else
        return false;
}

private boolean lecturerIsAuthorised(HttpServletRequest request)
{
    String requestURI = request.getRequestURI().toLowerCase();
    System.out.println("LECTURER REQUEST URI: " + requestURI);
    String token = request.getHeader("AUTHORIZATION");

    Lecturer lecturer = lecturerService.checkToken(token);

    if (lecturer != null){
        if (requestURI.startsWith("/api/v1/lecturer/")){
            String[] split = requestURI.split("/");
            String classId = split[split.length - 1];
            if (classService.checkLecturer(classId, lecturer.getId())){
                return true;
            }
        }

    }

    return false;
}

    private boolean studentIsAuthorised(HttpServletRequest request) {

        String requestURI = request.getRequestURI().toLowerCase();
        System.out.println("STUDENT REQUEST URI: " + requestURI);
        String token = request.getHeader("AUTHORIZATION");

        Student student = studentService.checkToken(token);

        if (student != null) {
            if (requestURI.startsWith("/api/v1/student") )
            {
                return true;
            }
            }
        return false;
    }


}
