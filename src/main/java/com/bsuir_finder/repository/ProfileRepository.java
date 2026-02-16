package com.bsuir_finder.repository;

import com.bsuir_finder.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    List<ProfileEntity> findAllByIsFullTrueAndIdNot(Long id);
}
