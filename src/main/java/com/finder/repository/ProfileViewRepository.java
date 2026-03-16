package com.finder.repository;

import com.finder.model.domain.ProfileEntity;
import com.finder.model.domain.ProfileViewEntity;
import org.springframework.data.domain.Page;
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

    @Query("""
SELECT p
FROM ProfileEntity p
WHERE p.id <> :myId
AND p.formStatus = 'ACTIVE'
AND EXISTS (
    SELECT 1
    FROM ProfileViewEntity v1
    WHERE v1.viewerId = :myId
    AND v1.viewedProfileId = p.id
    AND v1.reaction = 'LIKE'
)
AND EXISTS (
    SELECT 1
    FROM ProfileViewEntity v2
    WHERE v2.viewerId = p.id
    AND v2.viewedProfileId = :myId
    AND v2.reaction = 'LIKE'
)
""")
    Page<ProfileEntity> findMutualLikes(@Param("myId") Long myId, Pageable pageable);

    boolean existsByViewerIdAndViewedProfileId(Long viewerId, Long viewedProfileId);
}
