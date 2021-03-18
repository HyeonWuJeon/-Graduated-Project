## Guhae Dog (Graduation Project)
웹기반 AI 기술을 이용한 반려견 헬스케어 서비스

### Preview
![guhaedog_main](./img/guhaedog_main.PNG)

### How to Run
#### Web Server (Java based)
```
// Move to the directory where a .jar file exists
$ cd libs

// Run the .jar file
$ java -jar demo-0.0.1-SNAPSHOT.jar
```

#### AI Server (Python based)
```
# Move to the directory where a .py file exists
$ cd guhaedogML

# Run the .py file
$ python app.py
```

### Architecture
![guhaedog_architecture](img/guhaedog_architecture.png)

### Tech Stack
|Frontend   |Backend          |Database|AI            |CI/CD       |Cloud       |
|-----------|-----------------|--------|--------------|------------|------------|
|`Thymeleaf`|`Spring Boot`    |`MySQL` |`Flask`       |`Travis CI` |`Amazon EC2`|
|`Bootstrap`|`Spring Security`|        |`pandas`      |`Nginx`     |`Amazon RDS`|
|           |                 |        |`scikit-learn`|            |`Amazon S3` |
|           |                 |        |              |            |`Amazon Codedeploy`|

|API                         |ETC          |
|----------------------------|-------------|
|`Kakao Postcode Service API`|`Public Data`|
|`Kakao Maps API`            |             |
|`Google Charts API`         |             |

### Team
:dog: Guhaedog

### Developers
- https://github.com/da-nyee (Daeun Lee, 이다은)
- https://github.com/HyeonWuJeon (Hyeonwu Jeon, 전현우)
- https://github.com/workhardslave (Seunghwan Hwang, 황승환)
