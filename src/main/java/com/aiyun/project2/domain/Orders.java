package com.aiyun.project2.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * 订单表
 */
@ApiModel(description = "订单表")
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Movie movieId;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Customer customerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public Orders count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Movie getMovieId() {
        return movieId;
    }

    public Orders movieId(Movie movie) {
        this.movieId = movie;
        return this;
    }

    public void setMovieId(Movie movie) {
        this.movieId = movie;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public Orders customerId(Customer customer) {
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
        if (!(o instanceof Orders)) {
            return false;
        }
        return id != null && id.equals(((Orders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", count=" + getCount() +
            "}";
    }
}
