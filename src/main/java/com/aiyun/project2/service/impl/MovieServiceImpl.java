package com.aiyun.project2.service.impl;

import com.aiyun.project2.service.MovieService;
import com.aiyun.project2.domain.Movie;
import com.aiyun.project2.repository.MovieRepository;
import com.aiyun.project2.service.dto.MoiveCou1;
import com.aiyun.project2.service.dto.MoiveCou2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.util.TypeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Movie}.
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;

    String [] proAarry= new String []{"江苏省","安徽省","浙江省","江苏","安徽","浙江"};

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Autowired
    JdbcTemplate jdbcTemplate;


    /**
     * Save a movie.
     *
     * @param movie the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Movie save(Movie movie) {
        log.debug("Request to save Movie : {}", movie);
        return movieRepository.save(movie);
    }

    /**
     * Get all the movies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Movie> findAll(Pageable pageable) {
        log.debug("Request to get all Movies");
        return movieRepository.findAll(pageable);
    }


    /**
     * Get one movie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Movie> findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        return movieRepository.findById(id);
    }

    /**
     * Delete the movie by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.deleteById(id);
    }
    /**
     * 统计各地区电影数据
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<MoiveCou1> findAllMovieCon1(String cityName) throws Exception {
        boolean isPro=false;
        String proName=null;
        List<Map<String, Object>> movieCou1=null;
        for (String s : proAarry) {
            if(s.equals(cityName)){
                isPro=true;
                break;
            }
        }

        if(isPro){
            proName=cityName.substring(0,2)+"省";
        }else {
            cityName=cityName.substring(0,2)+"市";
        }

        String sql = "SELECT *FROM\n" +
            "(SELECT c1.cityName,c1.cinCou,c2.localCtiyCou FROM (" +
            "SELECT b.con cinCou,c.NAME cityName FROM (" +
            "SELECT COUNT(id) con,city_id_id FROM cinema GROUP BY city_id_id) b RIGHT JOIN city c ON b.city_id_id=c.id) c1 LEFT JOIN (" +
            "SELECT SUM(b1.影院放映电影数) localCtiyCou,city_name FROM (" +
            "SELECT a2.NAME,a1.影院放映电影数,a3.NAME city_name,a3.id FROM (" +
            "SELECT COUNT(movie_id_id) 影院放映电影数,cinema_id_id FROM round GROUP BY cinema_id_id) a1,cinema a2,city a3 WHERE a1.cinema_id_id=a2.id AND a3.id=a2.city_id_id) b1 GROUP BY b1.city_name) c2 ON c1.cityName=c2.city_name) tb1 " +
            "WHERE tb1.cityName="+"\""+cityName+"\"";
        String sql1="SELECT c.`name` cityName,tb6.cinCou,tb6.localCtiyCou FROM (" +
            "SELECT SUM(tb5.cinCou) cinCou,SUM(tb5.localCtiyCou) localCtiyCou,tb5.proid proid FROM (" +
            "SELECT tb1.*,tb2.proid FROM (" +
            "SELECT c1.cityName,c1.cinCou,c2.localCtiyCou FROM (" +
            "SELECT b.con cinCou,c.NAME cityName FROM (" +
            "SELECT COUNT(id) con,city_id_id FROM cinema GROUP BY city_id_id) b RIGHT JOIN city c ON b.city_id_id=c.id) c1 LEFT JOIN (" +
            "SELECT SUM(b1.影院放映电影数) localCtiyCou,city_name FROM (" +
            "SELECT a2.NAME,a1.影院放映电影数,a3.NAME city_name,a3.id FROM (" +
            "SELECT COUNT(movie_id_id) 影院放映电影数,cinema_id_id FROM round GROUP BY cinema_id_id) a1,cinema a2,city a3 WHERE a1.cinema_id_id=a2.id AND a3.id=a2.city_id_id) b1 GROUP BY b1.city_name) c2 ON c1.cityName=c2.city_name) tb1,(" +
            "SELECT city.*,tb4.id proid FROM city,(" +
            "SELECT id FROM city WHERE `name`="+"\""+proName+"\""+") tb4 WHERE parent_id=tb4.id) tb2 WHERE tb1.cityName=tb2.NAME) tb5 GROUP BY tb5.proid) tb6,city c WHERE c.id=tb6.proid";

        if(isPro){
            movieCou1 = jdbcTemplate.queryForList(sql1);
        }else {
            movieCou1 = jdbcTemplate.queryForList(sql);
        }
        List<MoiveCou1> moiveCou1s = new ArrayList<>();
        if (movieCou1 == null || movieCou1.size() == 0) {
            throw new Exception("没有数据");

        } else {
            for (int i = 0; i < movieCou1.size(); i++) {
                System.out.println("数据" + movieCou1.toString());
                MoiveCou1 moiveCou1En = new MoiveCou1();
                moiveCou1En.setCityName(TypeUtils.castToString(movieCou1.get(i).get("cityName")));
                if (movieCou1.get(i).get("cinCou") == null) {
                    moiveCou1En.setCinCou(0);
                } else {
                    moiveCou1En.setCinCou(TypeUtils.castToInt(movieCou1.get(i).get("cinCou")));
                }
                if (movieCou1.get(i).get("localctiyCou") == null) {
                    moiveCou1En.setLocalmovieCou(0);
                } else {
                    moiveCou1En.setLocalmovieCou(TypeUtils.castToInt(movieCou1.get(i).get("localctiyCou")));
                }
                moiveCou1s.add(moiveCou1En);
            }
            return moiveCou1s;

        }
    }

    @Override
    public List<MoiveCou2> findAllMovieCon2(String cityName) throws Exception {
        boolean isPro=false;
        String proName=null;
        List<Map<String, Object>> movieCou2=null;
        for (String s : proAarry) {
            if(s.equals(cityName)){
                isPro=true;
                break;
            }
        }

        if(isPro){
            proName=cityName.substring(0,2)+"省";
        }else {
            cityName=cityName.substring(0,2)+"市";
        }

        String sql = "SELECT tb4.roundCou,tb4.movie_id_id,tb5.NAME movieName FROM (" +
            "SELECT COUNT(cinema_id_id) roundCou,movie_id_id FROM (" +
            "SELECT tb2.*FROM (" +
            "SELECT a.id FROM cinema a JOIN city b ON a.city_id_id=b.id AND b.id=(" +
            "SELECT id FROM city WHERE `name`="+"\""+cityName+"\""+")) tb1 JOIN round tb2 ON tb1.id=tb2.cinema_id_id) tb3 GROUP BY movie_id_id) tb4,movie tb5 WHERE tb4.movie_id_id=tb5.id ORDER BY tb4.roundCou DESC LIMIT 5";
        if(isPro){
            movieCou2 = jdbcTemplate.queryForList(sql);
        }else {
            movieCou2 = jdbcTemplate.queryForList(sql);
        }
        List<MoiveCou2> moiveCou2s = new ArrayList<>();
        if (movieCou2 == null || movieCou2.size() == 0) {
            throw new Exception("没有数据");

        } else {
            for (int i = 0; i < movieCou2.size(); i++) {
                System.out.println("数据" + movieCou2.toString());
                MoiveCou2 moiveCou2En = new MoiveCou2();
                moiveCou2En.setMovieName(TypeUtils.castToString(movieCou2.get(i).get("movieName")));
                if (movieCou2.get(i).get("movie_id_id") == null) {
                    moiveCou2En.setMovie_id_id(0);
                } else {
                    moiveCou2En.setMovie_id_id(TypeUtils.castToInt(movieCou2.get(i).get("movie_id_id")));
                }
                if (movieCou2.get(i).get("roundCou") == null) {
                    moiveCou2En.setRoundCou(0);
                } else {
                    moiveCou2En.setRoundCou(TypeUtils.castToInt(movieCou2.get(i).get("roundCou")));
                }
                moiveCou2s.add(moiveCou2En);
            }
            return moiveCou2s;

        }
    }
}
