
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

### 추가(변경)된 내용

* Docker 이미지 빌드 및 PUSH 처리를 [```io.fabric8/docker-maven-plugin```](http://dmp.fabric8.io/#introduction)로 변경하였다.
* 기존의 maven multi modules 설정에서 자식 모듈이 parent 태그를 통해서 지정하는 부모 프로젝트가 잘 못 되어있었기 때문에 그 부분도 수정을 했다.(변경 내역을 참조)
* io.fabric8/docker-maven-plugin 플러그인과 관련된 pom.xml 내용에는 주석을 추가했다.

---

### 이전 형태의 프로젝트 사용

다음과 같이 clone 받은 프로젝트 루트에서 checkout 커맨드로 변경하고 사용한다.

  ```shell
  $ git checkout tags/v0.1
  ```

---

### Maven settings.xml 설정

```io.fabric8/docker-maven-plugin``` 플러그인을 통해서 hub.docker.io 공개 도메인 저장소에 PUSH 처리가 되기 때문에 이 ```사이트를 접근 할 수 있는 인증 정보를 셋팅```해야한다. maven 설정에 인증 정보를 셋팅하는 방식이 여러 가지가 있는데 필자는 가장 간단한 설정으로 처리를 해놓았다.

* Maven wrapper version 확인

  프로젝트를 clone 받은 경로에서 maven wrapper 버전을 확인한다. wrapper가 설치된 경로에 위치한 설정 파일을 수정하여 인증을 할 것이다. 아래는 필자의 환경을 출력해본 예시이다.

  ```shell
  $ ./mvnw --version
  Apache Maven 3.6.1 (d66c9c0b3152b2e69ee9bac180bb8fcc8e6af555; 2019-04-05T04:00:29+09:00)
  Maven home: /Users/smartkuk/.m2/wrapper/dists/apache-maven-3.6.1-bin/38pn40mp89t5c94bjdbeod370m/apache-maven-3.6.1
  Java version: 1.8.0_191, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre
  Default locale: en_KR, platform encoding: UTF-8
  OS name: "mac os x", version: "10.14.5", arch: "x86_64", family: "mac"
  ```

* Change directory into configration directory of maven wrapper

  ```shell
  $ cd /Users/smartkuk/.m2/wrapper/dists/apache-maven-3.6.1-bin/38pn40mp89t5c94bjdbeod370m/apache-maven-3.6.1/conf
  $ vi ./settings.xml
  ```

* Add authentication into settings.xml

  vi 편집기로 내용을 열고 다음의 내용을 추가한다. 추가를 완료하면 docker push 처리까지 할 수 있는 상태가 된다.

  ```xml
  <servers>
    <server>
      <id>docker.io</id>
      <username>자신의 hub.docker.io 로그인 ID</username>
      <password>자신의 hub.docker.io 로그인 PASSWORD</password>
    </server>
  </servers>
  ```

---

### SAGA Pattern project start

위에서 Axon server를 실행한 뒤에 order-service, payment-service, shipping-service 스프링부트 프로젝트를 실행하면 Axon server 기반의 SAGA 패턴 구현 처리과정을 테스트 할 수 있다. 이클립스에 STS 플러그인을 사용할 것을 추천(편함)한다.

---

### Build project then docker build and push

커맨드를 한번 실행해서 스프링 애플리케이션의 빌드, docker build, docker push 처리를 모두 할 수 있다.

* ***```소스 빌드, 패키징, 도커 빌드 및 푸시```***

  clone 받은 프로젝트의 루트에서 실행하면 모두 빌드하고 도커 빌드 및 hub.docker.io push 까지 실행한다.

  ```shell
  $ ./mvnw clean package install docker:build docker:push
  ```

* 일반 spring 프로젝트 빌드

  단순히 빌드만 하는 행위는 위와 같이 실행

  ```shell
  $ ./mvnw clean package
  ```

* 도커 빌드 또는 푸시

  도커 빌드 혹은 푸시 처리는 각각 할수 있고 열거하면 같이 할 수 있다.

  ```shell
  # 같이 처리
  $ ./mvnw docker:build docker:push
  ```

  ```shell
  # 빌드만
  $ ./mvnw docker:build
  ```

  ```shell
  # 푸시만
  $ ./mvnw docker:push
  ```

* 특정 프로젝트만 선택하여 처리

  메이븐 커맨드를 통해서 특정 프로젝트에 대한 처리를 다음과 같이 할 수 있다.

  ```shell
  # order-service 프로젝트만 빌드, 패키징, 도커 빌드 및 푸시 처리
  $ ./mvnw -pl order-service clean package install docker:build docker:push
  ```

* Docker registry 변경 혹은 저장소 변경

  Docker 에서는 이미지를 저장하기 위해 registry 라는 것을 사용하게 되는데, 일반적으로 registry는 Docker 이미지를 Build 할때 사용하는 이름의 일부분이다. 첫번째 슬래쉬(```/```) 앞 부분에 dot(```.```) 또는 colon(```:```)이 포함되면 Docker는 이 부분을 자동으로 원격지 registry로 해석하여 처리를 한다. 루트 프로젝트 그리고 자식 프로젝트의 pom.xml 설정을 기준으로 Docker registry로 이미지가 등록(PUSH) 될때 다음 3개의 정보를 사용한다.

  * docker.image.prefix

    기본값은 smartkuk(필자의 hub.docker.io 사이트 ID이며 저장소 이름)이고, 이를 변경시 ```-Ddocker.image.prefix=my-repository``` 형태로 maven 커맨드에 포함시킨다.

    ```shell
    # docker.io 사이트에 saga 저장소로 이미지를 PUSH
    $ ./mvnw docker:build docker:push -Ddocker.image.prefix=saga
    ```

  * docker.registry

    기본값은 docker.io 되어있고, 다른 Docker registry를 사용하려면 ```-Ddocker.registry=my-docker-registry.io``` 형태로 maven 커맨드에 포함시킨다.

    ```shell
    # some-private-registry.io 사이트에 이미지를 PUSH(본 프로젝트를 기준으로 기본값으로 설정된 smartkuk 저장소로 처리)
    $ ./mvnw docker:build docker:push -Ddocker.registry=some-private-registry.io
    ```

  * settings.xml

    이 설정은 사용자가 직접 셋팅해야 하기 때문에 기본값은 없다. 만약 maven 커맨드를 실행하는 환경에 [Docker Engine](https://docs.docker.com/install/)이 설치가 되어있고, ```docker login``` 커맨드를 통해 로그인된 상태라면 settings.xml 파일에 인증 정보를 넣을 필요없이 이용가능(단, docker.registry 아큐먼트를 입력하지 않았을때)

---

### Minikube

쿠버네티스의 minikube 환경에서 테스트를 진행할 수 있도록 ```/minikube``` 경로에 yaml 파일들이 있다.

|파일명|설명|
|:---|:---|
|axonserver.yaml|axon server를 내려받으면 안에 포함된 파일, axon server를 쿠버네티스에서 기동할 수 있는 내용|
|*-service.yaml|각각의 애플리케이션을 Deployment 리소스를 사용해서 생성하며, DB 정보는 Secret을 통해서 읽고 그외에 정보는 ConfigMap 리소스를 통해서 읽는다.|
|*-repository.yaml|Postgresql을 구동하는 Deployment yaml 이다. 테스트 목적 혹은 시연을 목적으로 했기 때문에 StatefulSet으로 하지 않았다.|
|postgresql-pvc.yaml|PostgreSQL 구동시 PersistentVolumeClaim을 사용해서 리소스 할당|
|repository-configmap.yaml|PostgreSQL 공통 변수를 담을 ConfigMap 리소스|
|repository-secrets.yaml|PostgrelSQL 인증 관련 정보를 담을 Secret 리소스|

### operating

minikube 환경 기준으로 적용 순서는 다음과 같다.

```shell
$ kubectl apply -f ./axonserver.yaml
$ kubectl apply -f ./postgresql-pvc.yaml
$ kubectl apply -f ./order-repository.yaml
$ kubectl apply -f ./order-service-nodeport.yaml
$ kubectl apply -f ./payment-repository.yaml
$ kubectl apply -f ./payment-service-nodeport.yaml
$ kubectl apply -f ./repository-configmap.yaml
$ kubectl apply -f ./repository-secrets.yaml
$ kubectl apply -f ./shipping-repository.yaml
$ kubectl apply -f ./shipping-service.yaml
```

위와 같이 적용을 하면 웹 페이지로 확인이 가능하다. 아래와 같이 서비스 목록을 리스팅 하고 order-service 우측에 보이는 URL을 사용해서 브라우저로 접속한다. 위에 파일에 *-nodeport.yaml 로 서비스를 적용해야 로컬환경에서 기동한 브라우저로 연결되니 주의한다.

```shell
$ minikube service list
|-------------|---------------------|-----------------------------|
|  NAMESPACE  |        NAME         |             URL             |
|-------------|---------------------|-----------------------------|
| default     | axonserver          | No node port                |
| default     | axonserver-gui      | http://192.168.99.100:30350 |
| default     | kubernetes          | No node port                |
| default     | order-repository    | No node port                |
| default     | order-service       | http://192.168.99.100:30962 |
| default     | payment-repository  | No node port                |
| default     | payment-service     | http://192.168.99.100:30797 |
| default     | shipping-repository | No node port                |
| default     | shipping-service    | No node port                |
| kube-system | kube-dns            | No node port                |
| kube-system | metrics-server      | No node port                |
|-------------|---------------------|-----------------------------|
```

![노드 포트로 개방된 서버를 설정하는 화면 이미지](./minikube/images/endpoint-configuration.png "노드 포트로 개방된 서버를 설정하는 화면")

### index.html

테스트 또는 동작 확인을 위해 간단한 페이지를 만들었다. 상단 부분에서 각각 상품별 재고를 생성하며, 맨 밑에 부분에서 해당 상품을 구입하는 유스케이스로 보면 된다.

![테스트용 화면 이미지](./minikube/images/index.png "테스트용 화면")

### Flow

```
┌────────┐          ┌───────────────┐          ┌─────────────────┐          ┌──────────────────┐          ┌─────────────┐
│ Client │          │ Order-Service │          │ Payment-Service │          │ Shipping-Service │          │ Axon-Server │
└────────┘          └───────────────┘          └─────────────────┘          └──────────────────┘          └─────────────┘
    │                       │                           │                             │                          │
    │                       │                           │                             │                          │
    │   ┌─────────────┐     │   ┌────────────────────┐  │                             │                          │
    │───┤ HTTP / POST ├────▶│───┤ CreateOrderCommand ├──│─────────────────────────────│──────────────────────────│─┐
    │   │ /api/orders │     │   └────────────────────┘  │                             │                          │ │
    │   └─────────────┘     │◀──────────────────────────│─────────────────────────────│──────────────────────────│─┘
    │                       │                           │                             │                          │
    │                       │───────────────────────────│─────────────────────────────│──────────────────────────│─┐
    │                       │     OrderCreatedEvent     │                             │                          │ │
    │                       │                           │                             │                          │ │
    │                       │◀──────────────────────────│─────────────────────────────│──────────────────────────│─┘
    │                       │                           │                             │                          │
    │                       │   ┌────────────────────┐  │                             │                          │
    │                       │───┤CreateInvoiceCommand├──│─────────────────────────────│──────────────────────────│─┐
    │                       │   └────────────────────┘  │                             │                          │ │
    │                       │                           │◀────────────────────────────│──────────────────────────│─┘
    │                       │                           │                             │                          │
    │                       │                           │─────────────────────────────│──────────────────────────│─┐
    │                       │                           │   InvoiceToBeValidateEvent  │                          │ │
    │                       │                           │                             │                          │ │
    │                       │                           │◀────────────────────────────│──────────────────────────│─┘
    │                       │                           │                             │                          │
    │                       │                           │    ┌────────────────────┐   │                          │
    │                       │                           │────┤   DoOrderCommand   ├───│──────────────────────────│─┐
    │                       │                           │    └────────────────────┘   │                          │ │
    │                       │                           │                             │                          │ │
    │                       │                           │◀────────────────────────────│──────────────────────────│─┘
    │                       │                           │                             │                          │
    │                       │                           │─────────────────────────────│──────────────────────────│─┐
    │                       │                           │     InvoiceCreatedEvent     │                          │ │
    │                       │                           │                             │                          │ │
    │                       │◀──────────────────────────│─────────────────────────────│──────────────────────────│─┘
    │                       │                           │                             │                          │
    │                       │  ┌─────────────────────┐  │                             │                          │
    │                       │──┤CreateShippingCommand├──│─────────────────────────────│──────────────────────────│─┐
    │                       │  └─────────────────────┘  │                             │                          │ │
    │                       │                           │                             │◀─────────────────────────│─┘
    │                       │                           │                             │                          │
    │                       │                           │                             │──────────────────────────│─┐
    │                       │                           │                             │    OrderShippedEvent     │ │
    │                       │                           │                             │                          │ │
    │                       │◀──────────────────────────│─────────────────────────────│──────────────────────────│─┘
    │                       │                           │                             │                          │
    │                       │  ┌─────────────────────┐  │                             │                          │
    │                       │──┤  UpdateOrderStatus  ├──│─────────────────────────────│──────────────────────────│─┐
    │                       │  │       Command       │  │                             │                          │ │
    │                       │  └─────────────────────┘  │                             │                          │ │
    │                       │                           │                             │                          │ │
    │                       │◀──────────────────────────│─────────────────────────────│──────────────────────────│─┘
    │                       │                           │                             │                          │
    │                       │───────────────────────────│─────────────────────────────│──────────────────────────│─┐
    │                       │     OrderUpdatedEvent     │                             │                          │ │
    │                       │                           │                             │                          │ │
    │                       │◀──────────────────────────│─────────────────────────────│──────────────────────────│─┘
    │                       │                           │                             │                          │
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

