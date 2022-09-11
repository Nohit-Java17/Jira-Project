package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface CreditCardService {
    public List<CreditCard> getDsCreditCard();

    public CreditCard getCreditCard(int id);

    public CreditCard saveCreditCard(CreditCard creditCard);

    public void deleteCreditCard(int id);

    public CreditCard createCreditCard(KhachHang khachHang);
}
