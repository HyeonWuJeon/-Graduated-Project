package com.example.demo.config;


import lombok.Getter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass   // JPA 엔티티 클래스들이 해당 클래스를 상속할경우 해당 클래스의 필드들도 엔티티로 인식하게 한다.
@EntityListeners(AuditingEntityListener.class)  // BaseTimeEntity 클래스에 Auditing 기능을 포함시킨다.
public class BaseTimeEntity {

    @CreatedDate    // 엔티티가 생성되어 저장될때 자동으로 시간을 부여함
    private LocalDateTime createDate;

    @LastModifiedDate   // 조회한 엔티티의 값이 변경할때 자동으로 시간을 부여함
    private LocalDateTime modifiedDate;
}
