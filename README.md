# [REST] 개발 정보
Rest 프로젝트 개발 정보입니다.

# 개발 일정
[...](...)

# 기획서 정보
[...](...)

# URI 정보
[URI 정의서]() 에는 기능에 따른 진행 상태와 작업자 정보 등을 확일할 수 있으니 참고 요청드립니다.

API 정보는 [**Domain**](#domain-정보), [**Document**](#document-정보) 가 있습니다.
[**Document**](#document-정보) 에는 테이블 명세서가 포함되어 있으니 참고 요청드립니다.

> ## Domain 정보
>> |서버            |URI                                                            |
>> |----------------|---------------------------------------------------------------|
>> |**개발**        |[...](...)                                                     |
>> |**스테이징**    |[...](...)                                                     |
>> |**운영**        |[...](...)                                                     |

> ## Document 정보
>> |서버            |URI                                                            |
>> |----------------|---------------------------------------------------------------|
>> |**개발**        |[.../docs](.../docs)                                           |
>> |**스테이징**    |[.../docs](.../docs)                                           |
>> |**운영**        |[.../docs](.../docs)                                           |

# Database 접속정보
> ## 개발 서버
>> ### MARIA DB
>> |Title           |Value                                                          |
>> |----------------|---------------------------------------------------------------|
>> |**Host**        |XXX.XXX.XXX                                                    |
>> |**Port**        |XXXXX                                                          |
>> |**Username**    |XXXX                                                           |
>> |**Password**    |XXXX                                                           |
>> |**Database**    |XXXX                                                           |
>> ### REDIS
>> |Title           |Value                                                          |
>> |----------------|---------------------------------------------------------------|
>> |**Host**        |XXX.XXX.XXX                                                    |
>> |**Port**        |XXXXX                                                          |
>> |**Username**    |XXXX                                                           |
>> |**Password**    |XXXX                                                           |
>> |**Database**    |XXXX                                                           |
> ## 스테이징 서버
>> ### MARIA DB
>> |Title           |Value                                                          |
>> |----------------|---------------------------------------------------------------|
>> |**Host**        |XXX.XXX.XXX                                                    |
>> |**Port**        |XXXXX                                                          |
>> |**Username**    |XXXX                                                           |
>> |**Password**    |XXXX                                                           |
>> |**Database**    |XXXX                                                           |
>> ### REDIS
>> |Title           |Value                                                          |
>> |----------------|---------------------------------------------------------------|
>> |**Host**        |XXX.XXX.XXX                                                    |
>> |**Port**        |XXXXX                                                          |
>> |**Username**    |XXXX                                                           |
>> |**Password**    |XXXX                                                           |
>> |**Database**    |XXXX                                                           |
> ## 운영 서버
>> ### MARIA DB
>> |Title           |Value                                                          |
>> |----------------|---------------------------------------------------------------|
>> |**Host**        |XXX.XXX.XXX                                                    |
>> |**Port**        |XXXXX                                                          |
>> |**Username**    |XXXX                                                           |
>> |**Password**    |XXXX                                                           |
>> |**Database**    |XXXX                                                           |
>> ### REDIS
>> |Title           |Value                                                          |
>> |----------------|---------------------------------------------------------------|
>> |**Host**        |XXX.XXX.XXX                                                    |
>> |**Port**        |XXXXX                                                          |
>> |**Username**    |XXXX                                                           |
>> |**Password**    |XXXX                                                           |
>> |**Database**    |XXXX                                                           |

> # FTP 접속 정보
> ## 개발 서버
>> |Title           |Value                                                          |
>> |----------------|---------------------------------------------------------------|
>> |**Protocol**    |SFTP                                                           |
>> |**Host**        |XXX.XXX.XXX                                                    |
>> |**Port**        |XXXXX                                                          |
>> |**Username**    |XXXX                                                           |
>> |**Password**    |XXXX                                                           |
>> |**Path**        |/XXX/XXX                                                       |
> ## 스테이징 서버
>> |Title           |Value                                                          |
>> |----------------|---------------------------------------------------------------|
>> |**Protocol**    |SFTP                                                           |
>> |**Host**        |XXX.XXX.XXX                                                    |
>> |**Port**        |XXXXX                                                          |
>> |**Username**    |XXXX                                                           |
>> |**Password**    |XXXX                                                           |
>> |**Path**        |/XXX/XXX                                                       |
> ## 운영 서버
>> |Title           |Value                                                          |
>> |----------------|---------------------------------------------------------------|
>> |**Protocol**    |SFTP                                                           |
>> |**Host**        |XXX.XXX.XXX                                                    |
>> |**Port**        |XXXXX                                                          |
>> |**Username**    |XXXX                                                           |
>> |**Password**    |XXXX                                                           |
>> |**Path**        |/XXX/XXX                                                       |

# 개발 서버 수동 배포 방법
**_WARNING:_** 파일 이름이 달라지면 이후 진행방법이 달라지기때문에 주의
- /app/rest 디렉토리 하위에 빌드한 rest-0.0.1-SNAPSHOT.jar 파일을 업로드
- 다음 command 를 실행
```shell
sudo service rest restart
```

# 개발 가이드
**_NOTE_**: PL 의 판단에 따라 프로젝트 개발 가이드는 변경될 수 있다.
- ## Code Convention
> - ### [Naming Rule](https://git.freshr.run/freshr/documents/code-convention/-/wikis/Naming-Rule)
> - ### [Java Code Convention](https://git.freshr.run/freshr/documents/code-convention/-/wikis/Java-Code-Conventio)
- ## 프로젝트 개발 가이드
> - ### 개발 환경
>> |           |                                                   |
>> |-----------|---------------------------------------------------|
>> |Framework  |Spring Boot                                        |
>> |Language   |Java 11 (이상)                                     |
>> |Build      |Maven                                              |
>> |Orm        |JPA & QueryDsl                                     |
>> |Test Code  |Controller Test 까지만 작성. 단위테스트 작성은 자유|
>> |API Docs   |Rest Docs 작성 필수                                |
>> |IDE        |IntelliJ (권장)                                    |
> - #### Project 생성
>> 1. [LITE Package](https://git.freshr.run/freshr/packages/rest) 를 내려받는다.
>> 2. Git URL 을 변경한다.  
      >> *Git > Manage Remotes... 에서 변경할 수 있다.*
>>
> - #### Project Intializr
>> Project 를 생성했다면 몇 가지 설정을 해야 한다.
> 1. ##### 파일 업로드
>> 파일 업로드 형식을 프로젝트에 맞춰 변경해야한다.
>>> - AWS S3 를 사용하는 형식
>>>> 1. pom.xml 에서 run.freshr:aws-s3:1.0.0.RELEASE 주석 해제
>>>> 2. run.freshr.service.CommonService.java 파일에서 S3 기능 관련 주석 해제
>>> - 서버에 직접 업로드 하는 형식
>>>> 1. pom.xml 에서 run.freshr:attach:1.0.0.RELEASE 주석 해제
>>>> 2. run.freshr.service.CommonService.java 파일에서 물리 파일 기능 관련 주석 해제
> 2. ##### 접속 정보
> 프로젝트에 맞춰 접속 정보를 변경해야 한다.
>> 1. README.md 파일을 프로젝트 정보에 맞춰 수정한다.
>> 2. application-*.yml 파일을 프로젝트 정보에 맞춰 수정한다.
> - ### Code Convention
>> 기본적으로는 위에 링크된 (공통 코드 컨벤션)[## Code Convention] 을 따른다.  
>> 여기에 작성된 Code Convention 은 프로젝트 특징과 개발환경에 맞춰 작성된 Code Convention 이다.
>> - Domain
>>> main > java > run.freshr.domain 에 생성되는 Package 와 Class 는 다음과 같은 Naming Rule 로 생성한다.
>>> ```
>>> domain
>>>    └─ {{GROUP_NAME}}
>>>       ├─ dto
>>>       │  ├─ request
>>>       │  │  └─ [EntityName + CRUD + <명사 또는 By 또는 For> + <명사 또는 By 또는 For>...]Request.java
>>>       │  ├─ response
>>>       │  │  └─ [EntityName + CRUD + <명사 또는 By 또는 For> + <명사 또는 By 또는 For>...]Cache.java
>>>       │  └─ cache
>>>       │     └─ [RedisHashName + CRUD + <명사 또는 By 또는 For> + <명사 또는 By 또는 For>...]Cache.java
>>>       ├─ entity
>>>       │  ├─ [EntityName].java
>>>       │  └─ inheritance
>>>       │     └─ [EntityName].java
>>>       ├─ enumeration
>>>       │  └─ [<EntityName 또는 RedisHashName> + <Type Name 또는 Field Name>].java
>>>       ├─ redis
>>>       │  └─ [RedisHashName].java
>>>       ├─ repository
>>>       │  ├─ [<EntityName 또는 RedisHashName>]Repository.java
>>>       │  ├─ [EntityName]RepositoryCustom.java
>>>       │  └─ [EntityName]RepositoryImpl.java
>>>       ├─ service
>>>       │  └─ [<EntityName 또는 RedisHashName>]Service.java
>>>       ├─ validator
>>>       │  └─ {{GROUP_NAME}}Validator.java
>>>       └─ vo
>>>          └─ {{GROUP_NAME}}Search.java
>>> ```
>> - Controller & Service
>>> main > java > run.freshr.controller, run.freshr.service 에 생성되는 Package 와 Class 는 다음과 같은 Naming Rule 로 생성한다.
>>> ```
>>> ├─ controller
>>> │  └─ {{GROUP_NAME}}Controller.java
>>> └─ service
>>>    └─ {{GROUP_NAME}}Service.java
>>> ```
>> - Docs
>>> main > asciidocs 에 생성되는 파일은 다음과 같은 Naming Rule 로 생성한다.
>>> ```
>>> └─ docs
>>>    └─ {{GROUP_NAME}}.adoc
>>> ```
>> - Test
>>> test > java 에 생성되는 Package 와 Class 는 다음과 같은 Naming Rule 로 생성한다.
>>> ```
>>> ├─ controller
>>> │  └─ {{GROUP_NAME}}ControllerTest.java
>>> └─ domain
>>>    └─ {{GROUP_NAME}}
>>>       └─ <EntityName 또는 RedisHashName>Docs.java
>>> ```
>> - Method
>>> Method Naming Rule 은 다음과 같다.  
>>> [CRUD + EntityName + <명사 또는 By 또는 For> + <명사 또는 By 또는 For>...]


- ## Module 가이드
> - ### Utils
>> [가이드 문서](https://git.freshr.run/freshr/modules/core/utils)
>> ``` xml
>> <dependency>
>>     <groupId>run.freshr</groupId>
>>     <artifactId>utils</artifactId>
>>     <version>1.0.0.RELEASE</version>
>> </dependency>
>> ```
> - ### Enum Mapper
>> [가이드 문서](https://git.freshr.run/freshr/modules/core/enum-mapper)
>> ``` xml
>> <dependency>
>>     <groupId>run.freshr</groupId>
>>     <artifactId>enum-mapper</artifactId>
>>     <version>1.0.0.RELEASE</version>
>> </dependency>
>> ```
> - ### Exceptions
>> [가이드 문서](https://git.freshr.run/freshr/modules/core/exceptions)
>> ``` xml
>> <dependency>
>>     <groupId>run.freshr</groupId>
>>     <artifactId>exceptions</artifactId>
>>     <version>1.0.0.RELEASE</version>
>> </dependency>
>> ```
> - ### Entity Comment
>> [가이드 문서](https://git.freshr.run/freshr/modules/docs/entity-comment)
>> ``` xml
>> <dependency>
>>     <groupId>run.freshr</groupId>
>>     <artifactId>entity-comment</artifactId>
>>     <version>1.0.0.RELEASE</version>
>> </dependency>
>> ```
> - ### Search Comment
>> [가이드 문서](https://git.freshr.run/freshr/modules/docs/search-comment)
>> ``` xml
>> <dependency>
>>     <groupId>run.freshr</groupId>
>>     <artifactId>search-comment</artifactId>
>>     <version>1.0.0.RELEASE</version>
>> </dependency>
>> ```
> - ### Rest Docs Auto Maker 
>> [가이드 문서](https://git.freshr.run/freshr/modules/docs/rest-docs)
>> ``` xml
>> <dependency>
>>     <groupId>run.freshr</groupId>
>>     <artifactId>rest-docs</artifactId>
>>     <version>1.0.0.RELEASE</version>
>> </dependency>
>> ```
> - ### AWS S3
>> [가이드 문서](https://git.freshr.run/freshr/modules/util/aws-s3)
>> ``` xml
>> <dependency>
>>     <groupId>run.freshr</groupId>
>>     <artifactId>aws-s3</artifactId>
>>     <version>1.0.0.RELEASE</version>
>> </dependency>
>> ```
> - ### AWS CloudFront
>> [가이드 문서](https://git.freshr.run/freshr/modules/util/aws-cloud-front)
>> ``` xml
>> <dependency>
>>     <groupId>run.freshr</groupId>
>>     <artifactId>aws-cloud-front</artifactId>
>>     <version>1.0.0.RELEASE</version>
>> </dependency>
>> ```
> - ### 물리 파일 관리
>> [가이드 문서](https://git.freshr.run/freshr/modules/util/attach)
>> ``` xml
>> <dependency>
>>     <groupId>run.freshr</groupId>
>>     <artifactId>attach</artifactId>
>>     <version>1.0.0.RELEASE</version>
>> </dependency>
>> ```

# DB 초기화 및 초기 데이터 설정
**_WARNING:_** PL 이 진행하거나 PL 감독하에 진행하는 것을 권장합니다.  
개발 서버는 내부에서 해결이 가능하지만 운영서버가 초기화된다면 방법이 없습니다.  
그렇기 때문에 혹시 모를 불상사를 방지하기 위하여 DB 를 백업받는 것을 권장합니다.

- application.yml 파일수정
> 초기화하고자 하는 정보의 profile 로 수정
> ``` yaml
> spring:
>   profiles:
>     active: CHANGE_PROFILE
> ```
- 선택한 profile 의 ddl-auto 변경
> ``` yaml
> spring:
>   jpa:
>     hibernate:
>       ddl-auto: create
> ```
- DataRunner @Profile 설정 변경
> ``` java
> @Profile("CHANGE_PROFILE")
> @Component
> public class DataRunner implements ApplicationRunner {
> ```
- CommonControllerTest 의 dummy() 메서트 주석 해제
> ``` java
>     @Test
>     @DisplayName("개발 DB 더미 데이터 생성")
>     public void dummy() {
>         System.out.println("Insert dummy");
>     }
> ```
- CommonControllerTest.dummy 실행