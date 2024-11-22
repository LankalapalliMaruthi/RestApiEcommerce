package com.spring.rest.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.startup.HomesUserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.rest.exceptions.EmployeeNotFoundException;
import com.spring.rest.models.Employees;
import com.spring.rest.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class EmployeeController {
@Autowired
EmployeeService es;

@PostMapping("/saveEmp")
public ResponseEntity<Employees> save(@Valid @RequestBody Employees emps ) {
	Employees emp= es.saved(emps);
	return  ResponseEntity.status(HttpStatus.CREATED)
			.header("employee status", "data saved successfully")
			.body(emps);
			
}
@PostMapping("/saveAllEmp")
public ResponseEntity<List<Employees>> allSave(@RequestBody List<Employees> emps) {
	 List< Employees> emp= es.allSaved(emps);
	return  ResponseEntity.status(HttpStatus.CREATED)
			.header("employee status", "data saved successfully")
			.body(emps);
			
}
@GetMapping("/findAll")
public ResponseEntity<List<Employees>> findAll(){
	List<Employees>emps=es.findAllEmp();
	return  ResponseEntity.status(HttpStatus.OK)
			.header("status", "information recieved")
			.body(emps);
}
@GetMapping("/findById/{id}")
public ResponseEntity<?> getById(@PathVariable Long id){
	Optional<Employees>emp=es.getbyId(id);
	if(emp.isPresent()) {
		Employees data=emp.get();
		 // Create an EntityModel for the user
		   EntityModel <Employees> entityModel = EntityModel.of(data);
		   Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getById(id)).withSelfRel();
	        entityModel.add(selfLink);

	        // Add link to get all employees
	        Link allEmployeesLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).findAll()).withRel("all-employees");
	        entityModel.add(allEmployeesLink);
            
	        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getById(id)).withRel("update");
	        entityModel.add(updateLink);
	        // Add link to delete this employee
	        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteById(id)).withRel("delete-employee");
	        entityModel.add(deleteLink);
		return ResponseEntity.status(HttpStatus.OK)
				.header("status", "information recieved")
				.body(entityModel);
		
	}	
	else {
	 throw new EmployeeNotFoundException("employee not found with id:"+id);
	}
}
	@GetMapping("/findByMail/{mail}")
	public ResponseEntity<?> findByMail(@PathVariable String mail){
		Optional<List<Employees>>emp=es.findByMail(mail);
		if(emp.isPresent()) {return ResponseEntity.status(HttpStatus.OK)
				.header("status", "information recieved")
				.body(emp.get());}
		else {return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.header("status", "not found")
				.body("not found");
			
		}
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?>deleteById(@PathVariable Long id){
		boolean status=es.deleteById(id);
		if(status) {
			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.header("notfound", "data is not found with "+id)
					.body("no data");
		}
	}
	
	@DeleteMapping("/DeleteByMail/{mail}")
	public ResponseEntity<?> deleteByMail(@PathVariable String mail){
		boolean status=es.deleteByMail(mail);
		if(status) {
			return ResponseEntity.noContent().build();
		}
		else {
			return  ResponseEntity.status(HttpStatus.NOT_FOUND)
					.header("notfound", "data is not found with "+mail)
					.body("no data");
		}
	}
	@DeleteMapping("/DeleteAll")
	public ResponseEntity<?> deleteAll(){
		boolean status=es.deleteAll();
		if(status) {
			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.status(HttpStatus.OK)
					.header("success", "all deleted")
					.body("no data");
		}
	}
	
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Employees newEmp,@PathVariable Long id){
    	Optional<Employees>emp=es.update(id,newEmp);
    	if(emp.isPresent()) {
    		return ResponseEntity.status(HttpStatus.OK)
    		            .header("success", " updated")
    				.body(emp);
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.OK)
		            .header("failed", " no data")
				.body("no data");
    	}
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<?> patching(@PathVariable Long id, @RequestBody Map<String, Object> newEmp) {
        Optional<Employees> updatedEmployee = es.patching(id, newEmp);
        
        if (updatedEmployee.isPresent()) {
            return ResponseEntity.ok(updatedEmployee.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        }
    }
    @GetMapping("/getnames")
    public List<String>names(){
    	return List.of("maruthi","sathya");
    }
    @GetMapping("/getdetails")
    public List<String>details(){
    	return es.names();
    }
    
    @PutMapping("updateByName/{name}")
    public ResponseEntity<?> updateByName(@RequestBody Employees emp,@PathVariable String name){
    	Optional<Employees>data=es.updateByName(emp,name);
    	if(data.isPresent()) {
    		return ResponseEntity.status(HttpStatus.OK)
    				.header("success","updated By name")
    				.body(data);
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND)
    				.header("failed","not found")
    						.body("no data found");
    	}
    }
    @PatchMapping("patchByName/{name}")
    public ResponseEntity<?> patchByName(@PathVariable String name ,@RequestBody Map<String,Object>emp){
    	Optional<Employees>data=es.patchByName(name,emp);
    	if(data.isPresent()) {
    		return ResponseEntity.status(HttpStatus.OK)
    				.header("success","updated By name")
    				.body(data);
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND)
    				.header("failed","not found")
    						.body("no data found");
    	}
    }
    
    
}