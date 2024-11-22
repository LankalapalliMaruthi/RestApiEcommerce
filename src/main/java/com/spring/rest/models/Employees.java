/*
 * package com.spring.rest.models;
 * 
 * import org.hibernate.validator.constraints.NotBlank;
 * 
 * import jakarta.persistence.Entity; import jakarta.persistence.GeneratedValue;
 * import jakarta.persistence.GenerationType; import jakarta.persistence.Id;
 * import jakarta.validation.constraints.Size; import lombok.AllArgsConstructor;
 * import lombok.Data; import lombok.NoArgsConstructor;
 * 
 * @Data
 * 
 * @AllArgsConstructor
 * 
 * @NoArgsConstructor
 * 
 * @Entity
 * 
 * public class Employees {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
 * 
 * @NotBlank(message="name cannot be blank")
 * 
 * @Size(min=2,max=100,message="name must be between 2 and 100 characters")
 * private String name; private Double sal; private String dept; private String
 * address; private String mail;
 * 
 * }
 */

package com.spring.rest.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employees {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @NotNull(message = "Salary cannot be null")
    @Min(value = 1000, message = "Salary must be at least 1000")
    @Max(value = 100000, message = "Salary must not exceed 100000")
    private Double sal;
    
    @NotBlank(message = "Department cannot be blank")
    private String dept;
    
    @NotBlank(message = "Address cannot be blank")
    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    private String address;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String mail;
}
