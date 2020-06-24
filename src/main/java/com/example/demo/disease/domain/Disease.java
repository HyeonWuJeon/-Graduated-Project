package com.example.demo.disease.domain;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
@Entity
//public class Disease extends BaseTimeEntity {                 // 생성일자, 수정일자 BaseTimeEntity 클래스에서 상속받음
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;            // 질병명
    private String description;     // 질병설명

    @Builder
    public Disease(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}