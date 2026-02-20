package com.bsuir_finder.entity;

import com.bsuir_finder.dto.enums.Reaction;
import jakarta.persistence.*;

@Entity
@Table(name = "profile_views")
public class ProfileViewEntity {

    //html ->

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "viewer")
    private UserEntity viewer;

    @ManyToOne
    @JoinColumn(name = "viewed_profile")
    private ProfileEntity viewedProfile;

    @ManyToOne
    @JoinColumn(name = "reaction")
    private Reaction reaction;


    public ProfileViewEntity(Long id, UserEntity viewer, ProfileEntity viewedProfile, Reaction reaction) {
        this.id = id;
        this.viewer = viewer;
        this.viewedProfile = viewedProfile;
        this.reaction = reaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getViewer() {
        return viewer;
    }

    public void setViewer(UserEntity viewer) {
        this.viewer = viewer;
    }

    public ProfileEntity getViewedProfile() {
        return viewedProfile;
    }

    public void setViewedProfile(ProfileEntity viewedProfile) {
        this.viewedProfile = viewedProfile;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }
}
