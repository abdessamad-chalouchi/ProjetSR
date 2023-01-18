package com.app.repository;

import com.app.entities.Carnet;
import com.app.entities.CarnetId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CarnetRepository extends JpaRepository<Carnet, CarnetId> {
}
