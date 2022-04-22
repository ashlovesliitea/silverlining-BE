package com.example.capstone.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseException extends Exception{
    private ResponseStatusCode statusCode;
}
