package com.app.repository;

import com.app.entities.Preference;
import com.app.entities.PreferenceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface PreferenceRepository extends JpaRepository<Preference, PreferenceId> {

}