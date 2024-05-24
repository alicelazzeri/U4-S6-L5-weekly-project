package it.epicode.U4_S6_L5_weekly_project.controllers;

import it.epicode.U4_S6_L5_weekly_project.entities.Employee;
import it.epicode.U4_S6_L5_weekly_project.exceptions.BadRequestException;
import it.epicode.U4_S6_L5_weekly_project.exceptions.NoContentException;
import it.epicode.U4_S6_L5_weekly_project.exceptions.NotFoundException;
import it.epicode.U4_S6_L5_weekly_project.records.EmployeePayloadDto;
import it.epicode.U4_S6_L5_weekly_project.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // GET (http://localhost:8080/api/employees)

    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy) {
        Page<Employee> employees = employeeService.getAllEmployees(page, size, sortBy);

        if (employees.isEmpty()) {
            throw new NoContentException("The list of employees is empty.");
        } else {
            ResponseEntity<Page<Employee>> responseEntity = new ResponseEntity<>(employees, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET id (http://localhost:8080/api/employees/{id})

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            throw new NotFoundException(id);
        } else {
            ResponseEntity<Employee> responseEntity = new ResponseEntity<>(employee, HttpStatus.OK);
            return responseEntity;
        }
    }

    // POST (http://localhost:8080/api/employees) + payload

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody @Validated EmployeePayloadDto employeePayload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            Employee employeeToBeSaved = Employee.builder()
                    .withUsername(employeePayload.username())
                    .withFirstName(employeePayload.firstName())
                    .withLastName(employeePayload.lastName())
                    .withEmail(employeePayload.email())
                    .withAvatar(employeePayload.avatar())
                    .build();

            Employee savedEmployee = employeeService.saveEmployee(employeeToBeSaved);
            ResponseEntity<Employee> responseEntity = new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
            return responseEntity;
        }
    }

    // PUT (http://localhost:8080/api/employees/{id}) + payload

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody @Validated EmployeePayloadDto updatedEmployeePayload) {
        Employee employeeToBeUpdated = employeeService.getEmployeeById(id);
        if ( employeeToBeUpdated == null) {
            throw new NotFoundException(id);
        }
        employeeToBeUpdated.setUsername(updatedEmployeePayload.username());
        employeeToBeUpdated.setFirstName(updatedEmployeePayload.firstName());
        employeeToBeUpdated.setLastName(updatedEmployeePayload.lastName());
        employeeToBeUpdated.setEmail(updatedEmployeePayload.email());
        employeeToBeUpdated.setAvatar(updatedEmployeePayload.avatar());

        Employee updatedEmployee = employeeService.updateEmployee(id, updatedEmployeePayload);
        ResponseEntity<Employee> responseEntity = new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        return responseEntity;
    }

    // DELETE (http://localhost:8080/api/employees/{id})

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }

}
