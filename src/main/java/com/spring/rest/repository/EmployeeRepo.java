package com.spring.rest.repository;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.rest.models.Employees;

import jakarta.transaction.Transactional;

public interface EmployeeRepo extends JpaRepository<Employees, Long>{

	Optional<List<Employees>> findByMail(String mail);

	
	  @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM Employees e WHERE e.mail = :mail" ) 
	  boolean existsByMail(String mail);
	 
	/*
	 * @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.mail = :mail") boolean
	 * existsByMail( String mail);
	 */

       @Transactional
	void deleteByMail(String mail);


	Optional<Employees> findByName(String name);


	

}
