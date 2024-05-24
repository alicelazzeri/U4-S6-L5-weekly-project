package it.epicode.U4_S6_L5_weekly_project.runners;

import com.github.javafaker.Faker;
import it.epicode.U4_S6_L5_weekly_project.entities.Device;
import it.epicode.U4_S6_L5_weekly_project.entities.Employee;
import it.epicode.U4_S6_L5_weekly_project.entities.enums.DeviceStatus;
import it.epicode.U4_S6_L5_weekly_project.entities.enums.DeviceType;
import it.epicode.U4_S6_L5_weekly_project.services.DeviceService;
import it.epicode.U4_S6_L5_weekly_project.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CompanyDevicesRunner implements CommandLineRunner {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DeviceService deviceService;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        for (int i = 0; i < 50; i++) {
            Employee employee = Employee.builder()
                    .withUsername(faker.name().username())
                    .withFirstName(faker.name().firstName())
                    .withLastName(faker.name().lastName())
                    .withEmail(faker.internet().emailAddress())
                    .withAvatar(faker.internet().avatar())
                    .build();

            employeeService.saveEmployee(employee);
        }

        // Creare e salvare istanze di Device
        for (int i = 0; i < 50; i++) {
            Device device = Device.builder()
                    .withDeviceType(DeviceType.values()[faker.number().numberBetween(0, DeviceType.values().length)])
                    .withDeviceStatus(DeviceStatus.values()[faker.number().numberBetween(0, DeviceStatus.values().length)])
                    .build();

            deviceService.saveDevice(device);
        }
    }
}
