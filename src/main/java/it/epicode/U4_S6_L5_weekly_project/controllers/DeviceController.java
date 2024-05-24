package it.epicode.U4_S6_L5_weekly_project.controllers;

import it.epicode.U4_S6_L5_weekly_project.entities.Device;
import it.epicode.U4_S6_L5_weekly_project.entities.Employee;
import it.epicode.U4_S6_L5_weekly_project.exceptions.BadRequestException;
import it.epicode.U4_S6_L5_weekly_project.exceptions.NoContentException;
import it.epicode.U4_S6_L5_weekly_project.exceptions.NotFoundException;
import it.epicode.U4_S6_L5_weekly_project.records.AssignedDeviceToEmployeePayloadDto;
import it.epicode.U4_S6_L5_weekly_project.records.DevicePayloadDto;
import it.epicode.U4_S6_L5_weekly_project.services.DeviceService;
import it.epicode.U4_S6_L5_weekly_project.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")

public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private EmployeeService employeeService;


    // GET (http://localhost:8080/api/devices)

    @GetMapping
    public ResponseEntity<Page<Device>> getAllDevices(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "id") String sortBy) {
        Page<Device> devices = deviceService.getAllDevices(page, size, sortBy);

        if (devices.isEmpty()) {
            throw new NoContentException("The list of devices is empty.");
        } else {
            ResponseEntity<Page<Device>> responseEntity = new ResponseEntity<>(devices, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET id (http://localhost:8080/api/devices/{id})

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable long id) {
        Device device = deviceService.getDeviceById(id);
        if (device == null) {
            throw new NotFoundException(id);
        } else {
            ResponseEntity<Device> responseEntity = new ResponseEntity<>(device, HttpStatus.OK);
            return responseEntity;
        }
    }

    // POST (http://localhost:8080/api/devices) + payload

    @PostMapping
    public ResponseEntity<Device> saveDevice(@RequestBody @Validated DevicePayloadDto devicePayload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            Device deviceToBeSaved = Device.builder()
                    .withDeviceStatus(devicePayload.deviceStatus())
                    .withDeviceType(devicePayload.deviceType())
                    .build();
            Employee employee = employeeService.getEmployeeById(devicePayload.employeeId());

            if (employee == null) {
                throw new NotFoundException("Employee with ID " + devicePayload.employeeId() + " not found.");
            } else {
                deviceToBeSaved.setEmployee(employee);
            }
            Device savedDevice = deviceService.saveDevice(deviceToBeSaved);
            ResponseEntity<Device> responseEntity = new ResponseEntity<>(savedDevice, HttpStatus.CREATED);
            return responseEntity;
        }
    }

    // PUT (http://localhost:8080/api/devices/{id}) + payload

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable long id, @RequestBody @Validated DevicePayloadDto updatedDevicePayload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        Device deviceToBeUpdated = deviceService.getDeviceById(id);
        if (deviceToBeUpdated == null) {
            throw new NotFoundException(id);
        }
        deviceToBeUpdated.setDeviceType(updatedDevicePayload.deviceType());
        deviceToBeUpdated.setDeviceStatus(updatedDevicePayload.deviceStatus());

        Employee employee = employeeService.getEmployeeById(updatedDevicePayload.employeeId());

        if (employee == null) {
            throw new NotFoundException("Employee with ID " + updatedDevicePayload.employeeId() + " not found.");
        } else {
            deviceToBeUpdated.setEmployee(employee);
        }
        Device updatedDevice = deviceService.updateDevice(id, updatedDevicePayload);
        ResponseEntity<Device> responseEntity = new ResponseEntity<>(updatedDevice, HttpStatus.OK);
        return responseEntity;
    }

    // DELETE (http://localhost:8080/api/devices/{id})

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable long id) {
        deviceService.deleteDevice(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }

    // Custom API to assign device to employee (http://localhost:8080/api/assign?deviceId={deviceId}&employeeId={employeeId})

    @PostMapping("/assign")
    public ResponseEntity<AssignedDeviceToEmployeePayloadDto> assignDeviceToEmployee(@RequestParam long deviceId, @RequestParam long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            throw new NotFoundException(employeeId);
        }

        Device deviceToBeAssigned = deviceService.assignDeviceToEmployee(deviceId, employee);
        if (deviceToBeAssigned == null) {
            throw new NotFoundException(deviceId);
        }

        AssignedDeviceToEmployeePayloadDto payload = new AssignedDeviceToEmployeePayloadDto(
                deviceToBeAssigned.getId(),
                deviceToBeAssigned.getDeviceType(),
                deviceToBeAssigned.getDeviceStatus(),
                employee.getId(),
                employee.getUsername(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getAvatar());

        ResponseEntity<AssignedDeviceToEmployeePayloadDto> responseEntity = new ResponseEntity<>(payload, HttpStatus.CREATED);
        return responseEntity;
    }
}
