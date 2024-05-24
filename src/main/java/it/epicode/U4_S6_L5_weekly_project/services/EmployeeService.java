package it.epicode.U4_S6_L5_weekly_project.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.epicode.U4_S6_L5_weekly_project.entities.Employee;
import it.epicode.U4_S6_L5_weekly_project.exceptions.NotFoundException;
import it.epicode.U4_S6_L5_weekly_project.records.EmployeePayloadDto;
import it.epicode.U4_S6_L5_weekly_project.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    Cloudinary cloudinary;

    // GET all

    public Page<Employee> getAllEmployees(int page, int size, String sortBy) {
        if (size < 0) {
            size = 10;
        }
        if (size > 100) {
            size = 100;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return employeeRepository.findAll(pageable);
    }

    // GET id

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // POST

    public Employee saveEmployee(Employee employee) {
        if (employee.getAvatar() == null || employee.getAvatar().isEmpty()) {
            employee.setAvatar("https://ui-avatars.com/api/?name=" +
                    employee.getFirstName() + "+" +
                    employee.getLastName());
        } else {
            employeeRepository.save(employee);
        }
        return employee;
    }

    // PUT

    public Employee updateEmployee(long id, EmployeePayloadDto updatedEmployee) {
        Employee employeeToBeUpdated = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        employeeToBeUpdated.setUsername(updatedEmployee.username());
        employeeToBeUpdated.setFirstName(updatedEmployee.firstName());
        employeeToBeUpdated.setLastName(updatedEmployee.lastName());
        employeeToBeUpdated.setEmail(updatedEmployee.email());
        employeeToBeUpdated.setAvatar("https://ui-avatars.com/api/?name=" +
                updatedEmployee.firstName() + "+" +
                updatedEmployee.lastName());

        employeeRepository.save(employeeToBeUpdated);
        return employeeToBeUpdated;
    }

    // DELETE

    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    // Avatar upload

    public String uploadAvatar(long employeeId, MultipartFile file) throws IOException {

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String imageUrl = (String) uploadResult.get("url");

        return imageUrl;
    }
}
