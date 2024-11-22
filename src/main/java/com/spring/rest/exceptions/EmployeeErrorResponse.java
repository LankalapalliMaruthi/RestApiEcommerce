package com.spring.rest.exceptions;

import java.time.LocalDateTime;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeErrorResponse {
private LocalDateTime timeStamp;
private String msg;
private int status;
}
