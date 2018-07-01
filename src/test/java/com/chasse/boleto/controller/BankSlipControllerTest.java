package com.chasse.boleto.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.chasse.boleto.entity.BankSlipRequest;
import com.chasse.boleto.entity.BankSlipResponseWithError;
import com.chasse.boleto.entity.BankSlipResponseWithTax;
import com.chasse.boleto.model.BankSlip;
import com.chasse.boleto.model.BankSlipStatus;
import com.chasse.boleto.repository.BankSlipRepository;
import com.chasse.boleto.util.BoletoConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankSlipControllerTest {
	
	private BankSlipController controller;
    private BankSlipRepository bankSlipRepository;

    @Before
    public void init() {
        bankSlipRepository = mock(BankSlipRepository.class);
        controller = new BankSlipController(bankSlipRepository);
    }
    
	@Test
    public void testPay() {
		UUID id = UUID.fromString("84e8adbf-1a14-403b-ad73-d78ae19b59bf");
		BankSlip bankSlip = new BankSlip();
		
        when(bankSlipRepository.findById(id)).thenReturn(Optional.of(bankSlip));
        when(bankSlipRepository.save(bankSlip)).thenReturn(bankSlip);

        assertEquals(controller.pay(id.toString()).getStatusCode(), HttpStatus.OK);
    }
	
	@Test
    public void testCancel() {
		UUID id = UUID.fromString("84e8adbf-1a14-403b-ad73-d78ae19b59bf");
		BankSlip bankSlip = new BankSlip();
		
        when(bankSlipRepository.findById(id)).thenReturn(Optional.of(bankSlip));
        when(bankSlipRepository.save(bankSlip)).thenReturn(bankSlip);

        assertEquals(controller.cancel(id.toString()).getStatusCode(), HttpStatus.OK);
    }
	
	@Test
    public void testTaxUntilTenDaysFindByID() {
		UUID id = createBankSlipToFindByID(-4);
        
        ResponseEntity<BankSlipResponseWithTax> response = controller.findByID(id.toString());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.getBody().getFine() == 3000);
    }
	
	@Test
    public void testTaxMoreThanTenDaysFindByID() {
		UUID id = createBankSlipToFindByID(-11);
        
		ResponseEntity<BankSlipResponseWithTax> response = controller.findByID(id.toString());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.getBody().getFine() == 6000);
    }

	private UUID createBankSlipToFindByID(int daysBefore) {
		Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.add(Calendar.DATE, daysBefore);

        BankSlip bankSlip = new BankSlip();
        bankSlip.setDueDate(date.getTime());
        bankSlip.setTotalInCents(600000);
        
        UUID id = UUID.fromString("84e8adbf-1a14-403b-ad73-d78ae19b59bf");
        when(bankSlipRepository.findById(id)).thenReturn(Optional.of(bankSlip));
		return id;
	}
	
	@Test
    public void testCreateBankSlip() {
		BankSlipRequest bankSlipRequest = new BankSlipRequest();
		bankSlipRequest.setCustomer("New Company");
		bankSlipRequest.setDueDate(new Date());
		bankSlipRequest.setStatus(BankSlipStatus.PENDING);
		bankSlipRequest.setTotalInCents(50000);
		
		when(bankSlipRepository.save(new BankSlip())).thenReturn(new BankSlip());
        assertEquals(controller.createBankSlip(bankSlipRequest).getStatusCode(), HttpStatus.CREATED);
    }
	
	@Test
    public void testCreateBankSlipWithEntityErros() {
		BankSlipRequest bankSlipRequest = new BankSlipRequest();
		ResponseEntity<BankSlipResponseWithError> responseWithError = controller.createBankSlip(bankSlipRequest);
		
        assertEquals(responseWithError.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertTrue(responseWithError.getBody().getErrorList().contains(BoletoConstants.ERROR_MSG_CUSTOMER_NOT_NULL));
        assertTrue(responseWithError.getBody().getErrorList().contains(BoletoConstants.ERROR_MSG_DUE_DATE_NOT_NULL));
        assertTrue(responseWithError.getBody().getErrorList().contains(BoletoConstants.ERROR_MSG_TOTAL_IN_CENTS_POSITIVE_NUMBER));
        assertTrue(responseWithError.getBody().getErrorList().contains(BoletoConstants.ERROR_MSG_STATUS_NOT_NULL));
    }
	
	@Test
    public void testCreateBankSlipNull() {
        assertEquals(controller.createBankSlip(null).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}
