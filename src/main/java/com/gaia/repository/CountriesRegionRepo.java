package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.CountriesRegionEntity;

public interface CountriesRegionRepo extends JpaRepository<CountriesRegionEntity, Long>, JpaSpecificationExecutor<CountriesRegionEntity>{

}
