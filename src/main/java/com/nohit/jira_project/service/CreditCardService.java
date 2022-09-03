package com.nohit.jira_project.service;

import com.nohit.jira_project.model.*;

public interface CreditCardService {
    public Iterable<CreditCard> getDsCreditCard();

    public CreditCard getCreditCard(int id);

    public void saveCreditCard(CreditCard creditCard);

    public void deleteCreditCard(int id);
}
