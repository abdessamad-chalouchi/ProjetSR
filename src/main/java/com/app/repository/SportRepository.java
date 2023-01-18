package com.app.repository;

import com.app.entities.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SportRepository extends JpaRepository<Sport, Integer> {
}
