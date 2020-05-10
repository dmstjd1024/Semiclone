package com.semiclone.springboot.domain.timetable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long>{

    /* 더미데이터 INSERT할 때 중복값 체크용 쿼리 */
    List<TimeTable> findByScreenIdAndMovieIdAndTurningNoAndDateAndStartTimeAndEndTime(
            Long screenId, Long movieId, int turningNo, Long date, Long startTime, Long endTime);

    List<TimeTable> findByScreenId(Long screenId);
  
    @Query("SELECT date FROM TimeTable GROUP BY date")
    List<Long> findDate();

    @Query("SELECT date FROM TimeTable WHERE movieId = ?1 GROUP BY date")
    List<Long> findDateByMovieId(Long movieId);

    @Query("SELECT screenId FROM TimeTable WHERE movieId = ?1 GROUP BY screenId")
    List<Long> findScreenIdByMovieId(Long movieId);

    @Query("SELECT movieId FROM TimeTable WHERE screenId = ?1 GROUP BY movieId")
    List<Long> findMovieIdByScreenId(Long screenId);

    @Query("SELECT movieId FROM TimeTable WHERE date = ?1 GROUP BY movieId")
    List<Long> findMovieIdByDate(Long date);

    @Query("SELECT screenId FROM TimeTable WHERE date = ?1 GROUP BY screenId")
    List<Long> findScreenIdByDate(Long date);

    @Query("SELECT date FROM TimeTable WHERE screenId = ?1 AND movieId = ?2 GROUP BY date")
    List<Long> findDateByScreenIdAndMovieId(Long screenId, Long movieId);

    @Query("SELECT screenId FROM TimeTable WHERE movieId = ?1 AND date = ?2")
    List<Long> findScreenIdByMovieIdAndDate(Long movieId, Long date);

    @Query("SELECT movieId FROM TimeTable WHERE screenId = ?1 AND date = ?2")
    List<Long> findMovieIdByScreenIdAndDate(Long screenId, Long date);

    @Query("SELECT t FROM TimeTable t WHERE movieId = ?1 AND screenId = ?2 AND date = ?3")
    List<TimeTable> findTimeTableByMovieIdAndScreenIdAndDate(Long movieId, Long screenId, Long date);

    TimeTable findOneById(Long timeTableId);

    @Query("SELECT movieId FROM TimeTable WHERE cinemaId = ?1 GROUP BY movieId")
    List<Long> findMovieIdByCinemaId(Long cinemaId);

    @Query("SELECT date FROM TimeTable WHERE cinemaId = ?1 GROUP BY date")
    List<Long> findDateByCinemaId(Long cinemaId);

    @Query("SELECT date FROM TimeTable WHERE cinemaId = ?1 AND movieId = ?2 GROUP BY date")
    List<Long> findDateByCinemaIdAndMovieId(Long cinemaId, Long movieId);

    @Query("SELECT movieId FROM TimeTable WHERE cinemaId = ?1 AND date = ?2 GROUP BY movieId")
    List<Long> findMovieIdByCinemaIdAndDate(Long cinemaId, Long date);

    @Query("SELECT cinemaId FROM TimeTable WHERE movieId = ?1 GROUP BY cinemaId")
    List<Long> findCinemaIdByMovieId(Long movieId);

    @Query("SELECT cinemaId FROM TimeTable WHERE date = ?1 GROUP BY cinemaId")
    List<Long> findCinemaIdIdByDate(Long date);

    @Query("SELECT cinemaId FROM TimeTable WHERE movieId = ?1 AND date = ?2 GROUP BY cinemaId")
    List<Long> findCinemaIdIdByMovieIdAndDate(Long movieId, Long date);

    @Query("SELECT movieId FROM TimeTable WHERE movieId = ?1")
    List<Long> findMovieIdByMovieDi(Long movieId);

    TimeTable findFirstByCinemaIdOrderByDate(Long cinemaId);

    @Query("SELECT screenId FROM TimeTable WHERE id = ?1")
    Long findScreenIdById(Long timeTableId);

}//end of interface