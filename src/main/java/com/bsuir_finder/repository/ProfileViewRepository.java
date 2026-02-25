package com.bsuir_finder.repository;

import com.bsuir_finder.entity.ProfileEntity;
import com.bsuir_finder.entity.ProfileViewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileViewRepository extends JpaRepository<ProfileViewEntity, Long> {

    @Query("""
    SELECT p FROM ProfileEntity p
    WHERE p.id != :currentId
    AND NOT EXISTS (
        SELECT 1 FROM ProfileViewEntity pv
        WHERE pv.viewerId = :currentId
        AND pv.viewedProfileId = p.id
    )
    ORDER BY function('RANDOM')
""")
    List<ProfileEntity> findRandomUnreactedWithId(
            @Param("currentId") Long currentId,
            Pageable pageable);

    boolean existsByViewerIdAndViewedProfileId(Long viewerId, Long viewedProfileId);
}
