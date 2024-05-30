package it.epicode.U4_S6_L5_weekly_project.entities;

import it.epicode.U4_S6_L5_weekly_project.entities.enums.DeviceType;
import it.epicode.U4_S6_L5_weekly_project.entities.enums.DeviceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devices")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")

public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;
}
