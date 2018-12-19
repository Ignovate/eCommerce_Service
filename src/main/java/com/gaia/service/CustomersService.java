package com.gaia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gaia.domain.CustomersAddrEntity;
import com.gaia.domain.CustomersEntity;
import com.gaia.repository.CustomersAddrRepo;
import com.gaia.repository.CustomersRepo;

@Service
public class CustomersService {

	@Autowired
	private CustomersRepo repo;
	@Autowired
	private CustomersAddrRepo customerAddressRepo;

	public CustomersEntity addCustomers(CustomersEntity request) {
		request.setActive(true);
		return repo.save(request);
	}

	public CustomersEntity getCustomers(Long id) {
		return repo.findById(id).orElse(null);
	}

	public CustomersEntity getCustomer(String email, String password) {
		Specification<CustomersEntity> spec = new Specification<CustomersEntity>() {
			@Override
			public Predicate toPredicate(Root<CustomersEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();
				predicate.add(criteriaBuilder.equal(root.get("email"), email));
				predicate.add(criteriaBuilder.equal(root.get("password"), password));
				return criteriaBuilder.and(predicate.stream().toArray(Predicate[]::new));
			}

		};
		return repo.findOne(spec).orElse(null);
	}

	public CustomersEntity getCustomer(Map<String, String> map) {
		Specification<CustomersEntity> spec = new Specification<CustomersEntity>() {
			@Override
			public Predicate toPredicate(Root<CustomersEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();
				map.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});
				return criteriaBuilder.and(predicate.stream().toArray(Predicate[]::new));
			}

		};
		return repo.findOne(spec).orElse(null);
	}

	public Page<CustomersEntity> getCustomers(Map<String, String> map, Pageable pageable, Long id, boolean isActive,
			boolean isPresent) {
		Specification<CustomersEntity> spec = new Specification<CustomersEntity>() {
			@Override
			public Predicate toPredicate(Root<CustomersEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();
				map.remove("active");

				if (id != null)
					predicate.add(criteriaBuilder.equal(root.get("id"), id));
				if (isPresent)
					predicate.add(criteriaBuilder.equal(root.get("active"), isActive));
				map.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});
				return criteriaBuilder.and(predicate.stream().toArray(Predicate[]::new));
			}

		};
		return repo.findAll(spec, pageable);

	}

	public void deleteCustomers(Long id) {
		repo.deleteById(id);
	}

	public CustomersAddrEntity getCustomerAddress(Long addressId) {
		return customerAddressRepo.findById(addressId).orElse(null);
	}

}
