package com.semiclone.springboot.web;

import java.util.List;

import com.semiclone.springboot.repository.CinemaRepository;
import com.semiclone.springboot.repository.MovieRepository;
import com.semiclone.springboot.repository.entity.Cinema;
import com.semiclone.springboot.repository.entity.Movie;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*  FileName : onloadRestController.java
*   DB Table에 더미 데이터 셋팅
*   Return : Client에 DB Table 실제 데이터 출력
*/
@RestController
@RequestMapping("onload")
public class OnloadRestController{

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping(value = "/constructor", method = RequestMethod.GET)
    public String constructor() throws Throwable{

        /* 강원 */
        cinemaRepository.save(new Cinema("강원", "강릉"));
        cinemaRepository.save(new Cinema("강원", "원주"));
        cinemaRepository.save(new Cinema("강원", "인제"));
        cinemaRepository.save(new Cinema("강원", "춘천"));
        cinemaRepository.save(new Cinema("강원", "춘천명동"));

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

        /* 대구 */
        cinemaRepository.save(new Cinema("대구", "대구"));
        cinemaRepository.save(new Cinema("대구", "대구수성"));
        cinemaRepository.save(new Cinema("대구", "대구스타디음"));
        cinemaRepository.save(new Cinema("대구", "대구아카데미"));
        cinemaRepository.save(new Cinema("대구", "대구월성"));
        cinemaRepository.save(new Cinema("대구", "대구이시아"));
        cinemaRepository.save(new Cinema("대구", "대구칠곡"));
        cinemaRepository.save(new Cinema("대구", "대구한일"));
        cinemaRepository.save(new Cinema("대구", "대구현대"));
        cinemaRepository.save(new Cinema("대전", "대전"));
        cinemaRepository.save(new Cinema("대전", "대전가수원"));
        cinemaRepository.save(new Cinema("대전", "대전가오"));
        cinemaRepository.save(new Cinema("대전", "대전탄방"));
        cinemaRepository.save(new Cinema("대전", "대전터미널"));
        cinemaRepository.save(new Cinema("대전", "유성노은"));

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
        cinemaRepository.save(new Cinema("부산", "CINE de CHEF 센텀"));

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
        cinemaRepository.save(new Cinema("서울", "피카다리 1958"));
        cinemaRepository.save(new Cinema("서울", "하계"));
        cinemaRepository.save(new Cinema("서울", "홍대"));
        cinemaRepository.save(new Cinema("서울", "CINE de CHEF 압구정"));
        cinemaRepository.save(new Cinema("서울", "CINE de CHEF 용산"));

        /* 울산 */
        cinemaRepository.save(new Cinema("울산", "울산삼산"));
        cinemaRepository.save(new Cinema("울산", "울산신천"));
        cinemaRepository.save(new Cinema("울산", "울산진장"));

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
        
        /*  
        *   영화 상세정보 크롤링
        *   1. 영화리스트에서 영화상세 페이지 Url 정보를 얻는다.
        *   2. 영화상세 페이지에서 DB Table Movie의 정보를 얻는다.
        *   3. DB Table Movie에 Data INSERT
        */
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
                    if( movieDetail.select(".spec > dl > dd:nth-child(4)").text().equals("") ){
                        movieActorLocation = 5;
                        movieBaseLocation = 9;                       
                    }else{                     
                        movieActorLocation = 6;
                        movieBaseLocation = 10;
                    }   //  배열 위치
                                 
                    actor = movieDetail.select(".spec > dl > dd:nth-child("+movieActorLocation+")").text();
                    movieTime = movieDetail.select(".spec > dl > dd:nth-child("+movieBaseLocation+")").text().split(",")[1].trim();
                    movieRating = movieDetail.select(".spec > dl > dd:nth-child("+movieBaseLocation+")").text().split(",")[0].trim();  
                    if( movieDetail.select(".spec > dl > dd:nth-child("+movieBaseLocation+")").text().split(",").length < 3 ){
                        movieCountry = "";
                    }else{
                        movieCountry = movieDetail.select(".spec > dl > dd:nth-child("+movieBaseLocation+")").text().split(",")[2].trim();
                    }

                    /* DB INSERT */
                    movieRepository.save(Movie.builder()
                                                .movieRating(movieRating)
                                                .movieTitle(movieDetail.select(".title > strong").text())
                                                .movieTitleEng(movieDetail.select(".title > p").text())
                                                .movieGenre(genre)
                                                .movieTime(movieTime)
                                                .movieImage(movieDetail.select(".box-image > a > span > img").attr("src"))
                                                .movieDrector(movieDetail.select(".spec > dl > dd:nth-child(2) > a").text())
                                                .movieActor(actor)
                                                .movieCountry(movieCountry)
                                                .movieIntro(new javax.sql.rowset.serial.SerialClob(movieIntro.toCharArray()))
                                                .releaseDate((long)Integer.parseInt(releaseDate))
                                                .releaseType(releaseType).build());
                                                
                    
                }//end of save
            }else{
                break;    // 영화상세 페이지 URL이 없을 경우 정지
            }     
            listNo++;
        }//end of while

        /*  DB Table COLUMN 개수 화면에 출력   */
        String resultMessage = "<p>TABLE CINEMA row = "+cinemaRepository.count()+"개\t(정상 출력값 : 174개)</p>" 
                                        +"<p>TABLE MOVIE row = "+movieRepository.count()+"개\t(정상 출력값 : 72개)</p>"
                                        +"<p><br/>출력값이 비정상일 경우 DB TABLE DROP 후에 재실행 (이유 : 중복제거)</p>";
        return resultMessage;
    }//end of Method

}//end of RestController