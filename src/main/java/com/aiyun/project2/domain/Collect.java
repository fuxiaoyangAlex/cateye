package com.aiyun.project2.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 收藏表
 */
@ApiModel(description = "收藏表")
@Entity
@Table(name = "collect")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties("collects")
    private Movie moveId;

    @ManyToOne
    @JsonIgnoreProperties("collects")
    private Customer customerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Collect date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Movie getMoveId() {
        return moveId;
    }

    public Collect moveId(Movie movie) {
        this.moveId = movie;
        return this;
    }

    public void setMoveId(Movie movie) {
        this.moveId = movie;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public Collect customerId(Customer customer) {
        this.customerId = customer;
        return this;
    }

    public void setCustomerId(Customer customer) {
        this.customerId = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collect)) {
            return false;
        }
        return id != null && id.equals(((Collect) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Collect{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
