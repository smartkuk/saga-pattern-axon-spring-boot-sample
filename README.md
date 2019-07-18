
## SAGA Pattern with axon

본 프로젝트는 [dashsaurabh/saga-pattern-axon-spring-boot-sample] fork 하여 추가적으로 필요한 내용을 적용한 프로젝트이다.

### Axon server dependency

프로젝트를 로컬 환경에서 실행하여 테스트를 하려면 로컬 환경에 [Axon server]를 기동해야 한다. 다운로드를 받아서 사용할 것을 권함

* Axon 관련 zip 파일을 다운로드 받아 압축을 해제하고, axonserver-4.1.2.jar 파일을 실행하면 아래와 같이 기동하는 것을 확인 할 수 있다.

  ```shellscript
  $ AxonServer ./axonserver-4.1.2.jar
  12:13:13.749 [main] INFO org.springframework.core.KotlinDetector - Kotlin reflection implementation not found at runtime, related features won't be available.
      _                     ____
      / \   __  _____  _ __ / ___|  ___ _ ____   _____ _ __
    / _ \  \ \/ / _ \| '_ \\___ \ / _ \ '__\ \ / / _ \ '__|
    / ___ \  >  < (_) | | | |___) |  __/ |   \ V /  __/ |
  /_/   \_\/_/\_\___/|_| |_|____/ \___|_|    \_/ \___|_|
  Standard Edition                        Powered by AxonIQ

  version: 4.1.2
  2019-07-03 12:13:14.490  INFO 18411 --- [           main] io.axoniq.axonserver.AxonServer          : Starting AxonServer on KimJeongkukui-MacBook-Pro.local with PID 18411 (/Users/smartkuk/Documents/no_git/Axon/AxonQuickStart/AxonServer/axonserver-4.1.2.jar started by smartkuk in /Users/smartkuk/Documents/no_git/Axon/AxonQuickStart/AxonServer)
  2019-07-03 12:13:14.493  INFO 18411 --- [           main] io.axoniq.axonserver.AxonServer          : No active profile set, falling back to default profiles: default
  2019-07-03 12:13:14.893  WARN 18411 --- [kground-preinit] org.springframework.web.HttpLogging      : For Jackson Kotlin classes support please add "com.fasterxml.jackson.module:jackson-module-kotlin" to the classpath
  2019-07-03 12:13:18.608  INFO 18411 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8024 (http)
  ```

---

### SAGA Pattern project start

위에서 Axon server를 실행한 뒤에 order-service, payment-service, shipping-service 스프링부트 프로젝트를 실행하면 Axon server 기반의 SAGA 패턴 구현 처리과정을 테스트 할 수 있다.

---

### Build

* 프로젝트 ROOT 경로에서 다음의 커맨드를 사용하면 프로젝트를 빌드하고 내부 저장소에 저장한다.

  ```shell
  $ ./mvnw clean package install;
  ```

* 각각의 프로젝트로 이동하여 다음의 커맨드를 사용해서 로컬환경에 도커 이미지를 만들고 올린다.

  ```shell
  $ docker-build.sh
  ```

위에 커맨드는 각각의 프로젝트(order-service, payment-service, shipping-service)로 이동해서 로커 빌드를 실행하도록 만들어놓은 쉘스크립트이다.

로컬환경에서 만들어 놓은 이미지를 docker hub로 업로드 하기 전에 프로젝트별로 maven pom.xml 내부에 ```<docker.image.prefix>본인의dockerhub이름</docker.image.prefix>``` 부분을 변경해야 하고 docker login 된 상태이어야 동작하니 주의한다.

* 위에 과정을 마쳤다면 다음과 같이 docker hub에 이미지를 올린다.

  ```shell
  $ docker push smartkuk/payment-service:latest;
  $ docker push smartkuk/order-service:latest;
  $ docker push smartkuk/shipping-service:latest;
  ```

---

### TroubleShooting

* 만약 Axon server 기동을 하지 않은 상태로 order-service 프로젝트를 기동하면 아래와 같은 오류 로그가 친절하게 나오니 axon server를 실행하면 해결된다.

```shellscript
# ... 중략 ...
2019-07-03 12:17:18.783  WARN 18706 --- [           main] o.a.a.c.AxonServerConnectionManager      : Connecting to AxonServer node localhost:8124 failed: UNAVAILABLE: io exception
**********************************************
*                                            *
*  !!! UNABLE TO CONNECT TO AXON SERVER !!!  *
*                                            *
* Are you sure it's running?                 *
* If you haven't got Axon Server yet, visit  *
*       https://axoniq.io/download           *
*                                            *
**********************************************

To suppress this message, you can
 - explicitly configure an AxonServer location,
 - start with -Daxon.axonserver.suppressDownloadMessage=true
# ... 중략 ...
```

[Axon server]: https://axoniq.io/product-overview/axon-server
[dashsaurabh/saga-pattern-axon-spring-boot-sample]: https://github.com/dashsaurabh/saga-pattern-axon-spring-boot-sample

