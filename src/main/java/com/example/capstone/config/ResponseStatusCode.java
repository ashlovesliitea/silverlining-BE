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
    ABLE_TO_MODIFY(true, 1001, "구인 공고를 수정할 수 있는 권한이 있습니다."),

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
    NO_USER_JOINED_BY_THIS_PHONE_NUM(false,2011,"해당 번호로 가입된 유저가 없습니다."),
    ALREADY_FRIEND(false,2012,"이미 친구로 등록된 유저입니다."),
    INVALID_USER_IDX(false,2013,"존재하지 않는 유저입니다."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_EMAIL_DOESNT_EXIST(false,2018,"가입되지 않은 이메일입니다."),

    NOT_FOR_PERSONAL_USERS(false,2030,"기업회원을 위한 서비스입니다. 일반 회원은 접근이 불가합니다."),
    ALREADY_APPLIED(false,2031,"이미 지원하신 공고입니다."),
    ALREADY_LIKED_POLICY(false,2032,"이미 즐겨찾기 하신 정책입니다."),
    ALREADY_LIKED_JOB(false,2033,"이미 즐겨찾기 하신 구인공고입니다."),
    ALREADY_JOINED_PHONE(false,2034,"이미 가입된 전화번호입니다."),

    //유저 비번, 유저 직무 경험, 유저 운전 여부, 유저 주소+ 유저 위경도, 유저 보험 여부, 유저 중위 소득, 유저 프로필 이미지, 유저 질병 여부, 관심 분야
    MODIFY_PW_FAIL(false,2050,"비밀번호 수정에 실패하였습니다."),
    OLD_PW_DOESNT_MATCH(false,2058,"오래된 비밀번호가 틀렸습니다."),
    NEW_PW_CHECK_FAIL(false,2059,"새로운 비밀번호와 재확인 비밀번호가 불일치합니다."),
    MODIFY_EXP_FAIL(false,2051,"직무 경험 수정에 실패하였습니다"),
    MODIFY_DRIVE_FAIL(false,2052,"운전 여부 수정에 실패하였습니다"),
    MODIFY_ADDRESS_FAIL(false,2053,"유저 주소 수정에 실패하였습니다"),
    MODIFY_INSURANCE_FAIL(false,2054,"유저 보험 여부 수정에 실패하였습니다"),
    MODIFY_INCOME_FAIL(false,2055,"유저 중위 소득 수정에 실패하였습니다"),
    MODIFY_DISEASE_FAIL(false,2056,"유저 질병 여부 수정에 실패하였습니다"),
    MODIFY_INTEREST_FAIL(false,2057,"유저 관심 분야 수정에 실패하였습니다"),
    MODIFY_DETAIL_ADDRESS_FAIL(false,2058,"유저 상세 주소 수정에 실패하였습니다."),
    MODIFY_GUARDIAN_FAIL(false,2059,"유저의 보호자 연락처 수정에 실패하였습니다."),

    //유저 비번, 유저 직무 경험, 유저 운전 여부, 유저 주소+ 유저 위경도, 유저 보험 여부, 유저 중위 소득, 유저 프로필 이미지, 유저 질병 여부, 관심 분야
    MODIFY_TITLE_FAIL(false,2080,"공고 제목 수정에 실패하였습니다."),
    MODIFY_AGE_FAIL(false,2081,"공고 연령 수정에 실패하였습니다"),
    MODIFY_GENDER_FAIL(false,2082,"공고 성별 수정에 실패하였습니다"),
    MODIFY_WAGE_FAIL(false,2083,"공고 수당 수정에 실패하였습니다"),
    MODIFY_TIME_FAIL(false,2084,"공고 업무시간 수정에 실패하였습니다"),
    MODIFY_PERSONNEL_FAIL(false,2085,"공고 정원 수정에 실패하였습니다"),
    MODIFY_DETAIL_FAIL(false,2086,"공고 상세내용 수정에 실패하였습니다"),
    MODIFY_OFFER_STATUS_FAIL(false,2087,"공고 모집여부 수정에 실패하였습니다"),
    MODIFY_JOB_ADDRESS_FAIL(false,2088,"공고 주소 수정에 실패하였습니다"),

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
