package com.springboot.training.spaceover.spacemission.manager.repository;

import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceShipTotalRevenue;
import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceMissionRepository extends JpaRepository<SpaceMission, Long> {


    @Query("SELECT m.spaceShipId AS spaceShipId, SUM(m.revenue) AS totalRevenue " +
            "FROM SpaceMission AS m " +
            "WHERE m.status=3 " +
            "GROUP BY m.spaceShipId")
    public List<SpaceShipTotalRevenue> calculateSpaceShipsTotalRevenue();

}
