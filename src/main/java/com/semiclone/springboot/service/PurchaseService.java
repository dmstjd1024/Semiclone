package com.semiclone.springboot.service;

import com.semiclone.springboot.domain.payment.PaymentRepository;
import com.semiclone.springboot.support.IamportClient;
import com.semiclone.springboot.web.dto.iamport.IamportResponse;
import com.semiclone.springboot.web.dto.iamport.Purchase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final IamportClient iamportClient;
    private final PaymentRepository paymentRepository;

    @Transactional
    public Purchase iamport(String imp_uid) throws Exception {

        Purchase purchase = null;

        if (imp_uid != null) {
            IamportResponse<Purchase> purchaseData = iamportClient.paymentByImpUid(imp_uid);
            purchase = purchaseData.getResponse();
        }

        return purchase;

    }

    @Transactional
    public void giftshopPurchase(String imp_uid) throws Exception {

        Purchase purchase = iamport(imp_uid);


    }
}