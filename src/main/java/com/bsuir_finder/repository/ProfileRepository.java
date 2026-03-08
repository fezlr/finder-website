package com.bsuir_finder.repository;

import com.bsuir_finder.model.dto.enums.FormStatus;
import com.bsuir_finder.model.domain.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    List<ProfileEntity> findAllByIsFullTrueAndIdNot(Long id);

    List<ProfileEntity> findAllByFormStatus(FormStatus formStatus);

    @Query(value = "SELECT * FROM profiles WHERE id != :id ORDER BY RANDOM() LIMIT 1",
            nativeQuery = true)
    ProfileEntity findRandomProfileWithIdNot(@Param("id") Long id);

//    @Query(value = "SELECT * FROM profiles WHERE id != :id AND gender != 'MALE' ORDER BY RANDOM() LIMIT 1",
//            nativeQuery = true)
//    ProfileEntity findRandomProfileWithGenderMaleAndIdNot(@Param("id") Long id);
//
//    @Query(value = "SELECT * FROM profiles WHERE id != :id AND gender = 'FEMALE' ORDER BY RANDOM() LIMIT 1")
//    ProfileEntity findProfileWithGenderFemaleAndIdNot(@Param("id") Long id);
}
