package com.semiclone.springboot.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.semiclone.springboot.config.IamPortConfig;
import com.semiclone.springboot.domain.payment.Payment;
import com.semiclone.springboot.domain.payment.PaymentRepository;
import com.semiclone.springboot.web.dto.iamport.AccessToken;
import com.semiclone.springboot.web.dto.iamport.AuthData;
import com.semiclone.springboot.web.dto.iamport.IamportResponse;
import com.semiclone.springboot.web.dto.iamport.Purchase;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final IamPortConfig iamPortConfig;
    private final PaymentRepository paymentRepository;

    @Transactional
    public Map<String, Object> iamport(Map<String, Object> purchaseMap) throws Exception {

        Map<String, Object> returnMap = new HashMap<String, Object>();

        if (purchaseMap.containsKey("imp_uid")) {

            String API_URL = "https://api.iamport.kr";
            String api_key = iamPortConfig.getApikey();
            String api_secret = iamPortConfig.getApisecret();
            HttpClient client = HttpClientBuilder.create().build();
            Gson gson = new Gson();

            /* getToken */
            AuthData authData = new AuthData(api_key, api_secret);
            String authJsonData = gson.toJson(authData);

            StringEntity data = new StringEntity(authJsonData);

            HttpPost postRequest = new HttpPost(API_URL + "/users/getToken");
            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Connection", "keep-alive");
            postRequest.setHeader("Content-Type", "application/json");

            postRequest.setEntity(data);

            HttpResponse response = client.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                // throw new RuntimeException("Failed : HTTP error code : "
                // + response.getStatusLine().getStatusCode());
                returnMap.put("result", "0");
                return returnMap;
            }

            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);
            Type listType = new TypeToken<IamportResponse<AccessToken>>() {
            }.getType();
            IamportResponse<AccessToken> auth = gson.fromJson(body, listType);

            String token = auth.getResponse().getToken();

            /* getPurchase
             * IamPort Server에서 Data 가져오기 : 안전한 데이터
             */
            if (token != null) {
                String path = "/payments/" + purchaseMap.get("imp_uid");   //  imp_uid에 변수를 입력해야 함!

                HttpGet getRequest = new HttpGet(API_URL + path);
                getRequest.addHeader("Accept", "application/json");
                getRequest.addHeader("Authorization", token);

                HttpResponse responses = client.execute(getRequest);

                if (responses.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + responses.getStatusLine().getStatusCode());
                }

                ResponseHandler<String> handlers = new BasicResponseHandler();
                String responsed = handlers.handleResponse(responses);

                Type listTypes = new TypeToken<IamportResponse<Purchase>>() {
                }.getType();
                IamportResponse<Purchase> purchaseData = gson.fromJson(responsed, listTypes);
                Purchase purchase = purchaseData.getResponse();
                returnMap.put("purchase", purchase);

            } else {
                returnMap.put("result", "0"); // IamPort Server에 해당 Data가 없을 경우
            }//end of token Validation Check

        } else {
            returnMap.put("result", "0"); // imp_uid가 Map에 없을 경우
        }//end of imp_uid Validation Check

        return returnMap;

    }


    public Map<String, Object> giftshopPurchase(Map<String, Object> purchaseMap, String accountId) throws Exception {

        boolean everythingsOk = false;
        Map<String, Object> returnMap = iamport(purchaseMap);

        if(returnMap.isEmpty() == false) {
            return returnMap;
        } else {

            if (purchaseMap.containsKey("")) {

            }

/*
            if (purchaseMap.containsKey("movieCoupons") && ((List) purchaseMap.get("movieCoupons")).size() > 0) {    //  movieCoupons 유효성 검사
                for (Object movieCouponId : (List) purchaseMap.get("movieCoupons")) {
                    movieCoupons += movieCouponId + ",";
                    MovieCoupon movieCoupon = movieCouponRepository.findOneById((long) movieCouponId);
                    movieCoupon.setMovieCouponState('0');
                    movieCouponRepository.save(movieCoupon);
                }
                movieCoupons = movieCoupons.substring(0, movieCoupons.length() - 1);
            }


            if (purchaseMap.containsKey("giftCards") && ((List) purchaseMap.get("giftCards")).size() > 0) {    //  giftCards 유효성 검사
                for (Object giftCardInfo : (List) purchaseMap.get("giftCards")) {
                    Map<String, Object> map = (Map) giftCardInfo;
                    giftCards += map.get("giftCardId");
                    GiftCard giftCard = giftCardRepository.findOneById((long) map.get("giftCardId"));
                    int useMoney = (int) map.get("useMoney");
                    int balance = giftCard.getGiftCardBalance();
                    if (balance >= useMoney) {
                        giftCard.setGiftCardBalance((balance - useMoney));
                        giftCardRepository.save(giftCard);
                    } else {
                        everythingsOk = false;
                        break;
                    }
                }
                giftCards = giftCards.substring(0, giftCards.length() - 1);
            }

            if (purchaseMap.containsKey("point") && ((List) purchaseMap.get("point")).size() > 0) {    //  point 유효성 검사
                Account account = accountRepository.findByAccountId(((Account) session.getAttribute("account")).getAccountId());
                int usePoint = (int) ((List) purchaseMap.get("point")).get(0);
                int point = account.getPoint();
                if (point >= usePoint) {
                    account.setPoint((point - usePoint));
                    accountRepository.save(account);
                } else {
                    everythingsOk = false;
                }
            }

            if (purchaseMap.containsKey("tickets") && ((List) purchaseMap.get("tickets")).size() > 0) {    //  tickets 유효성 검사
                for (Object ticketId : (List) purchaseMap.get("tickets")) {
                    tickets += ticketId + ",";
                    if (everythingsOk) {
                        Ticket ticket = ticketRepository.findOneById((long) ticketId);

                        ticketHisotryRepository.save(
                                TicketHistory.builder().seatId((long) ticket.getSeatId())
                                        .screenId((long) ticket.getScreenId())
                                        .movieId((long) ticket.getMovieId())
                                        .ticketPrice(ticket.getTicketPrice())
                                        .accountId(ticket.getAccountId()).build());
                        ticketRepository.delete(ticket);
                    }
                }
                tickets = tickets.substring(0, tickets.length() - 1);
            }
*/
            if (everythingsOk) {    // 기프트카드, 포인트 잔액 유효성 검사에 성공했을 경우
                Purchase purchase = (Purchase) purchaseMap.get("purchase");
                paymentRepository.save(
                        Payment.builder().accountId(accountId)
                                .receiverName(purchase.getBuyerName())
                                .receiverPhone(purchase.getBuyerTel())
                                .paymentMethod(purchase.getPayMethod())
                                .paymentAmount(purchase.getAmount())
/*                                .giftCardIds(giftCards)
                                .movieCouponIds(movieCoupons)
                                .ticketIds(tickets)*/
                                .build()
                );

                returnMap.put("result", "1");
            } else {
                returnMap.put("result", "0");
            }


        return returnMap;

        }
    }
}