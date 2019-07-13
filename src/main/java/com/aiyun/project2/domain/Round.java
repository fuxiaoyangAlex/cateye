package com.aiyun.project2.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 放映安排表
 */
@ApiModel(description = "放映安排表")
@Entity
@Table(name = "round")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Round implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time")
    private ZonedDateTime time;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JsonIgnoreProperties("rounds")
    private Cinema cinemaId;

    @ManyToOne
    @JsonIgnoreProperties("rounds")
    private Movie movieId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Round time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Integer getDuration() {
        return duration;
    }

    public Round duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public Round price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Cinema getCinemaId() {
        return cinemaId;
    }

    public Round cinemaId(Cinema cinema) {
        this.cinemaId = cinema;
        return this;
    }

    public void setCinemaId(Cinema cinema) {
        this.cinemaId = cinema;
    }

    public Movie getMovieId() {
        return movieId;
    }

    public Round movieId(Movie movie) {
        this.movieId = movie;
        return this;
    }

    public void setMovieId(Movie movie) {
        this.movieId = movie;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Round)) {
            return false;
        }
        return id != null && id.equals(((Round) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Round{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", duration=" + getDuration() +
            ", price=" + getPrice() +
            "}";
    }
}
