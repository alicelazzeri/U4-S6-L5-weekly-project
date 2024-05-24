package it.epicode.U4_S6_L5_weekly_project.repositories;

import it.epicode.U4_S6_L5_weekly_project.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>, PagingAndSortingRepository<Device, Long> {

}
