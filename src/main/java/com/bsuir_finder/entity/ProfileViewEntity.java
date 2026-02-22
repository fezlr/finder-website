package com.bsuir_finder.entity;

import com.bsuir_finder.dto.enums.Reaction;
import jakarta.persistence.*;

@Table(name = "profile_views")
@Entity
public class ProfileViewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "viewer_id", nullable = false)
    private Long viewerId;

    @Column(name = "viewed_profile_id", nullable = false)
    private Long viewedProfileId;

    @Column(name = "reaction", nullable = false)
    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    public ProfileViewEntity(Long id, Long viewerId, Long viewedProfileId, Reaction reaction) {
        this.id = id;
        this.viewerId = viewerId;
        this.viewedProfileId = viewedProfileId;
        this.reaction = reaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getViewerId() {
        return viewerId;
    }

    public void setViewerId(Long viewerId) {
        this.viewerId = viewerId;
    }

    public Long getViewedProfileId() {
        return viewedProfileId;
    }

    public void setViewedProfileId(Long viewedProfileId) {
        this.viewedProfileId = viewedProfileId;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }
}
