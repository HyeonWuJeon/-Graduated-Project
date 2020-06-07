package com.example.demo.dog.vo;


import com.example.demo.disease.dto.Disease;
import com.example.demo.member.vo.Member;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Dog {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String name;
    private String age;
    private String gender;
    private String birth;
    private String type;

//    @OneToMany(mappedBy = "dog", orphanRemoval = true)
//    List<Disease> diseaseList = new ArrayList<>();


    @Builder
    public Dog(Long id, Member member, String name, String age, String gender, String birth, String type) {
        this.id = id;
        this.member=member;
        this.name = name;
        this.age = age;
        this.gender=gender;
        this.birth=birth;
        this.type=type;
    }
    public Dog update(String age, String gender, String name) {
        this.age = age;
        this.gender = gender;
        this.name = name;
        return this;
    }

    public Dog update_admin(String age, String name, String gender, String birth) {
        this.age = age;
        this.gender = gender;
        this.name = name;
        this.birth = birth;
        return this;
    }

}
