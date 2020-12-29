# 스프링부트, 웹 MVC, DB 접근기술에 대한 넓고 얕은 기본 공부. 

## Note

### start.spring.io

- Maven, Gradle 라이브러리를 가져오고, 관리해주는 툴. 요즘은 Gradle.

- spring boot version 은 정식릴리즈 중에서 2.3.x

- Group - 기업도메인명을 보통 씀.

- Artifact - 결과물

- Dependencies - (어떤 라이브러리를 가져다 쓸것인지) spring web, thymeleaf


**스프링 파일 내 프로젝트**

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
    
     

### 회원 관리 예제 - 백엔드 개발

- 비즈니스 요구사항 정리 - 가장 단순하게. 강의 목표가 단순한 예제를 가지고 어떻게 동작하는지 알아보기위함.

- 데이터 : 회원id, 이름

- 기능 : 회원 등록, 조회

- 아직 데이터 저장소가 선정되지 않음(가상의 시나리오)

 

**일반적인 웹 애플리케이션 계층 구조**

- 컨트롤러 : 웹 MVC의 컨트롤러 역할서비스 : 핵심 비즈니스 로직 구현

- 리포지토리 : 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리

- 도메인 : 비즈니스 도메인 객체. (회원, 주문, 쿠폰 등 주로 데이터베이스에 저장하고 관리됨)

- 아직 데이터 저장소가 선정되지 않았다는 가정이기에 우선 인터페이스로 구현 클래스를 변경할 수 있도록 설계.

- 데이터 저장소는 RDB, NoSQL 등등 다양한 저장소를 고민중인 상황으로 가정.

- 개발을 진행하기 위해서 초기 개발 단계에서는 구현체로 가벼운 메모리 기반의 데이터 저장소 사용.

 

### 회원 리포지토리 테스트 케이스 작성

- 개발한 기능을 실행해서 테스트 할때, 자바의 main 메서드를 통해서 실행하거나, 웹 애플리케이션의 컨트롤러를 통해서 해당기능을 실행한다.

  - 이방법은 오래걸리고, 반복 실행하기 어렵고, 여러 테스트를 한번에 실행하기 어렵다.
  - 자바는 JUnit이라는 프레임워크로 테스트를 실행해서 이러한 문제를 해결한다.

- 테스트케이스의 순서는 보장이 안됨. 순서와 상관이 없이 따로 동작하게 설계해야함. 

  순서에 의존되게 설계하면 절대안됨. 테스트가 끝나면 클리어되게.

 

### 회원 서비스 클래스 만들기

- 리포지토리와 도메인을 활용해서 실제 비지니스로직을 작성하는 것.

- 서비스를 만드려면 회원 리포지토리가 있어야함.

- private final MemberRepository memberRepository = new MemoryMemberRepository();

- 회원가입

  `public Long join(Member member){ //동명이인중복회원불가조건`

  `Optional<Member>result=memberRepository.findByName(member.getName());`

  `result.ifPresent(m->{`

  `throw new IllegalStateException("이미 존재하는 회원입니다.");`

  `//result에 null이 아니라 어떠한 값이 있으면 로직이 동작.`

  `Optional이라서 가능`

  `});`

  `memberRepository.save(member);`

  `return member.getId();`

  `}`

- 전체 회원 조회.

  `public List<Member> findMembers(){`

  ​    `return memberRepository.findAll();`

  `}`

 

*테스트 만들때 ctrl + shift + t (인텔리제이)*

<u>given, when, then = 뭔가가 주어졌을 때, 이것을 실행했을 때, 결과가 이것이 나와야한다.</u>

<u>테스트는 예외가 더 중요.</u>

`public void 중복_회원_예외(){`

`}`

`// try, catch 를 쓰기엔 애매함.` 

`assertThrows(IllegalStateException.class, () -> memberService.join(member2));`

`// exception 이 터지기를 기대함. memberService 로직을 태울 때.`

`// assertThrows 는 메시지 반환 가능.`

 

`MemoryMemberRepository memberRepository = new MemoryMemberRepository();`

`@AfterEach`

`public void afterEach(){`

`memberRepository.clearStore();`

`}`

 초기화를 위해 필요함. 메서드가 끝나고 어떠한 동작하는 콜백메서드.

 

 

### 스프링 빈과 의존관계

- 스프링은 @Controller 라는 어노테이션이 있으면 스프링이 객체생성해서 관리 함.

  (스프링컨테이너에서 스프링 빈이 관리된다.) 그래서 스프링과 관련된 기능들이 동작함. 

  private final MemberService memberService = new MemberService();

  -->> MemberService 를 가져다 써야해서 이렇게 쓸 수 있지만, 스프링이 관리를 하게 되면

  컨테이너에 등록하고 컨테이너에서 받아서 쓰도록 바꿔야함.

  왜냐하면, 여러 컨테이너들이 MemberService를 가져다 쓸 수 있음.

  근데 MemberService에는 특별한게 없음. 여러개를 생성할 필요없이 하나만 생성해서 공유하면 됨. 그래서 스프링 컨테이너에 등록하고 쓰면 됨. (스프링은 스프링컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다. ㅠ일하게 하나만 등록해서 공유. 따라서 같은 스프링 빈이면 모두 같은 인스턴스다. 설정으로 싱글톤이 아니게 설정할 수 있으나, 특별한 경우를 제외하면 대부분 싱글톤을 사용한다.)

  연결은 생성자로 연결하고, @Autowired 어노테이션.

  @Autowired 가 있으면 스프링이 스프링컨테이너에서 MemberService를 가져와서 연결 시켜줌.

  가지고 올 클래스에는 @Service 어노테이션을 달아줘야함. 이 어노테이션이 없으면 스프링이 구별을 못함. 그저 일반 클래스일뿐. @Repository 도 마찬가지.

- 컨트롤러를 통해서 외부요청을 받고, 서비스에서 로직을 만들고, 리포지토리에 데이터를 저장하고. 정형화된 패턴. 

  컨트롤러 ->(연결-Autowired) 서비스 ->(연결-Autowired) 리포지토리

- 컨트롤러가 생성될 때, 스프링빈에 등록되어있는 서비스객체를 가져와 넣어줌(DI - 의존성 주입)

  서비스를 생성할 때, 리포지토리를 가져와서 넣어줌(DI)

  -  **@Service, @Repository, @Autowired 어노테이션을 이용해서 컴포넌트 스캔방법을 이용해 스프링 빈 등록방법.**

 

### 자바코드로 직접 스프링 빈 등록하기

- 컴포넌트 스캔방법과 자바코드로 직접 스프링 빈 등록하는 것에는 장단점이 있다. 2가지 다 알아야함.

  과거에는 xml이라는 문서로 설정했으나, 현재는 잘 사용하지 않음. 

`@Configuration`

`public class SpringConfig {`

  `@Bean`

  `public MemberService memberService(){`

    `return new MemberService(memberRepository());`

  `}`

  `@Bean`

  `public MemberRepository memberRepository(){`

​    `return new MemoryMemberRepository();`

  `}`

`}`

 

- DI 에는 필드주입, setter 주입, 생성자 주입 이렇게 3가지 방법이 있다.

  지금까지 했던 것들이 생성자를 통해 들어오므로, 생성자 주입이다.

- 필드주입은 @Autowired private MemberService memberService; 

  처럼 필드에 바로 들어가는것. 필드주입은 따로 수정할 방법이 없음.  

- setter 주입은 생성자는 따로 생성하고 setter 메서드에 주입하는 것.

  누군가가 멤버컨트롤러를 호출했을떄 퍼블릭으로 열려있어야함.

- 실행중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입이 권장된다.

  실무에서는 정형화된 컨트롤러, 서비스, 리포지토리같은 코드는 컴포넌트 스캔을 사용한다.	

  정형화되지 않거나, 상황에 따라 구현클래스를 변경해야 하는 경우, 설정을 통해 스프링 빈으로 등록한다.

  

### 회원 관리 예제 - 웹 MVC 개발

1.웹 기능 홈 화면, 2.등록, 3.조회

1. 아무것도 없으면 welcome page라고 해서 index.html이 호출되야하지만

   localhost:8080 요청이 오면 맵핑된게 있는지 찾아봄.

   (정적컨텐츠 파트에서 설명했듯, 웹브라우저에서 호출하면 관련 컨트롤러를 찾고 없으면

   static파일을 찾음.)

   맵핑된게 있으면 바로 그 컨트롤러가 호출되고 끝남. 

   그래서 기존에 만들었던 index.html은 무시됨.

2. 회원가입을 들어가면 members/new로 들어감. (get)

   createForm() 이 맵핑됨.

   아무하는거 없이 return members/createMemberForm.

   template에서 찾음.

   찾은 html을 thymeleaf를통해 렌더링되고 뿌려짐.

   (<form> 에서 post방식으로 members/new로 넘어감.)

   members/new 로 주소는 같지만 get , post 에 따라 다르게 맵핑가능.

3. GetMapping("/members")

   회원조회할때 findMembers()로 members 를 리스트형태로 다 가져옴.

   model.addAttribute("members", members)로 members리스트 자체를 model에 넣어서 

   화면(view)에 넘김.

   메모리안에 있기 때문에 자바를 내려버리면 저장되있던 데이터가 다 날라감.

   

### H2 데이터베이스.

- 가볍고 심플. 

- JDBC URL : jdbc:h2:tcp://localhost/~/test ->> 이렇게 사용하는 이유는

  jdbc:h2~/test 이런식으로 파일로 접근 했을 경우, 동시에 접근이 안되고 충돌이 일어날수 있음.

  파일을 직접 접근하지 않고 소켓을 통해서 접근. 

  

### 순수 JDBC

- 스프링을 쓰는 이유는 객체지향적 설계를 좋다고 한다.

  인터페이스를 두고 구현체를 바꿔끼울 수 있는데(다형성 활용)

  스프링은 이것을 편리하게 하도록 스프링컨테이너가 지원해줌.

  스프링의 DI(Dependencies Injection)을 사용하면 기존 코드를 전혀 손대지 않고, 

  설정만으로 구현 클래스를 변경할 수 있다.

- 개방폐쇄원칙 : OCP, Open-Closed Principle

  확장에는 열려있고, 수정에는 닫혀있다.

  말이 좀 안 되는 것 같으나, 객체지향에서 말하는 다형성을 활용하면

  기능을 변경해도, 애플리케이션 동작에 필요한 코드들은 변경하지 않아도 됨.

 

### 스프링통합테스트

- 지금까지의 테스트는 순수자바코드만으로 테스트했으나(단위테스트), 

  데이터베이스 커넥션 정보를 스프링부트가 들고있기때문에 

  이제부터는 테스트를 스프링과 엮어서 하도록 함(통합테스트).

  

  **[순수한 자바코드만으로 테스트하는 단위테스트가 좋은 테스트일 확률이 높음.**

  **최소한의 단위마다 테스트를 할 수 있고, 스프링컨테이너 없이 테스트 할 수 있도록**

  **훈련하는 것이 좋다.]**

- 예전에는 복잡했으나, 지금은 @SpringBootTest 어노테이션을 붙여주면 됨.

  테스트케이스작성할 때는, 가장 편하게. 필드기반으로 Autowired.

- 테스트케이스에서는 반복할 수 있어야함. 

  데이터베이스는 기본적으로 트랜젝션이라는 개념이 있음.

  인설트쿼리를 한다음 커밋하기전까지는 디비에 반영이 안됨.

  테스트케이스에 @Transactional 어노테이션을 쓰면 테스트가 끝난 후 커밋하지 않고 롤백해줌.

  (DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.)

 

### 스프링 JDBCTemplate

- 스프링 JdbcTemplate, MyBatis 같은 라이브러리는 JDBC API에서 본 반복 코드를 대부분 제거해준다. 하지만 SQL은 직접 작성해야 한다.

  `private final JdbcTemplate jdbcTemplate; //injection 받지는 못함.`

    `@Autowired`

    `public JdbcTemplateMemberRepository(DataSource dataSource) {`

  ​    `jdbcTemplate = new JdbcTemplate(dataSource);`

    `}`

  -->> 권장 스타일.

  **[생성자가 하나뿐일 때는, 스프링 빈으로 등록되면 Autowired 생략 가능.]**

 

​		`public Member save(Member member) {`

​    	`	SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);`

​    	`	jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");`

​    	`	Map<String, Object> parameters = new HashMap<>();`

​    	`	parameters.put("name", member.getName());`

​    	`	Number key = jdbcInsert.executeAndReturnKey(new`

​    	    `MapSqlParameterSource(parameters));`

​    	`	member.setId(key.longValue());`

​    	`	return member;`

​		`}`

**SimpleJdbcInsert -->> 쿼리를 짤 필요 없이 insert문을 만들어줌.**

 

### JPA

- 기존의 반복코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.

- SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환을 할 수 있다.

- 개발 생산성을 크게 높일 수 있다. 

 

- jpa는 간략히 말하면 표준 인터페이스.

- 구현은 하이버네이트, 이클립스 등 여러 밴드가 있음.

 

- jpa는 orm(Object relational mapping)

- jpa를 사용하려면 entity를 맵핑해야함.

 

- jpa는 EntityManager 로 동작함.

  `private final EntityManager em;`

    `public JpaMemberRepository(EntityManager em) {`

  ​    `this.em = em;`

    `}`

- JPA 라이브러리를 받으면, 스프링부트가 자동으로 EntityManager를 만들어줌.

  injection 받으면 된다.

 

- JPA를 쓰려면 항상 트랜젝션이 있어야함.

  서비스 계층에 @Transactional .

 

### 스프링 데이터 JPA

- 스프링부트와 JPA만 사용해도 개발 생산성이 증가하고, 코드도 줄어든다.

- 여기에 스프링 데이터 JPA를 사용하면, 리포지토리에 구현 클래스 없이 인터페이스 만으로

  개발을 완료할 수 있다. CRUD 기능 또한 스프링 데이터 JPA가 모두 제공한다.

- 개발자는 핵심 비즈니스 로직을 개발하는데 집중할 수 있다.

- 스프링 데이터 JPA는 실무에서 관계형 데이터베이스를 사용한다면 필수다.

- **[ 스프링데이터 JPA는 JPA를 편리하게 사용하도록 도와주는 기술이므로, JPA를 먼저 학습한 후에 프링 데이터 JPA를 학습해야 한다]**

 

`public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {`

  `@Override`

  `Optional<hello.hellospring.domain.Member> findByName(String name);`

`}`

`@Configuration`

`public class SpringConfig {`

 `private final MemberRepository memberRepository;`

  `@Autowired`

  `public SpringConfig(MemberRepository memberRepository) {`

​    `this.memberRepository = memberRepository;`

  `}`

  `@Bean`

  `public MemberService memberService() {`

​    `return new MemberService(memberRepository);`

  `}`

`}`

-->> 스프링 데이터 JPA가 SpringDataJpaMemberRepository 를 스프링 빈으로 자동 등록해준다.

 

- 스프링 데이터 안에 기본 메서드들이 다 제공 됨.(findAll, findAllById 등)

  공통하는 게 아닌, 비즈니스가 다른 것들은 제공하지 못함.

`@Override`

  `Optional<hello.hellospring.domain.Member> findByName(String name);`

​	그래서 이런식으로 해줘야됨.

`(JPQL select m from Member m where m.name = ?)`

- findByName(), findByEmail() 처럼 메서드 이름만으로 조회 기능 제공.

- 페이징 기능 자동 제공.

 

**[실무에서는 JPA와 스프링데이터 JPA를 기본으로 사용하고, 복잡한 동적 쿼리는 Querydsl 이라는 라이브러리를 사용하면 된다. Querydsl 을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작할 수 있다. 이 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용하거나, 앞서 학습한 스프링 JdbcTemplate를 사용하면 된다.]**

 

### AOP가 필요한 상황

- 모든 메소드의 호출 시간을 측정하고 싶다면?

  공통관심사항 vs 핵심 관심 사항

  회원가입시간, 회원 조회 시간을 측정하고 싶다면?

 

- 문제 

  회원가입, 회원 조회에 시간을 측정하는 기능은 핵심 관심 사항이 아니다. 

  시간을 측정하는 로직은 공통 관심 사항이다. 

  시간을 측정하는 로직과 핵심 비즈니스의 로직이 섞여서 유지보수가 어렵다. 

  시간을 측정하는 로직을 별도의 공통 로직으로 만들기 매우 어렵다. 

  시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.

 

### AOP 적용

- AOP : Aspect Oriented Programming

- 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 분리 

  -->> 시간측정로직을 한곳에 모으고, 원하는 곳에 공통 관심 사항 적용 하는 것.

- AOP는 @Aspect 어노테이션을 적용시켜야 한다. 

  `@Aspect`

  `@Component`

  `public class TimeTraceAop {`

    `@Around("execution(* hello.hellospring..*(..))") // hello.hellospring 하위에 모두 적용시킴.`

    `public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{`

  ​    `long start = System.currentTimeMillis();`

  ​    `System.out.println("START : " + joinPoint.toShortString());`

  ​    `try{`

  ​      `return joinPoint.proceed();`

  ​    `}finally {`

  ​      `long finish = System.currentTimeMillis();`

  ​      `long timeMs = finish - start;`

  ​      `System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");`

  ​    `}`

    `}`

  `}`

  -->> @Around("execution(* hello.hellospring..*(..))") 를 통해서 원하는 적용대상을 선택할 수 있음.

 

- AOP에서 스프링은 적용 전에는 controller -> service 의존관계로 끝.

- AOP를 적용하면 프록시라고 하는 기술로 발생하는 가짜 서비스(가짜 스프링 빈)을 사이에 세워둠.

- 가짜 스프링빈이 끝나면, 실제 서비스를 호출함.

 
