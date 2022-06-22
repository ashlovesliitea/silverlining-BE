#Silverlining Project
- Silverlining은 "희망" 이라는 뜻의 단어 silverlining에서 따온 이름으로, <br>
  최근 빠른 속도로 증가하고 있는 시니어 1인 가구의 자립을 돕기 위해 <br>
  가장 실생활에 밀접하게 연계될 수 있는 네가지 키워드(일자리, 건강, 국가정책, 관계단절)를 선정하고,<br>
  관련된 서비스들을 사용자 맞춤형으로 제공할 수 있도록 구현한 어플리케이션입니다.
- 해당 Repository에서는 Silverlining 주요 REST API 50여개를 구현한 내용을 확인하실 수 있습니다.
    - 주요 기능
        - 회원 관련 기능
        - 노년층 구인구직 플랫폼 기능
        - 주변 의료/복지시설 안내 기능
        - 맞춤형 노인 관련 정책/지원사업 알리미 기능
        - 유저간 안부 묻기 (채팅) 기능
        - 마이페이지 기능
- 인하대학교 컴퓨터공학종합설계 제출작품입니다.
- 프로젝트 진행 기간 : 2022.04 ~ 2022.06


## 1. 기술 스택
![image](https://user-images.githubusercontent.com/65891711/175073771-b1edd892-04a2-43d7-b679-6692e7f37fa5.png)

1) Backend 
   - Spring Boot & Tomcat
    <br> +) 채팅 기능 : Spring Webflux & Netty
2) Database 
   - AWS RDS Database - MySQL 8.0.28 ver
    <br> +) 채팅 기능 : MongoDB Atlas
3) Server
    - Deploy : AWS EC2 Linux instance
        - OS : Ubuntu 20.04
        - Web Server : Nginx
3) Domain : Gabia
4) Frontend : React Native & HTML/CSS/JS 


<br>

## 2. 프로젝트 ERD 명세서
![image](https://user-images.githubusercontent.com/65891711/175077278-eeecbdc6-e5fe-4e2b-b9ec-209b6465f828.png)


    URL : https://aquerytool.com/aquerymain/index/?rurl=6cdeca07-5fbd-4434-a836-6f6af64a8c9b&
    Password : k3g74g

<br>

## 3. 프로젝트 API 명세서
 
- API 명세서는 아래 링크에서 확인하실 수 있습니다.
    - [Silverlining API 명세서 링크](https://docs.google.com/spreadsheets/d/1HNS7wKWTMWdajW-vgZdG2_wL1ba2Hm0p/edit?usp=sharing&ouid=117173717945581994159&rtpof=true&sd=true)



## 4. Postman Workspace
- 하단 링크의 Postman Workspace를 통해 구현된 API를 직접 테스트해볼 수 있습니다. 
    - [Silverlining REST API Postman Workspace 링크](https://go.postman.co/workspace/My-Workspace~2076f76c-8fbc-4f7f-8345-829fd66ca175/collection/19267267-e76a74b6-735e-47ca-bb33-917b00c1c10f?action=share&creator=19267267)


## 5. 프로젝트 시연 동영상
- 어플리케이션 시연 영상 링크는 하단 유튜브 링크를 확인해 주세요.
    - [Silverlining Application 시연 동영상 링크](https://youtu.be/qM8MSkGH6R0)
<br>



## 6. 개발일지 
- 전체적인 개발 & 협업 과정과 이슈 사항을 정리한 개발 일지는 하단 노션 페이지 링크에서 확인하실 수 있습니다.
 - https://pinnate-goldfish-74c.notion.site/d1d11ab2adac4b938c31c700e2b1bb4f



###:point_down: 프로젝트에 대해 전반적인 정보가 더 궁금하시다면 하단 링크를 참고해주세요! <br>
- 프로젝트 최종 보고서 & 발표자료
  - 프로젝트 구현 동기, 요구사항, 역할 분담, 프론트엔드/백엔드/데이터 크롤링 파트 상세구현 및 고찰 등을 확인하실 수 있습니다.
  - Link1 : [최종 보고서 링크](https://docs.google.com/document/d/1ayuLWrQsmPCM-mrkjZgttW0uWZ1_-ErK/edit?usp=sharing&ouid=117173717945581994159&rtpof=true&sd=true)
  - Link2 : [발표자료 링크](https://www.miricanvas.com/v/1x4d73)
- Application 사용 방법을 정리한 README 문서
    -  [README](https://docs.google.com/document/d/17Qh7C_ieFNpOlDuZiuxv_mC0hYgzy6gg/edit?usp=sharing&ouid=117173717945581994159&rtpof=true&sd=true)