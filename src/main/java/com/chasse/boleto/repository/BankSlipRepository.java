package com.chasse.boleto.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chasse.boleto.model.BankSlip;
import com.chasse.boleto.model.BankSlipStatus;

/**
 *	Interface Repositória da BankSlip 
 * 
 * @author Cléverson Hasse
 * @version 1.0.0
 * 
 */

@Repository
public interface BankSlipRepository extends CrudRepository<BankSlip, UUID> {

	BankSlip findByCustomer(String customer);

	boolean existsByCustomer(String string);

	void deleteByCustomer(String string);

	Iterable<BankSlip> findAllByStatus(BankSlipStatus pending);

}
