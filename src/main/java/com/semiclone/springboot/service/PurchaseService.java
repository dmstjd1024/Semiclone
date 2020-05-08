package com.semiclone.springboot.service;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.semiclone.springboot.config.IamPortConfig;
import com.semiclone.springboot.web.dto.iamport.AccessToken;
import com.semiclone.springboot.web.dto.iamport.AuthData;
import com.semiclone.springboot.web.dto.iamport.IamportResponse;
import com.semiclone.springboot.web.dto.iamport.Purchase;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final IamPortConfig iamPortConfig;

    public Map<String, Object> iamport() throws Exception{

        Map<String, Object> returnMap = null;
        String API_URL = "https://api.iamport.kr";
        String api_key = iamPortConfig.getApikey();
        String api_secret = iamPortConfig.getApisecret();
        HttpClient client = HttpClientBuilder.create().build();
        Gson gson = new Gson();
    
        /* getToken */
        AuthData authData = new AuthData(api_key, api_secret);	
        String authJsonData = gson.toJson(authData);
        
            StringEntity data = new StringEntity(authJsonData);
            
            HttpPost postRequest = new HttpPost(API_URL+"/users/getToken");
            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Connection","keep-alive");
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
            Type listType = new TypeToken<IamportResponse<AccessToken>>(){}.getType();
            IamportResponse<AccessToken> auth = gson.fromJson(body, listType);
        
        String token = auth.getResponse().getToken();
        
        /* getPurchase
            * IamPort Server에서 Data 가져오기 : 안전한 데이터
            */
        if(token != null) {
            String path = "/payments/"+"imp_uid";    //  imp_uid에 변수를 입력해야 함!

            HttpGet getRequest = new HttpGet(API_URL+path);
            getRequest.addHeader("Accept", "application/json");
            getRequest.addHeader("Authorization", token);

            HttpResponse responses = client.execute(getRequest);

            if (responses.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                + responses.getStatusLine().getStatusCode());
            }
            
            ResponseHandler<String> handlers = new BasicResponseHandler();
            String responsed = handlers.handleResponse(responses);
            
            Type listTypes = new TypeToken<IamportResponse<Purchase>>(){}.getType();
            IamportResponse<Purchase> purchaseData = gson.fromJson(responsed, listTypes);
            Purchase purchase = purchaseData.getResponse();
        }

        return returnMap;

    }

}