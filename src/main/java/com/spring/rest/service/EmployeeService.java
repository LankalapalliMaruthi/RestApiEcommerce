package com.spring.rest.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.startup.HomesUserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.rest.models.Employees;
import com.spring.rest.repository.EmployeeRepo;

@Service

public class EmployeeService {
@Autowired
EmployeeRepo er;

public Employees saved(Employees emp) {
	Employees emps=er.save(emp);
	return emps;
	
}

public List<Employees> allSaved(List<Employees> emps) {
	// TODO Auto-generated method stub
	List<Employees> emp=er.saveAll(emps);
	return emp;
}

public List<Employees> findAllEmp() {
	List<Employees>emps=er.findAll();
	return emps;
}

public Optional<Employees> getbyId(Long id) {
	      Optional<Employees>emp=er.findById(id);
	return emp;
}

public Optional<List<Employees>> findByMail(String mail) {
	Optional<List<Employees>>emp=er.findByMail(mail);
	return emp;
}

public boolean deleteById(Long id) {
	boolean status=er.existsById(id);
	if(status) {
		er.deleteById(id);
		return true;
	}
	else {
	return false;}
}

public boolean deleteByMail(String mail) {
	boolean status=er.existsByMail(mail);
	
   if(status) {
	   er.deleteByMail(mail);
	   return true;
   }
   else {
	   return false;
   }
}

public boolean deleteAll() {
	Long cnt =er.count();
			er.deleteAll();
	Long delCnt=er.count();
	if(cnt>delCnt) {
		return true;
	}
	else {
	return false;
}}

public Optional<Employees> update(Long id, Employees newEmp) {
	Optional<Employees>emp=er.findById(id);
	if(emp.isPresent()) {
		Employees existEmp=emp.get();
		existEmp.setName(newEmp.getName());
		existEmp.setDept(newEmp.getDept());
		existEmp.setAddress(newEmp.getAddress());
		existEmp.setMail(newEmp.getMail());
		existEmp.setSal(newEmp.getSal());
		Employees updatedEmp=er.save(existEmp);
		return Optional.of(updatedEmp);
	}
	else {
		return Optional.empty();
	}
}

public Optional<Employees> patching(Long id, Map<String, Object> newEmp) {
	Optional<Employees>OptionalData=er.findById(id);
	if(OptionalData.isPresent()) {
		Employees existData=OptionalData.get();
		newEmp.forEach((key,value)->{switch(key) {
		case "name":
			existData.setName((String)value);
			break;
		case "dept":
			existData.setDept((String)value);
			break;
		case "address":
			existData.setAddress((String)value);
			break;
		case "mail":
			existData.setMail((String)value);
			break;
		case "sal":
			existData.setSal((Double)value);
			break;
		}});
		Employees updated=er.save(existData);
		return Optional.of(updated);
	}
	else {
		return Optional.empty();
		
	}
}

@Cacheable("names")
public List<String> names() {
	System.out.println("fetch the names");
	return List.of("maruthi","AA");
}

public Optional<Employees> updateByName(Employees emp, String name) {
	Optional<Employees>optionalData=er.findByName(name);
	if(optionalData.isPresent()) {
		Employees existData=optionalData.get();
		existData.setName(emp.getName());
		existData.setAddress(emp.getAddress());
		existData.setDept(emp.getDept());
		existData.setMail(emp.getMail());
		existData.setSal(emp.getSal());
		Employees updated=er.save(existData);
		return Optional.of(updated);
	}
	return Optional.empty();
}

public Optional<Employees> patchByName(String name, Map<String, Object> emp) {
	Optional<Employees>optionaldata= er.findByName(name);
	if(optionaldata.isPresent()) {
		Employees existData=optionaldata.get();
		emp.forEach((k,v)->{
			switch(k) {
			case "name":
				existData.setName((String)v);
			
		case "dept":
			existData.setDept((String)v);
			break;
		case "address":
			existData.setAddress((String)v);
			break;
		case "mail":
			existData.setMail((String)v);
			break;
		case "sal":
			existData.setSal((Double)v);
			break;
		}});
		Employees updated= er.save(existData);
		return Optional.of(updated);
	}
	return Optional.empty();
}




}
