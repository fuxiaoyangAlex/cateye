package com.aiyun.project2.service.dto;

public class MoiveCou2 {
    Integer roundCou;

    @Override
    public String toString() {
        return "MoiveCou2{" +
            "roundCou=" + roundCou +
            ", movie_id_id=" + movie_id_id +
            ", movieName='" + movieName + '\'' +
            '}';
    }

    public Integer getRoundCou() {
        return roundCou;
    }

    public void setRoundCou(Integer roundCou) {
        this.roundCou = roundCou;
    }

    public Integer getMovie_id_id() {
        return movie_id_id;
    }

    public void setMovie_id_id(Integer movie_id_id) {
        this.movie_id_id = movie_id_id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    Integer movie_id_id;

    public MoiveCou2() {
    }

    String movieName;
}
