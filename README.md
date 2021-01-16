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
|Frontend   |Backend          |Database|AI            |Cloud       |
|-----------|-----------------|--------|--------------|------------|
|`Thymeleaf`|`Spring Boot`    |`MySQL` |`Flask`       |`Amazon EC2`|
|`Bootstrap`|`Spring Security`|        |`pandas`      |`Amazon RDS`|
|           |                 |        |`scikit-learn`|            |

|API                         |ETC          |
|----------------------------|-------------|
|`Kakao Postcode Service API`|`Public Data`|
|`Kakao Maps API`            |             |
|`Google Charts API`         |             |

### Team
:dog: Guhaedog

### Developers
|이다은    |전현우    |황승환    |
|:-------:|:-------:|:-------:|
|<img src="https://avatars0.githubusercontent.com/u/50176238?s=400&u=212ca9ffd06b88465746a94eaa6f88b10485497d&v=4" alt="daeun" width="100" height="100">|<img src="https://avatars1.githubusercontent.com/u/45196240?s=400&u=7715bd7226b30f902a7099113f53c0ad5e963aaf&v=4" alt="hyeonwu" width="100" height="100">|<img src="https://avatars2.githubusercontent.com/u/55372264?s=400&u=4439594da28f640261d48200802f2d6b9ac3f436&v=4" alt="seunghwan" width="100" height="100">|
|[da-nyee](https://github.com/da-nyee)|[HyeonWuJeon](https://github.com/HyeonWuJeon)|[workhardslave](https://github.com/workhardslave)|

### License
This work is published under [Apache-2.0](https://github.com/da-nyee/guhae-dog/blob/master/LICENSE) License.