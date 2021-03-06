package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.SalesQuote;

public interface SalesQuoteRepo
		extends JpaRepository<SalesQuote, Long>, JpaSpecificationExecutor<SalesQuote> {

}
