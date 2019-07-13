package com.aiyun.project2.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * 电影表
 */
@ApiModel(description = "电影表")
@Entity
@Table(name = "movie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "release_day")
    private Integer releaseDay;

    @Column(name = "image")
    private String image;

    @Column(name = "brief")
    private String brief;

    @ManyToOne
    @JsonIgnoreProperties("movies")
    private Director directorId;

    @ManyToOne
    @JsonIgnoreProperties("movies")
    private Variety varietyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Movie name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseDay() {
        return releaseDay;
    }

    public Movie releaseDay(Integer releaseDay) {
        this.releaseDay = releaseDay;
        return this;
    }

    public void setReleaseDay(Integer releaseDay) {
        this.releaseDay = releaseDay;
    }

    public String getImage() {
        return image;
    }

    public Movie image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrief() {
        return brief;
    }

    public Movie brief(String brief) {
        this.brief = brief;
        return this;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Director getDirectorId() {
        return directorId;
    }

    public Movie directorId(Director director) {
        this.directorId = director;
        return this;
    }

    public void setDirectorId(Director director) {
        this.directorId = director;
    }

    public Variety getVarietyId() {
        return varietyId;
    }

    public Movie varietyId(Variety variety) {
        this.varietyId = variety;
        return this;
    }

    public void setVarietyId(Variety variety) {
        this.varietyId = variety;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        return id != null && id.equals(((Movie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", releaseDay=" + getReleaseDay() +
            ", image='" + getImage() + "'" +
            ", brief='" + getBrief() + "'" +
            "}";
    }
}
