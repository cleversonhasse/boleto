package com.contaazul.boleto.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.contaazul.boleto.entity.BankSlipRequest;
import com.contaazul.boleto.entity.BankSlipResponse;
import com.contaazul.boleto.entity.BankSlipResponseMessage;
import com.contaazul.boleto.entity.BankSlipResponseWithError;
import com.contaazul.boleto.model.BankSlip;
import com.contaazul.boleto.model.BankSlipStatus;
import com.contaazul.boleto.repository.BankSlipRepository;

/**
 *	Controla as requisições feitas à raiz da aplicação.
 * 
 * @author Cléverson Hasse
 * @version 1.0.0
 * 
 */

@RestController
@RequestMapping(path = "/rest/bankslips")
public class BankSlipController {

	@Autowired
	private BankSlipRepository repository;
	
	@GetMapping
	public Iterable<BankSlip> findAllBankSlip() {
		return repository.findAll();
	}
	
	/**
     * Método busca Boleto através do UUID passado em String por parâmetro 
     *
     * @param UUID em String do BankSlip passado por parâmetro 
     * @return Resposta da Entidade retorna:
     *   - 200 - ok
     *   - 404 - BankSlip with id {uuid} not found.
     */
	@RequestMapping(method = RequestMethod.GET, value = "/{uuid}", produces = "application/json")
	public ResponseEntity findByID(@PathVariable("uuid") String uuid) {
		BankSlip bankSlip = repository.findById(UUID.fromString(uuid)).orElse(null);
		
		if (bankSlip == null) {
			return responseWithError(HttpStatus.NOT_FOUND, "BankSlip with id " + uuid + " not found.");
		}
		
		return ResponseEntity.ok(bankSlip);
	}
	
	/**
     * Método alterá o status do BankSplit para PAID através de um request de PUT
     *
     * @param UUID em String do BankSlip passado por parâmetro 
     * @return Resposta da Entidade retorna:
     *   - 400 - UUID passed by parameter is not valid value
     *   - 200 - Bankslip paid 
     *   - 404 - Bankslip not found with the specified id
     */
    @RequestMapping(value = "/{uuid}/pay", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity pay(@PathVariable("uuid") String uuid) {
        UUID id;

        try {
            id = UUID.fromString(uuid);
        } catch (NullPointerException | IllegalArgumentException ex) {
    		return responseWithError(HttpStatus.BAD_REQUEST, "Invalid id provided - it must be a valid UUID");
        }

        BankSlip bankSlip = repository.findById(id).orElse(null);
        if (bankSlip == null) {
        	return responseWithError(HttpStatus.NOT_FOUND, "Bankslip not found with the specified id " + uuid);
        }

        bankSlip.setStatus(BankSlipStatus.PAID);

        try {
            repository.save(bankSlip);
        } catch (Throwable exe) {
            LoggerFactory.getLogger(getClass()).error("Error in update bankslip to PAID", exe);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Error in update bankslip to PAID");
        }

        BankSlipResponse payBean = new BankSlipResponse(bankSlip.getId(), bankSlip.getStatus());
        return ResponseEntity.ok(payBean);
    }

    /**
     * Método alterá o status do BankSplit para CANCELED através de um request de DELETE
     *
     * @param UUID em String do BankSlip passado por parâmetro 
     * @return Resposta da Entidade retorna:
     *   - 400 - Invalid id provided - it must be a valid UUID
     *   - 200 - Cancelado com sucesso 
     *   - 304 - Error in update bankslip to PAID
     *   - 200 - ok
     */
	@RequestMapping(value = "/{uuid}/cancel", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity cancel(@PathVariable("uuid") String uuid) {
        UUID id;

        try {
            id = UUID.fromString(uuid);
        } catch (NullPointerException | IllegalArgumentException ex) {
        	return responseWithError(HttpStatus.BAD_REQUEST, "Invalid id provided - it must be a valid UUID");
        }

        BankSlip bankSlip = repository.findById(id).orElse(null);
        if (bankSlip == null) {
        	return responseWithError(HttpStatus.NOT_FOUND, "Bankslip not found with the specified id " + uuid);
        }

        bankSlip.setStatus(BankSlipStatus.CANCELED);
        
        try {
            repository.save(bankSlip);
        } catch (Throwable exe) {
            LoggerFactory.getLogger(getClass()).error("Error in update bankslip to CANCELED", exe);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Error in update bankslip to PAID");
        }
        
        BankSlipResponse cancelBean = new BankSlipResponse(bankSlip.getId(), bankSlip.getStatus());
        return ResponseEntity.ok(cancelBean);
    }

    /**
     * Método cria um novo bankslip através de um request de POST
     *
     * @param request contendo via Json um BankSlip passado por parâmetro 
     * @return Resposta da Entidade retorna:
     *   - 400 - Bankslip not provided in the request body
     *   - 422 - Invalid bankslip provided.The possible reasons are: [errorList]
     *   - 304 - Error to save bankslip
     *   - 201 - Bankslip created
     */
	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity createBankSlip(@RequestBody(required = false) BankSlipRequest request) {
		if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bankslip not provided in the request body");
        }
		
		// Faz a validação retornando uma lista de erros caso encontre algum
		List<String> errorList = Validation.buildDefaultValidatorFactory().getValidator().validate(request).stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
		
		if (CollectionUtils.isNotEmpty(errorList)) {
            return responseWithError(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid bankslip provided.The possible reasons are:", errorList);
        }
		
		// Cria uma nova BankSlip e salva no banco de dados
		BankSlip bankSlip = request.createBankSlip();
		try {
			repository.save(bankSlip);
		} catch (Throwable e) {
			LoggerFactory.getLogger(getClass()).error("Error to save bankslip", e);
            return responseWithError(HttpStatus.NOT_MODIFIED, "Error to save bankslip");
		}
		
		// Retorna a resposta bankslip criada
		BankSlipResponseMessage messageBean = new BankSlipResponseMessage(HttpStatus.CREATED.toString(), "Bankslip created");
		return ResponseEntity.status(HttpStatus.CREATED).body(messageBean);
	}
	
	private ResponseEntity<BankSlipResponseWithError> responseWithError(final HttpStatus status, final String errorMessage) {
		return responseWithError(status, errorMessage, null);
	}
	
	private ResponseEntity<BankSlipResponseWithError> responseWithError(final HttpStatus status, final String errorMessage, final List<String> errorList) {
		BankSlipResponseWithError error = new BankSlipResponseWithError(status.toString() , errorMessage, errorList);
		return new ResponseEntity<BankSlipResponseWithError>(error, status);
	}
	
}
