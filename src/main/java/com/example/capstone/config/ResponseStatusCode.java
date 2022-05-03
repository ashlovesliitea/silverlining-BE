package com.example.capstone.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseStatusCode{
    /**
     * 1000: Request 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    UNAVAILABLE_TO_PARSE_JSON(false,2004,"잘못된 형태의 JSON 요청입니다. 요청 형식을 다시 확인해주세요."),
    INVALID_SOCIAL_LOGIN(false,2005,"잘못된 소셜 로그인 요청입니다. 다시 시도해주세요."),
    HTTP_MESSAGE_UNREADABLE(false,2006,"JSON값을 파싱할 수 없습니다. 다시 시도해주세요."),
    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_EMAIL_DOESNT_EXIST(false,2018,"가입되지 않은 이메일입니다."),

    NOT_FOR_PERSONAL_USERS(false,2030,"기업회원을 위한 서비스입니다. 일반 회원은 접근이 불가합니다."),
    ALREADY_APPLIED(false,2031,"이미 지원하신 공고입니다."),
    ALREADY_LIKED_POLICY(false,2032,"이미 즐겨찾기 하신 정책입니다."),
    ALREADY_LIKED_JOB(false,2033,"이미 즐겨찾기 하신 구인공고입니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),
    DATA_NOT_FOUND(false,4002,"해당 데이터가 존재하지 않습니다."),
    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    FAIL_TO_CREATE_JOB(false,4013,"구인 공고를 생성하는데 실패했습니다.");

    private final boolean isSuccess;
    private final int statusCode;
    private final String message;
}
