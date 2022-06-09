package com.example.capstone;

import com.example.capstone.config.ResponseException;
import com.example.capstone.config.ResponseObj;
import static com.example.capstone.config.ResponseStatusCode.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;

import java.nio.charset.MalformedInputException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value= ResponseException.class)
    public ResponseObj BaseExceptionHandler(ResponseException e){
        System.out.println("오류발생");
        return new ResponseObj<>(e.getStatusCode());
    }

    @ExceptionHandler(value= { SQLException.class})
    public ResponseObj DatabaseExceptionHandler(){
        //db관련 exception
        return new ResponseObj<>(DATABASE_ERROR);
    }

    @ExceptionHandler(value= {DataAccessException.class})
    public ResponseObj DataAccessExceptionHandler(){
        //db관련 exception
        return new ResponseObj<>(DATA_NOT_FOUND);
    }

    @ExceptionHandler(value= GeneralSecurityException.class)
    public ResponseObj SecurityExceptionHandler(){
        //비밀번호 입력시 SHA256 암호화 에러 관련 핸들러
        return new ResponseObj<>(PASSWORD_ENCRYPTION_ERROR);
    }

    @ExceptionHandler(value= JsonProcessingException.class)
    public ResponseObj InvalidSocialLoginHandler(){
        return new ResponseObj(INVALID_SOCIAL_LOGIN);
    }

    @ExceptionHandler(value={JsonParseException.class})
    public ResponseObj JsonParseExceptionHandler(){
        return new ResponseObj(UNAVAILABLE_TO_PARSE_JSON);
    }

    @ExceptionHandler(value={HttpMessageNotReadableException.class})
    public ResponseObj requestNotReadableExceptionhandler(){
        return new ResponseObj(HTTP_MESSAGE_UNREADABLE);
    }

    //JsonParseException,MalformedJwtException,NestedServletException
    @ExceptionHandler(value={MalformedInputException.class})
    public ResponseObj JWTExceptionHandler(){
        return new ResponseObj(INVALID_JWT);
    }

    @ExceptionHandler(value= {NullPointerException.class,Exception.class})
    public void EtcExceptionHandler(Exception e){

        System.err.println(e);
    }

}
