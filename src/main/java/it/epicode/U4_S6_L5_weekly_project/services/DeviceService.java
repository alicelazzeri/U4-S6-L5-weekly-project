package it.epicode.U4_S6_L5_weekly_project.services;

import it.epicode.U4_S6_L5_weekly_project.entities.Device;
import it.epicode.U4_S6_L5_weekly_project.entities.Employee;
import it.epicode.U4_S6_L5_weekly_project.exceptions.NotFoundException;
import it.epicode.U4_S6_L5_weekly_project.records.DevicePayloadDto;
import it.epicode.U4_S6_L5_weekly_project.repositories.DeviceRepository;
import it.epicode.U4_S6_L5_weekly_project.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // GET all

    public Page<Device> getAllDevices(int page, int size, String sortBy) {
        if(size < 0) {
            size = 10;
        }
        if (size > 100) {
            size = 100;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return deviceRepository.findAll(pageable);
    }

    // GET id

    public Device getDeviceById(long id) {
        return deviceRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    // POST

    public Device saveDevice(Device device) {
        deviceRepository.save(device);
        return device;
    }

    // PUT

    public Device updateDevice(long id, DevicePayloadDto updatedDevice) {
        Device deviceToBeUpdated = deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        deviceToBeUpdated.setDeviceType(updatedDevice.deviceType());
        deviceToBeUpdated.setDeviceStatus(updatedDevice.deviceStatus());

        Employee newEmployee = employeeRepository.findById(updatedDevice.employeeId())
                .orElseThrow(() -> new NotFoundException(updatedDevice.employeeId()));
        deviceToBeUpdated.setEmployee(newEmployee);

        deviceRepository.save(deviceToBeUpdated);
        return deviceToBeUpdated;
    }

    public void deleteDevice(long id) {
        deviceRepository.deleteById(id);
    }

    }







