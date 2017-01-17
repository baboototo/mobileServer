package com.baboototo.common;

/**
 * Constant
 * @author baboototo
 *
 */
public class Constant {

    public static final String HEADER_TOKEN_KEY = "X-Auth-BABOOTOTO-Token";      // Request, Response 토큰을 담는 Header Key 값
    
	public static final int HTTP_STATUS_OK 						= 200;		// 요청성공
	public static final int HTTP_STATUS_UNAUTHENTICATION 		= 400;		// 인증 정보 
	public static final int HTTP_STATUS_AUTHENTICATIONFAILURE	= 410;		// 로그인없음 실패
	public static final int HTTP_STATUS_ACCESSFAILURE 			= 440;		// 접근 권한 없음
	
	public static final int HTTP_LOGIN_OK 						= 10001;	// 로그인 정상
	public static final int HTTP_LOGIN_ERR_ID 					= 10002;	// 로그인 오류: 아이디 없음
	public static final int HTTP_LOGIN_ERR_IDS 					= 10003;	// 로그인 오류: 아이디 중복
	public static final int HTTP_LOGIN_ERR_PS 					= 10004;	// 로그인 오류: 패스워드
	
	public static final int HTTP_LOGIN_OUT_OK 					= 11001;	// 로그아웃 정상
	
	public static final int HTTP_TOKEN_OK 						= 20001;	// 토큰 정상
	public static final int HTTP_TOKEN_ERR_EXPIRE				= 20002;	// 토큰 만료
	
	public static final String RESULT_CODE 						= "CODE";	// 코드
	public static final String RESULT_DATA 						= "DATA";	// 데이터
	public static final String RESULT_OK						= "OK";		// 정상
	public static final String RESULT_ERROR						= "ERROR";	// 에러
	public static final String RESULT_MESSAGE 					= "MESSAGE";// 결과 메세지
	
}
