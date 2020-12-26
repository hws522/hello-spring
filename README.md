# 스프링부트, 웹 MVC, DB 접근기술에 대한 넓고 얕은 기본 공부. 

## Note

### start.spring.io

- Maven, Gradle 라이브러리를 가져오고, 관리해주는 툴. 요즘은 Gradle.

- spring boot version 은 정식릴리즈 중에서 2.3.x

- Group - 기업도메인명을 보통 씀.

- Artifact - 결과물

- Dependencies - (어떤 라이브러리를 가져다 쓸것인지) spring web, thymeleaf


**spring 파일 내 project.**

* .idea = 인텔리제이 사용하는 설정파일.

- gradle = 그래들 관련 폴더

- src = main, test 나누어져있음. (요즘 개발 트렌드에서 test코드가 중요하다.)

- resources = java 파일 제외하고 다 이쪽. (html, xml …)

- build.gradle = 요즘은 springboot에서 편하게 설정 제공.

- dependencies - 테스트 라이브러리와 설정했던 라이브러리들이 들어가 있음. 

- repositories - mavenCentral()에서 필요 라이브러리를 다운받으라고 설정 되있음. 필요하면 다른 url추가가능.

- .gitignore = 소스코드 말고는 올라가면 안되서 기본적으로 설정되있음.

- gradlew, gradlew.bat = 그래들로 빌드할 때.

 

### view 환경설정

- static/index.html을 올려두면 Welcome page 기능을 제공한다.

- 템플릿엔진을 쓰면 (thymeleaf 사용) 정적인 페이지(html)를 원하는대로 모양을 바꿀 수 있음.

- 웹어플리케이션 첫 진입점 = 컨트롤러.

  - helloController 에서 return 해준 hello 값은 templates 안의 hello.html

- thymeleaf 템플릿엔진을 사용할 때, <html xmlns:th="http://www.thymeleaf.org"> 이렇게 써줌. 

- 타임리프의 장점중 하나로, <p th:text="'hello'+${name}">hello!empty</p> 

  html 파일을 서버없이 그냥 열어봐도 내용을 볼 수 있음. 그때 hello! empty 라고 나오고, 템플릿으로서 작동을 하면 'hello'+${name}값으로 치환됨.

- 서버 배포 할 때는 hello-spring-0.0.1-SNAPSHOT.jar 파일만 복사해서 서버에 넣고 실행하면 됨. 그러면 서버에서도 스프링이 동작함.

 

### 스프링 웹 개발 기초

- 정적 컨텐츠 : 서버에서 하는 거 없이 파일 그대로 웹브라우저에 내려주는 것.

  - 스프링에서는 정적컨텐츠를 자동으로 제공함. 

    localhost:8080/hello-static.html -> 내장 톰캣에서 스프링 컨테이너로 hello-static 관련 컨트롤러를 찾음.

    컨트롤러가 없으면 resources에서 static/hello-static.html 파일을 찾아서 웹브라우저로 반환.

   

- MVC와 템플릿 엔진 : 가장많이 하는 방식. jsp, php 같은 게 템플릿 엔진. 서버에서 프로그래밍해서 html을 동적으로 바꿔서 내리는 것. 모델 뷰 컨트롤러.

  - 정적 컨텐츠는 파일을 그대로 고객에게 전달하는 방식이고 mvc와 템플릿 엔진은 서버에서 변형해서 내려주는 방식.

    MVC와 템플릿 엔진 = MVC 는 model, view, controller

    예전에는 뷰에 다 넣었음. (모델1 방식) 지금은 MVC 가 기본.

    View 는 화면을 그리는데에 모든 역량을 집중. Controller나 Model은 비지니스로직과 관련이 있거나, 내부적인건 처리하는데 집중해야함. 

    외부에서 파라미터를 받을 때는 @requestParam

    웹브라우저에서 localhost:8080/hello-mvc -> 내장 톰켓 서버를 거쳐서 스프링으로 넘어감. 스프링 컨테이너에서 helloController 에 매핑이 되있는지 찾고, 그 메서드를 호출해줌. return 값은 hello-template , model에는 (키값=name: 값=spring)

    viewResolver 가 templates/hello-template.html을 찾아서 thymeleaf 템플릿 엔진처리해달라고 넘기고, 템플릿엔진이 더링을 해서 변환한 html을 웹브라우저에 반환.

- API : 요즘에는 json 데이터포맷으로 클라이언트에게 데이터전달. 

  - API = 

    `@GetMapping("hello-string")`

    `@ResponseBody`

    `public String helloString(@RequestParam("name") String name){`

    `return "hello"+name;`

    `}`

    @ResponseBody 은 http body 부에 이 데이터를 직접 넣어 주겠다라는 의미. 템플릿엔진과의 차이는 view 가 없음.

    이 문자가 그대로 감. 문자를 넘기는 경우는 거의 없고, 데이터를 넘기는 방식 때문에 자주 쓰인다.

    `@GetMapping ("hello-api")`

    `@ResponseBody`

    `public Hello helloApi(*@RequestParam*("name")String name){`

    `Hello hello= new Hello();`

    `hello.setName(name);`

    `return hello;`

    `}`

    {"name":"spring"}

    이렇게 화면에 출력됨. json 방식으로 최근에는 거의 json으로 통일. spring에서도 기본이 json.

    웹브라우저에서 localhost:8080/hello-api -> 내장 톰캣 서버를 거쳐 스프링으로 넘어감. 스프링컨테이너에서 helloController가 매핑이 되있는지 찾음. @Responsebody 어노테이션이 붙어있으면 문자일 경우엔 HttpMessageConverter에서 StringConverter가 동작. 객체일 경우, JsonConverter가 동작함. 객체를 json 스타일로 바꿔서 웹브라우저로 응답함.
