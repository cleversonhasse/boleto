package com.chasse.boleto.repository;

import java.util.Date;
import java.util.UUID;

import org.assertj.core.util.IterableUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chasse.boleto.model.BankSlip;
import com.chasse.boleto.model.BankSlipStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankSlipRepositoryTest {

    @Autowired
    BankSlipRepository repository;

    @Test
    public void testRepository(){
    	BankSlip bankSlip1 = repository.findByCustomer("Facade Company");
    	if (bankSlip1 != null) {
    		repository.delete(bankSlip1);
    	}
    	
    	BankSlip bankSlip2 = repository.findByCustomer("Blue Company");
    	if (bankSlip2 != null) {
    		repository.delete(bankSlip2);
    	}
    	
    	BankSlip bankSlip3 = repository.findByCustomer("Account Company");
    	if (bankSlip3 != null) {
    		repository.delete(bankSlip3);
    	}
    	
    	repository.save(new BankSlip(UUID.fromString("33faadbf-1a14-403b-ad73-d78ae19b59bf"), new Date(), 100, "Facade Company", BankSlipStatus.PENDING));
        repository.save(new BankSlip(UUID.fromString("95faaddf-ae14-4afb-ade4-d7eae19aaebe"), new Date(), 200, "Blue Company", BankSlipStatus.PAID));
        repository.save(new BankSlip(UUID.fromString("5b4bb8db-ae4b-d4f5-aeea-45fde1ed644d"), new Date(), 308, "Account Company", BankSlipStatus.CANCELED));

        final Iterable<BankSlip> todos = repository.findAll();
        // Contabilizando os 2 inseridos via banco no arquivo V2_insert_data.sql e os 3 inseridos acima 
        Assert.assertTrue(IterableUtil.sizeOf(todos) >= 5);

        final Iterable<BankSlip> bankSlipListByStatus = repository.findAllByStatus(BankSlipStatus.PENDING);
        // Verifica se encontrou ao menos um bankSlip com status Pendente
        Assert.assertTrue(IterableUtil.sizeOf(bankSlipListByStatus) >= 1);

    }
	
}
