package com.semiclone.springboot.domain.timetable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long>{

    /* 더미데이터 INSERT할 때 중복값 체크용 쿼리 */
    List<TimeTable> findByScreenIdAndMovieIdAndTurningNoAndDateAndStartTimeAndEndTimeAndEmptySeat(
            Long screenId, Long movieId, int turningNo, Long date, Long startTime, Long endTime, int emptySeat);

  

}//end of interface