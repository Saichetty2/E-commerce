package com.cart.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseMessage {

    private String message;
    private boolean success;
    private HttpStatus status;
}
