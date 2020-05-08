/*
 * 적용기준일 : 2020.04.25 16:40
 */
package com.semiclone.springboot.web;

import java.util.List;

import com.semiclone.springboot.domain.cinema.Cinema;
import com.semiclone.springboot.domain.cinema.CinemaRepository;
import com.semiclone.springboot.domain.movie.Movie;
import com.semiclone.springboot.domain.movie.MovieRepository;
import com.semiclone.springboot.domain.screen.Screen;
import com.semiclone.springboot.domain.screen.ScreenRepository;
import com.semiclone.springboot.domain.seat.Seat;
import com.semiclone.springboot.domain.seat.SeatRepository;
import com.semiclone.springboot.domain.ticket.Ticket;
import com.semiclone.springboot.domain.ticket.TicketRepository;
import com.semiclone.springboot.domain.timetable.TimeTable;
import com.semiclone.springboot.domain.timetable.TimeTableRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/*  FileName : onloadRestController.java
*   DB Table에 더미 데이터 셋팅
*   Return : Client에 DB Table 실제 데이터 출력
*/
@Api(tags = "INSERT DB Data")
@RestController
@RequestMapping("onload")
@RequiredArgsConstructor
public class OnloadRestController{

    //Field
    private final CinemaRepository cinemaRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final TimeTableRepository timeTableRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    //Method
    @ApiOperation(value = "CGV 크롤링해서 DB에 Data 저장")
    @RequestMapping(value = "/constructor", method = RequestMethod.GET)
    public String constructor() throws Throwable{
        
        System.out.println("-- INSERT DB Data Start... 1. Cinema, 2. Movie, 3. Screen, 4. TimeTable, 5. Seat, 6. Ticket");   

        /* Table Cinema에 Data가 없을 경우에만 실행  */
        if(cinemaRepository.count()==0){

            System.out.println("Table Cinema(극장) Data 넣는 중...");

            /* 서울 */
            cinemaRepository.save(new Cinema("서울", "강남"));
            cinemaRepository.save(new Cinema("서울", "강변"));
            cinemaRepository.save(new Cinema("서울", "건대입구"));
            cinemaRepository.save(new Cinema("서울", "구로"));
            cinemaRepository.save(new Cinema("서울", "대학로"));
            cinemaRepository.save(new Cinema("서울", "동대문"));
            cinemaRepository.save(new Cinema("서울", "등촌"));
            cinemaRepository.save(new Cinema("서울", "명동"));
            cinemaRepository.save(new Cinema("서울", "명동역 씨네라이브러리"));
            cinemaRepository.save(new Cinema("서울", "목동"));
            cinemaRepository.save(new Cinema("서울", "미아"));
            cinemaRepository.save(new Cinema("서울", "불광"));
            cinemaRepository.save(new Cinema("서울", "상봉"));
            cinemaRepository.save(new Cinema("서울", "성신여대입구"));
            cinemaRepository.save(new Cinema("서울", "송파"));
            cinemaRepository.save(new Cinema("서울", "수유"));
            cinemaRepository.save(new Cinema("서울", "신촌아트레온"));
            cinemaRepository.save(new Cinema("서울", "압구정"));
            cinemaRepository.save(new Cinema("서울", "여의도"));
            cinemaRepository.save(new Cinema("서울", "영등포"));
            cinemaRepository.save(new Cinema("서울", "왕십리"));
            cinemaRepository.save(new Cinema("서울", "용산아이파크몰"));
            cinemaRepository.save(new Cinema("서울", "중계"));
            cinemaRepository.save(new Cinema("서울", "천호"));
            cinemaRepository.save(new Cinema("서울", "청담씨네시티"));
            cinemaRepository.save(new Cinema("서울", "피카디리1958"));
            cinemaRepository.save(new Cinema("서울", "하계"));
            cinemaRepository.save(new Cinema("서울", "홍대"));
            cinemaRepository.save(new Cinema("서울", "CINE DE CHEF 압구정"));
            cinemaRepository.save(new Cinema("서울", "CINE DE CHEF 용산아이파크몰"));

            /* 경기 */
            cinemaRepository.save(new Cinema("경기", "경기광주"));
            cinemaRepository.save(new Cinema("경기", "광교"));
            cinemaRepository.save(new Cinema("경기", "광교상현"));
            cinemaRepository.save(new Cinema("경기", "구리"));
            cinemaRepository.save(new Cinema("경기", "김포운양"));
            cinemaRepository.save(new Cinema("경기", "김포풍무"));
            cinemaRepository.save(new Cinema("경기", "김포한강"));
            cinemaRepository.save(new Cinema("경기", "동백"));
            cinemaRepository.save(new Cinema("경기", "동수원"));
            cinemaRepository.save(new Cinema("경기", "동탄"));
            cinemaRepository.save(new Cinema("경기", "동탄역"));
            cinemaRepository.save(new Cinema("경기", "동탄호수공원"));
            cinemaRepository.save(new Cinema("경기", "배곧"));
            cinemaRepository.save(new Cinema("경기", "범계"));
            cinemaRepository.save(new Cinema("경기", "부천"));
            cinemaRepository.save(new Cinema("경기", "부천역"));
            cinemaRepository.save(new Cinema("경기", "부천옥길"));
            cinemaRepository.save(new Cinema("경기", "북수원"));
            cinemaRepository.save(new Cinema("경기", "산본"));
            cinemaRepository.save(new Cinema("경기", "서현"));
            cinemaRepository.save(new Cinema("경기", "성남모란"));
            cinemaRepository.save(new Cinema("경기", "소풍"));
            cinemaRepository.save(new Cinema("경기", "수원"));
            cinemaRepository.save(new Cinema("경기", "스타필드시티위례"));
            cinemaRepository.save(new Cinema("경기", "시흥"));
            cinemaRepository.save(new Cinema("경기", "안산"));
            cinemaRepository.save(new Cinema("경기", "안성"));
            cinemaRepository.save(new Cinema("경기", "야탑"));
            cinemaRepository.save(new Cinema("경기", "역곡"));
            cinemaRepository.save(new Cinema("경기", "오리"));
            cinemaRepository.save(new Cinema("경기", "오산"));
            cinemaRepository.save(new Cinema("경기", "오산중앙"));
            cinemaRepository.save(new Cinema("경기", "용인"));
            cinemaRepository.save(new Cinema("경기", "의정부"));
            cinemaRepository.save(new Cinema("경기", "의정부태흥"));
            cinemaRepository.save(new Cinema("경기", "이천"));
            cinemaRepository.save(new Cinema("경기", "일산"));
            cinemaRepository.save(new Cinema("경기", "죽전"));
            cinemaRepository.save(new Cinema("경기", "파주문산"));
            cinemaRepository.save(new Cinema("경기", "판교"));
            cinemaRepository.save(new Cinema("경기", "평촌"));
            cinemaRepository.save(new Cinema("경기", "평택"));
            cinemaRepository.save(new Cinema("경기", "평택소사"));
            cinemaRepository.save(new Cinema("경기", "화성봉담"));
            cinemaRepository.save(new Cinema("경기", "화정"));
            cinemaRepository.save(new Cinema("경기", "CINE KIDS 북수원"));

            /* 인천 */
            cinemaRepository.save(new Cinema("인천", "계양"));
            cinemaRepository.save(new Cinema("인천", "남주안"));
            cinemaRepository.save(new Cinema("인천", "부평"));
            cinemaRepository.save(new Cinema("인천", "연수역"));
            cinemaRepository.save(new Cinema("인천", "인천"));
            cinemaRepository.save(new Cinema("인천", "인천공항"));
            cinemaRepository.save(new Cinema("인천", "인천논현"));
            cinemaRepository.save(new Cinema("인천", "인천연수"));
            cinemaRepository.save(new Cinema("인천", "주안역"));
            cinemaRepository.save(new Cinema("인천", "청라"));

            /* 강원 */
            cinemaRepository.save(new Cinema("강원", "강릉"));
            cinemaRepository.save(new Cinema("강원", "원주"));
            cinemaRepository.save(new Cinema("강원", "인제"));
            cinemaRepository.save(new Cinema("강원", "춘천"));
            cinemaRepository.save(new Cinema("강원", "춘천명동"));

            /* 대전 */
            cinemaRepository.save(new Cinema("대전", "대전"));
            cinemaRepository.save(new Cinema("대전", "대전가수원"));
            cinemaRepository.save(new Cinema("대전", "대전가오"));
            cinemaRepository.save(new Cinema("대전", "대전탄방"));
            cinemaRepository.save(new Cinema("대전", "대전터미널"));
            cinemaRepository.save(new Cinema("대전", "유성노은"));
            
            /* 충청 */
            cinemaRepository.save(new Cinema("충청", "당진"));
            cinemaRepository.save(new Cinema("충청", "보령"));
            cinemaRepository.save(new Cinema("충청", "서산"));
            cinemaRepository.save(new Cinema("충청", "세종"));
            cinemaRepository.save(new Cinema("충청", "천안"));
            cinemaRepository.save(new Cinema("충청", "천안터미널"));
            cinemaRepository.save(new Cinema("충청", "천안펜타포트"));
            cinemaRepository.save(new Cinema("충청", "청주(서문)"));
            cinemaRepository.save(new Cinema("충청", "청주성안길"));
            cinemaRepository.save(new Cinema("충청", "청주율량"));
            cinemaRepository.save(new Cinema("충청", "청주지웰시티"));
            cinemaRepository.save(new Cinema("충청", "청주터미널"));
            cinemaRepository.save(new Cinema("충청", "충북혁신"));
            cinemaRepository.save(new Cinema("충청", "홍성"));

            /* 대구 */
            cinemaRepository.save(new Cinema("대구", "대구"));
            cinemaRepository.save(new Cinema("대구", "대구수성"));
            cinemaRepository.save(new Cinema("대구", "대구스타디움"));
            cinemaRepository.save(new Cinema("대구", "대구아카데미"));
            cinemaRepository.save(new Cinema("대구", "대구월성"));
            cinemaRepository.save(new Cinema("대구", "대구이시아"));
            cinemaRepository.save(new Cinema("대구", "대구칠곡"));
            cinemaRepository.save(new Cinema("대구", "대구한일"));
            cinemaRepository.save(new Cinema("대구", "대구현대"));

            /* 부산 */
            cinemaRepository.save(new Cinema("부산", "남포"));
            cinemaRepository.save(new Cinema("부산", "대연"));
            cinemaRepository.save(new Cinema("부산", "대한"));
            cinemaRepository.save(new Cinema("부산", "동래"));
            cinemaRepository.save(new Cinema("부산", "서면"));
            cinemaRepository.save(new Cinema("부산", "서면삼정타워"));
            cinemaRepository.save(new Cinema("부산", "센텀시티"));
            cinemaRepository.save(new Cinema("부산", "아시아드"));
            cinemaRepository.save(new Cinema("부산", "정관"));
            cinemaRepository.save(new Cinema("부산", "하단아트몰링"));
            cinemaRepository.save(new Cinema("부산", "해운대"));
            cinemaRepository.save(new Cinema("부산", "화명"));
            cinemaRepository.save(new Cinema("부산", "CINE DE CHEF 센텀시티"));

            /* 울산 */
            cinemaRepository.save(new Cinema("울산", "울산삼산"));
            cinemaRepository.save(new Cinema("울산", "울산신천"));
            cinemaRepository.save(new Cinema("울산", "울산진장"));

            /* 경상 */
            cinemaRepository.save(new Cinema("경상", "거제"));
            cinemaRepository.save(new Cinema("경상", "구미"));
            cinemaRepository.save(new Cinema("경상", "김천율곡"));
            cinemaRepository.save(new Cinema("경상", "김해"));
            cinemaRepository.save(new Cinema("경상", "김해율하"));
            cinemaRepository.save(new Cinema("경상", "김해장유"));
            cinemaRepository.save(new Cinema("경상", "마산"));
            cinemaRepository.save(new Cinema("경상", "북포항"));
            cinemaRepository.save(new Cinema("경상", "안동"));
            cinemaRepository.save(new Cinema("경상", "양산물금"));
            cinemaRepository.save(new Cinema("경상", "양산삼호"));
            cinemaRepository.save(new Cinema("경상", "창원"));
            cinemaRepository.save(new Cinema("경상", "창원더시티"));
            cinemaRepository.save(new Cinema("경상", "통영"));
            cinemaRepository.save(new Cinema("경상", "포항"));

            /* 광주 */
            cinemaRepository.save(new Cinema("광주", "광주금남로"));
            cinemaRepository.save(new Cinema("광주", "광주상무"));
            cinemaRepository.save(new Cinema("광주", "광주용봉"));
            cinemaRepository.save(new Cinema("광주", "광주첨단"));
            cinemaRepository.save(new Cinema("광주", "광주충장로"));
            cinemaRepository.save(new Cinema("광주", "광주터미널"));
            cinemaRepository.save(new Cinema("광주", "광주하남"));

            /* 전라 */
            cinemaRepository.save(new Cinema("전라", "광양"));
            cinemaRepository.save(new Cinema("전라", "광양 엘에프스퀘어"));
            cinemaRepository.save(new Cinema("전라", "군산"));
            cinemaRepository.save(new Cinema("전라", "나주"));
            cinemaRepository.save(new Cinema("전라", "목포"));
            cinemaRepository.save(new Cinema("전라", "목포평화광장"));
            cinemaRepository.save(new Cinema("전라", "서전주"));
            cinemaRepository.save(new Cinema("전라", "순천"));
            cinemaRepository.save(new Cinema("전라", "순천신대"));
            cinemaRepository.save(new Cinema("전라", "여수웅천"));
            cinemaRepository.save(new Cinema("전라", "익산"));
            cinemaRepository.save(new Cinema("전라", "전주고사"));
            cinemaRepository.save(new Cinema("전라", "전주효자"));
            cinemaRepository.save(new Cinema("전라", "정읍"));

            /* 제주 */
            cinemaRepository.save(new Cinema("제주", "제주"));
            cinemaRepository.save(new Cinema("제주", "제주노형"));

            System.out.println("Table Cinema(극장) Data 작업 완료\n");
        }else{
            System.out.println("Table Cinema(극장) Data가 이미 있습니다. (현재 : "+cinemaRepository.count()+"개)");
        }

        /*  
        *   영화 상세정보 크롤링
        *   1. 영화리스트에서 영화상세 페이지 Url 정보를 얻는다.
        *   2. 영화상세 페이지에서 DB Table Movie의 정보를 얻는다.
        *   3. DB Table Movie에 Data INSERT
        */
        int movieTableCount = (int)movieRepository.count();
        System.out.println("Table Movie(영화) Data 넣는 중... (시작 Data 개수 : "+movieTableCount+"개)");

        int listNo = 1;
        Document movieList = Jsoup.connect("http://www.cgv.co.kr/reserve/show-times/movies.aspx").get();
        while(true){
             
            /* 영화상세 페이지 URL 유효성 검사 */
            String movieidx;
            if(!(movieidx = movieList.select("#movie_list > ul > li:nth-child("+listNo+") > a").attr("data-movieidx")).equals("")){
                Document movieDetail =  Jsoup.connect("http://www.cgv.co.kr/movies/detail-view/?midx="+movieidx).get();
                
                /* 영화만 저장 */
                if(movieDetail.select(".spec > dl").text().substring(0,2).equals("감독")){

                    /* 영화 개봉정보 :: 개봉날짜, 영화 개봉종류 */
                    String movieRelease = movieDetail.select(".spec > dl > dd:nth-child(11)").text();

                    String releaseDate;
                    if( movieRelease.length() > 0 ){
                        releaseDate = movieRelease.substring(0,4) + movieRelease.substring(5,7) + movieRelease.substring(8,10);
                    }else{
                        releaseDate = "0";   
                    }   //  개봉날짜

                    String releaseType;
                    if( movieRelease.length() > 10 ){
                        releaseType = movieRelease.substring(10,15);
                    }else{
                        releaseType = "(개봉)";
                    }   //  개봉종류
                    
                    /* 영화 주요정보 :: 영화 소개글 */
                    String movieIntro = movieDetail.select(".sect-story-movie").toString();
                    if( movieIntro.length() > 38 ){
                        movieIntro = movieIntro.substring(33, movieIntro.length()-8).trim();
                    }else{
                        movieIntro = "";
                    }
                    
                    /* 영화 장르 */
                    String genre = movieDetail.select(".spec > dl > dt:nth-child(6)").text();
                    if( genre.length() > 4 ){
                        genre = genre.substring(5, genre.length());
                    }else{
                        genre = "";
                    }

                    /* 영화 기본정보 :: 영화 배우, 영화 상영시간, 영화 관람등급, 영화 제작국가 */
                    int movieBaseLocation;
                    int movieActorLocation;
                    String actor;
                    String movieTime;
                    String movieRating;
                    String movieCountry;
                    /*  */
                    if( movieDetail.select(".spec > dl > dd:nth-child(4)").text().equals("") ){
                        movieActorLocation = 5;
                        movieBaseLocation = 9;                       
                    }else{                     
                        movieActorLocation = 6;
                        movieBaseLocation = 10;
                    }   //  배열 위치
                        
                    actor = movieDetail.select(".spec > dl > dd:nth-child("+movieActorLocation+")").text();
                    
                    movieRating = movieDetail.select(".spec > dl > dd:nth-child("+movieBaseLocation+")").text().split(",")[0].trim();
                    if(movieRating.equals("12세 이상")){
                        movieRating = "12";
                    }else if(movieRating.equals("15세 이상")){
                        movieRating = "15";
                    }else if(movieRating.equals("청소년 관람불가")){
                        movieRating = "19";
                    }

                    int infoLength = movieDetail.select(".spec > dl > dd:nth-child("+movieBaseLocation+")").text().split(",").length;
                    if( infoLength < 3 ){
                        movieTime = "(공백)";
                        movieCountry = "(공백)";
                    }else{
                        movieTime = movieDetail.select(".spec > dl > dd:nth-child("+movieBaseLocation+")").text().split(",")[1].trim();
                        movieCountry = movieDetail.select(".spec > dl > dd:nth-child("+movieBaseLocation+")").text().split(",")[2].trim();
                    }
                    

                    /* 영화 감독 */
                    String movieDrector = "";
                    int actorListNo = 1;
                    while(true){

                        /* 영화 감독이 여러명이면.. */
                        if( movieDetail.select(".spec > dl > dd:nth-child(2) > a:nth-child("+actorListNo+")").text() != null
                                    && !movieDetail.select(".spec > dl > dd:nth-child(2) > a:nth-child("+actorListNo+")").text().equals("") ){
                            movieDrector += movieDetail.select(".spec > dl > dd:nth-child(2) > a:nth-child("+actorListNo+")").text() + ", ";
                        }else{
                            break;
                        }
                        actorListNo++;
                    }
                    movieDrector = movieDrector.substring(0, movieDrector.length()-2);

                    /* Data 없는 값일 경우 '(공백)' 처리 :: NOT NULL 문제점 해결  */
                    if( movieRating.equals("") ){
                        movieRating = "(공백)";
                    }
                    if( genre.equals("") ){
                        genre = "(공백)";
                    }
                    if( movieDrector.equals("") ){
                        movieDrector = "(공백)";
                    }
                    if( actor.equals("") ){
                        actor = "(공백)";
                    }
                    if( movieCountry.equals("") ){
                        movieCountry = "(공백)";
                    }

                    /* 예매율 */
                    String reservation = movieDetail.select(".box-contents > div.score > strong.percent > span").text();
                    Float reservationRate = Float.valueOf(reservation.substring(0, reservation.length()-1)).floatValue();
   
                    /* DB INSERT */
                    if(movieRepository.findOneByMovieTitle(movieDetail.select(".title > strong").text()) != null){
                        movieRepository.save(Movie.builder()
                                                    .movieRating(movieRating)
                                                    .movieTitle(movieDetail.select(".title > strong").text())
                                                    .movieTitleEng(movieDetail.select(".title > p").text())
                                                    .movieGenre(genre)
                                                    .movieTime(movieTime)
                                                    .movieImage(movieDetail.select(".box-image > a > span > img").attr("src"))
                                                    .movieDrector(movieDrector)
                                                    .movieActor(actor)
                                                    .movieCountry(movieCountry)
                                                    .movieIntro(new javax.sql.rowset.serial.SerialClob(movieIntro.toCharArray()))
                                                    .reservationRate(reservationRate)
                                                    .releaseDate((long)Integer.parseInt(releaseDate))
                                                    .releaseType(releaseType).build());
                    }                             
                    
                }//end of save
            }else{
                break;    // 영화상세 페이지 URL이 없을 경우 정지
            }     
            listNo++;
        }//end of while 
        System.out.println("Table Movie(영화) Data 작업 완료... (총 "+
                (movieRepository.count()-movieTableCount)+"개 등록완료, 현재 Data : "+movieRepository.count()+"개)\n");

        /*   
        *   극장별 상영시간표 크롤링 
        */
    boolean screenAndTimeTable = false;    //  상영관, 상영 시간표 크롤링 여부
    if(screenAndTimeTable){    
        boolean saveTimetable = false;
        int screenTableCount = (int)screenRepository.count();
        int timeTableTableCount = (int)timeTableRepository.count();
        for(int i=0; i<2; i++){
            /* 콘솔 모니터링용 */
            if(!saveTimetable){
                System.out.println("Table Screen(상영관) Data 넣는 중... (시작 Data 개수 : "+screenTableCount+"개)");
            }else{
                System.out.println("Table TimeTable(상영 시간표) Data 넣는 중... (시작 Data 개수 : "+timeTableTableCount+"개)");
            }

            int listNum = 1;
            Document moviesList = Jsoup.connect("http://www.cgv.co.kr/reserve/show-times/movies.aspx").get();
            while(true){
                
                /* 영화상세 페이지 URL 유효성 검사 */
                String movieidx;
                if(!(movieidx = moviesList.select("#movie_list > ul > li:nth-child("+listNum+") > a").attr("data-movieidx")).equals("")){
                    
                    /* 영화만 저장 */
                    Document movieDetail =  Jsoup.connect("http://www.cgv.co.kr/movies/detail-view/?midx="+movieidx).get();
                    if(movieDetail.select(".spec > dl").text().substring(0,2).equals("감독")){
                        
                        /* 영화 시간표 메인 */
                        String movieTitle = movieDetail.select("#select_main > div.sect-base-movie > div.box-contents > div.title > strong").text();    //  영화 제목
                        int areaListNo = 1;
                        Document movieShowTimes =  Jsoup.connect("http://www.cgv.co.kr/common/showtimes/iframeMovie.aspx?midx="+movieidx).get();
                        while(true){
                            
                            /* 극장 지역 URL 유효성 검사 */
                            Elements areaUrl = movieShowTimes.select(".sect-city > ul > li:nth-child("+areaListNo+") > a");
                            if( ( areaUrl.attr("href") != null ) && ( !areaUrl.attr("href").equals("") ) ){
                                
                                /* 영화 시간표 리스트 */
                                int timeListNo = 1;
                                Document timeTable =  Jsoup.connect("http://www.cgv.co.kr/common/showtimes"+areaUrl.attr("href").substring(1, areaUrl.attr("href").length())).get();
                                while(true){
                                    
                                    /* 상영 날짜 리스트 */
                                    String dateUrl = timeTable.select("#slider > div > ul > li:nth-child("+timeListNo+") > div > a").attr("href");
                                    if( (dateUrl != null) && (!dateUrl.equals("")) ){
                                        
                                        /* 극장 리스트 */
                                        Long date = (long)Integer.parseInt(dateUrl.split("date=")[1]);
                                        int cinemaListNo = 1;
                                        Document cinemaList =  Jsoup.connect("http://www.cgv.co.kr/common/showtimes"+dateUrl.substring(1, areaUrl.attr("href").length())).get();
                                        while(true){
                                            
                                            /* 극장 이름 리스트 */
                                            Elements cinema = cinemaList.select(".sect-showtimes > ul > li:nth-child("+cinemaListNo+")");
                                            String cinemaName = cinema.select(".col-theater").text();   //  극장 이름
                                            if( (cinemaName != null) && (!cinemaName.equals("")) ){
                                                
                                                /* 극장 이름 가공 */
                                                if(cinemaName.substring(0,3).equals("CGV")){
                                                    cinemaName = cinemaName.split("CGV ")[1];   //  극장 이름에서 'CGV '를 제외
                                                }else if( cinemaName.substring(0,5).equals("씨네드쉐프") ){
                                                    cinemaName = "CINE DE CHEF "+cinemaName.split("씨네드쉐프 ")[1];
                                                }else{
                                                    //cinemaName = "CINE KIDS "+cinemaName.split("씨네키드")[1];
                                                }

                                                /* 상영관 정보 */
                                                int screenInfoNo = 1;
                                                while(true){
                                                    
                                                    /* 상영관 정보 리스트 */
                                                    Elements screenList = cinema.select(".col-times").select(".type-hall:nth-child("+screenInfoNo+")");
                                                    if( (screenList.text()) != null && (!screenList.text().equals("")) ){

                                                        String dimension = screenList.select(".info-hall > ul > li:nth-child(1)").text();    //  상영 방식
                                                        String name = screenList.select(".info-hall > ul > li:nth-child(2)").text();    //  상영관 이름
                                                        Short totalSeat = (short)30;    //  총 좌석수     
                                                        
                                                        /* DB TABLE SCREEN INSERT */
                                                        if(  !saveTimetable && screenRepository.findByName(name).size() == 0 ){    //  먼저, 상영관 테이블 데이터 추가 후 시간표 테이블 데이터 추가를 위한 boolean값
                                                            Long cinemaId = (long)cinemaRepository.findByCinemaName(cinemaName).get(0).getId();
                                                            screenRepository.save(new Screen(cinemaId, name, totalSeat, dimension));
                                                        }
                                                        
                                                        if( saveTimetable ){
                                                            /* 시간표 정보 리스트 */
                                                            int timeTableInfoNo = 1;
                                                            Elements timeTableList = screenList.select(".info-timetable > ul");
                                                            while(true){
                                                                
                                                                /* 시간표 정보 */
                                                                String titmeTableInfo = timeTableList.select("li:nth-child("+timeTableInfoNo+") a").attr("data-playendtime");
                                                                if( titmeTableInfo != null && (!titmeTableInfo.equals("")) ){

                                                                    Long startTime = (long)Integer.parseInt(timeTableList.select("li:nth-child("+timeTableInfoNo+") a").attr("data-playstarttime"));    //  상영 시작시간
                                                                    Long endTime = (long)Integer.parseInt(timeTableList.select("li:nth-child("+timeTableInfoNo+") a").attr("data-playendtime"));    //  상영 종료시간
                                                                    int turningNo = Integer.parseInt(timeTableList.select("li:nth-child("+timeTableInfoNo+") a").attr("data-playnum"));    //  회차

                                                                    /* 상영관 고유번호 */
                                                                    List<Screen> screens = screenRepository.findByName(name);
                                                                    Long screenId = null;
                                                                    if(  screens.size() > 0 ){
                                                                        screenId = (long)screens.get(0).getId();    //  상영관 고유번호
                                                                    }

                                                                    /* 영화 고유번호 */
                                                                    Movie movie = movieRepository.findOneByMovieTitle(movieTitle);
                                                                    Long movieId = null;
                                                                    if(  movie != null ){
                                                                        movieId = (long)movie.getId();    //  영화 고유번호
                                                                    }

                                                                    Long cinemaId = screenRepository.findCinemaIdById(screenId);    //  극장 고유번호

                                                                    /* DB TABLE TIMETABLE INSERT */
                                                                    if(timeTableRepository.findByScreenIdAndMovieIdAndTurningNoAndDateAndStartTimeAndEndTime(
                                                                            screenId, movieId, turningNo, date, startTime, endTime).size() == 0){
                                                                        timeTableRepository.save(TimeTable.builder().screenId(screenId).movieId(movieId).turningNo(turningNo)
                                                                                                        .date(date).startTime(startTime).endTime(endTime).cinemaId(cinemaId).build());
                                                                    }

                                                                }else{
                                                                    break;    //  시간표 정보가 없을 경우 정지
                                                                }
                                                                timeTableInfoNo++;
                                                            }//end of while  ::  시간표 정보
                                                        }//end of if  ::  상영관 테이블 데이터 추가 이후에 시간표 테이블 데이터 추가를 위한 if문

                                                    }else{
                                                        break;    //  상영관 정보가 없을 경우 정지
                                                    }
                                                    screenInfoNo++;
                                                }//end of while  ::  상영관 정보
                                                
                                            }else{
                                                break;    //  상영관 이름이 없을 경우 정지
                                            }
                                        cinemaListNo++;
                                        }//end of while  ::  극장 리스트                   
                                        
                                    }else{
                                        break;    //  날짜 URL이 없을 경우 정지    
                                    }
                                    timeListNo++;
                                }//end of while  ::  영화 시간표 리스트 

                            }else{
                                break;    //  지역 URL이 없을 경우 정지
                            }
                            areaListNo++;
                        }//end of while  :: 영화 시간표 메인

                    }//end of if  ::  영화 감독일 경우

                }else{
                    break;    // 영화상세 페이지 URL이 없을 경우 정지
                }     
                listNum++;
            }//end of while  ::  영화 리스트
            
            /* 콘솔 모니터링용 */
            if(!saveTimetable){
                System.out.println("Table Screen(상영관) Data 작업 완료... (총 "+
                        (screenRepository.count()-screenTableCount)+"개 등록완료, 현재 Data : "+screenRepository.count()+"개)\n");
            }else{
                System.out.println("Table TimeTable(상영 시간표) Data 작업 완료... (총 "+
                        (timeTableRepository.count()- timeTableTableCount)+"개 등록완료, 현재 Data : "+timeTableRepository.count()+"개)\n");
            }
            saveTimetable = true;    //  Screen정보 저장 후 Timetable 작업을 위한 용도

        }//end of for  ::  상영관 테이블, 시간표 테이블 데이터 추가 용도
    }

        /* DB TABLE SEAT INSERT
         * 좌석 label = A, B, C, D, E (행)
         * 좌석 no = 1, 2, 3, 4, 5, 6 (열)
         * 총 좌석수 : 30개
         */
        /* Table Seat에 Data가 없을 경우에만 실행  */
        if(seatRepository.count()==0){
            System.out.println("Table Seat(좌석)에 Data 넣는 중...");

            for(int screenId=1; screenId<=screenRepository.count(); screenId++){

                char label = 65;    //  좌석 label
                for(int line=1; line<=5; line++){
                    for(int no=1; no<=6; no++){    //  좌석 no
                        seatRepository.save(new Seat((long)screenId, String.valueOf(label)+no, "Standard Zone"));
                    }
                    label++;
                }

            }
            System.out.println("Table Seat(좌석)에 Data 작업 완료...\n");
        }else{
            System.out.println("Table Seat(좌석)에 Data가 이미 있습니다. (현재 : "+seatRepository.count()+"개)");
        }

        /* DB TABLE TICKET INSERT 
         * 모든 TimeTable(시간표)에 총 좌석 수만큼 티켓 생성
         */
        /* Table Ticket에 Data가 없을 경우에만 실행 */
        if(ticketRepository.count() == 0){
            System.out.println("Table Ticket(티켓)에 Data 넣는 중...");

                int timeTableCount = (int)timeTableRepository.count(); // 상영 시간표 개수 : (티켓 데이터가 방대해서 필요한만큼만 사용)
                for(int timeTableId=1; timeTableId<=timeTableCount; timeTableId++){
                    if(ticketRepository.findAllByTimeTableId((long)timeTableId).size() == 0){
                        Long screenId = timeTableRepository.findById((long)timeTableId).get().getScreenId();
                        for(int count=0; count<seatRepository.countByScreenId(screenId); count++){
                            ticketRepository.save(Ticket.builder().seatId(seatRepository.findByScreenId(screenId).get(count).getId())
                                                        .screenId(screenId)
                                                        .movieId(timeTableRepository.findById((long)timeTableId).get().getMovieId())
                                                        .timeTableId((long)timeTableId)
                                                        .ticketPrice(5000)
                                                        .ticketState('1').build());
                        }
                    }
                }
            System.out.println("Table Ticket(티켓)에 Data 작업 완료...\n");
         }else{
             System.out.println("Table Ticket(티켓) Data가 이미 있습니다. (현재 : "+ticketRepository.count()+"개)");
         }

        /*  DB Table COLUMN 개수 화면에 출력   */
        String resultMessage = "<p>TABLE CINEMA row = "+cinemaRepository.count()+"개\t(정상 출력값 : 174개)</p>" 
                                        +"<p>TABLE MOVIE row = "+movieRepository.count()+"개</p>"
                                        +"<p>TABLE SCREEN row = "+screenRepository.count()+"개</p>"
                                        +"<p>TABLE TIMETABLE row = "+timeTableRepository.count()+"개</p>"
                                        +"<p><br/>출력값이 비정상일 경우 DB TABLE DROP 후에 재실행 (이유 : 중복제거)</p>";
        System.out.println("모든 Table 작업 완료");

        return resultMessage;
    }//end of Method

}//end of RestController