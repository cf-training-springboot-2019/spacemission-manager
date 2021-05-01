package com.springboot.training.spaceover.spacemission.manager.repository;

import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceMissionRepository extends JpaRepository<SpaceMission, Long> {
}
