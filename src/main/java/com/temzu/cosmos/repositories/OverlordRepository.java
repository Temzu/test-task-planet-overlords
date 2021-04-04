package com.temzu.cosmos.repositories;

import com.temzu.cosmos.model.entities.Overlord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverlordRepository extends JpaRepository<Overlord, Long> {
    Page<Overlord> findByPlanetsNull(Pageable pageable);
}
