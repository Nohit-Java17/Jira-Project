package com.nohit.jira_project.service.Impl;

import java.util.*;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import lombok.extern.slf4j.*;

@Service
@Transactional
@Slf4j
public class CreditCardServiceImpl implements CreditCardService {
    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private StringUtil stringUtil;

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
    public CreditCard saveCreditCard(CreditCard creditCard) {
        creditCard.setNameOnCard(stringUtil.parseNameOnCard(creditCard.getNameOnCard()));
        log.info("Saving credit_card with name: {}", creditCard.getNameOnCard());
        return creditCardRepository.save(creditCard);
    }

    @Override
    public void deleteCreditCard(int id) {
        log.info("Deleting credit_card with id: {}", id);
        creditCardRepository.deleteById(id);
    }

    @Override
    public CreditCard createCreditCard(KhachHang khachHang) {
        var creditCard = new CreditCard();
        creditCard.setId(khachHang.getId());
        log.info("Create credit_card with email: {}", khachHang.getEmail());
        return creditCardRepository.save(creditCard);
    }
}
