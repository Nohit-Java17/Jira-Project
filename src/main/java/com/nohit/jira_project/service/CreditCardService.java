package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface CreditCardService {
    public List<CreditCard> getDsCreditCard();

    public CreditCard getCreditCard(int id);

    public void saveCreditCard(CreditCard creditCard);

    public void deleteCreditCard(int id);
}
