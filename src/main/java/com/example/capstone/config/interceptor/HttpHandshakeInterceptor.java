package com.example.capstone.config.interceptor;

import com.example.capstone.config.ResponseException;
import com.example.capstone.config.ResponseStatusCode;
import com.example.capstone.utils.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.simp.stomp.StompHeaders.SESSION;

@AllArgsConstructor
public class HttpHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler handler, Map attributes) throws ResponseException {
        //클라이언트의 연결 요청이 들어오면 3번의 handshake에서 호출된다.
        /*서블릿은 자바로 만들어진 프로그램을 서버에서 실행하기 위해 만들어졌다.
        특히 웹 서비스 개발에 특화되어 있으며 데이터베이스 연동, 외부 서비스 연동을 통해 정적인 웹에 동적인 정보 제공을 가능하게 한다.*/
        if(request instanceof ServletServerHttpRequest){
           HttpHeaders headers=request.getHeaders();
            String accessToken=headers.getFirst("X-ACCESS-TOKEN");
            int userIdx= jwtService.getUserIdx2(accessToken);
            attributes.put("userIdx",userIdx);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,ServerHttpResponse response,
                               WebSocketHandler handler,Exception ex){

    }
}
