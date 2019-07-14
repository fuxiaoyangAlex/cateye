package com.aiyun.project2.service;

import com.aiyun.project2.domain.Movie;

import com.aiyun.project2.service.dto.MoiveCou1;
import com.aiyun.project2.service.dto.MoiveCou2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Movie}.
 */
public interface MovieService {

    /**
     * Save a movie.
     *
     * @param movie the entity to save.
     * @return the persisted entity.
     */
    Movie save(Movie movie);

    /**
     * Get all the movies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Movie> findAll(Pageable pageable);


    /**
     * Get the "id" movie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Movie> findOne(Long id);

    /**
     * Delete the "id" movie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * 统计各地区电影数据
     * @return
     * @throws Exception
     */
    List<MoiveCou1> findAllMovieCon1(String cityName) throws Exception;
    /**
     * 统计各地区电影数据(电影排片数量前五名)
     * @return
     * @throws Exception
     */
    List<MoiveCou2> findAllMovieCon2(String cityName) throws Exception;
}
