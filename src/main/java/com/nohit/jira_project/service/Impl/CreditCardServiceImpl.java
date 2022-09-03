package com.nohit.jira_project.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

import lombok.extern.slf4j.*;

@Service
@Slf4j
public class CreditCardServiceImpl implements CreditCardService {
    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public List<CreditCard> getDsCreditCard() {
        log.info("Fetching all credit_card");
        return creditCardRepository.findAll();
    }

    @Override
    public CreditCard getCreditCard(int id) {
        log.info("Fetching credit_card with id {}", id);
        return creditCardRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCreditCard(CreditCard creditCard) {
        log.info("Saving credit_card with name: {}", creditCard.getNameOnCard());
        creditCardRepository.save(creditCard);

    }

    @Override
    public void deleteCreditCard(int id) {
        log.info("Deleting credit_card with id: {}", id);
        creditCardRepository.deleteById(id);

    }
}
