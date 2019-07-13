package com.aiyun.project2.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * 演出表
 */
@ApiModel(description = "演出表")
@Entity
@Table(name = "play")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Play implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("plays")
    private Movie movieId;

    @ManyToOne
    @JsonIgnoreProperties("plays")
    private Actor actorId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovieId() {
        return movieId;
    }

    public Play movieId(Movie movie) {
        this.movieId = movie;
        return this;
    }

    public void setMovieId(Movie movie) {
        this.movieId = movie;
    }

    public Actor getActorId() {
        return actorId;
    }

    public Play actorId(Actor actor) {
        this.actorId = actor;
        return this;
    }

    public void setActorId(Actor actor) {
        this.actorId = actor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Play)) {
            return false;
        }
        return id != null && id.equals(((Play) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Play{" +
            "id=" + getId() +
            "}";
    }
}
