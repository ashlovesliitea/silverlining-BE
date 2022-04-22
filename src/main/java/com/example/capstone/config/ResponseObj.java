package com.example.capstone.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.capstone.config.ResponseStatusCode.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess","code","message","contents"})
@JsonIgnoreProperties({"success"})
public class ResponseObj<T> {

    @JsonProperty("isSuccess")
    private final boolean isSuccess;

    private final int code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T contents;

    public ResponseObj(T contents){
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getStatusCode();
        this.contents = contents;
    }


    public ResponseObj(ResponseStatusCode responseStatusCode){
        this.isSuccess=responseStatusCode.isSuccess();
        this.message=responseStatusCode.getMessage();
        this.code=responseStatusCode.getStatusCode();
    }

}
