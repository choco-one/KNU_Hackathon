# ![image](https://user-images.githubusercontent.com/31034469/126733913-95394ad9-4303-4638-bf95-f06a00f64e68.png)
--------------------
# 프로젝트 주제

#### 코로나-19로 인해, 2020년 부터 접촉을 최소화하기 위한 비대면 수업 및 집합금지로 인해, 
#### 새내기, 재학생, 졸업생의 만남이 줄어들었습니다. 
#### 학부에서 진행되는 선후배간 멘토링도 학기에 한번씩 정해진 한팀만 진행되기에,
#### 같은 학과 내의 선후배간 만남에 크게 도움이 되지 못했습니다.

<br/>

#### 저희 팀은 이를 문제로 여기고, 선후배간, 나아가 졸업한 선배들과의 소통의 장을 열기 위해
#### '랜덤 멘토링' 어플을 제작하였습니다!!

--------------------
# Project

#### 저희 프로젝트는 다음의 기능을 제공합니다

* Sign in / Sign up
  - FireBase의 Authentication을 활용한 회원가입 및 이메일 인증 기능
  - FireBase의 Authentication을 활용한 로그인 기능

* 실시간 랜덤 채팅 기능
  - 재학생 또는 졸업생과의 멘토링 선택
  - 해당하는 조건에 맞춰 FireStore DataBase에 저장되어 있는 랜덤 매칭 대기열에 등록
  - 대기 중 해당 하는 조건의 상대방이 나타나면 FireBase의 Real Time DataBase 채팅방 생성

* My Page
  - FireStore DataBase에 저장되어 있는 개인 정보 조회 및 수정 기능
  - 로그아웃 기능 구현 예정

* API
  - Spring Framework를 이용해 Android Client와 FireBase DataBase에 사용되는 API 구현

<br/>
  
  ```
    @PostMapping(value = "/api/user/join",consumes = "application/x-www-form-urlencoded")
    public String join(@RequestParam Map map) throws Exception{
        return userService.join(new JoinRequest(map));
    }

    @GetMapping("/api/user/{email}")
    public User info(@PathVariable String email) throws Exception {
        return userService.info(email);
    }

    @PutMapping(value = "/api/user/{email}")
    public String update(@PathVariable String email, @RequestParam Map map) throws Exception{
        return userService.update(email, new UpdateRequest(map));
    }

    @PutMapping(value = "/api/user/friend/{email}")
    public String addFriend(@PathVariable String email, @RequestParam Map map) throws Exception{
        return userService.addFriend(email, map);
    
    @PostMapping("/api/matching/add")
    public String add(@RequestParam Map map) throws Exception{
        return matchingService.add(new AddRequest(map));
    }

    @PostMapping("/api/matching/gadd")
    public String gadd(@RequestParam Map map) throws Exception{
        return matchingService.gadd(new GAddRequest(map));
    }
  ```
--------------------
# Contributor

+ 최재원 - [ONE](https://github.com/choi-jaewon)
+ 송유나 - [yunasong](https://github.com/YunaSong97)
+ 정대규 - [DDAGUE](https://github.com/DDAGUE)
+ 고재철 - [Jfe](https://github.com/Go-Jaecheol)
