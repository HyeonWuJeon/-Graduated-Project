package com.example.demo.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 941874506L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.example.demo.config.QBaseTimeEntity _super = new com.example.demo.config.QBaseTimeEntity(this);

    public final QAddress address;

    public final StringPath birth = createString("birth");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final ListPath<com.example.demo.diagnosis.domain.Diagnosis, com.example.demo.diagnosis.domain.QDiagnosis> diList = this.<com.example.demo.diagnosis.domain.Diagnosis, com.example.demo.diagnosis.domain.QDiagnosis>createList("diList", com.example.demo.diagnosis.domain.Diagnosis.class, com.example.demo.diagnosis.domain.QDiagnosis.class, PathInits.DIRECT2);

    public final ListPath<com.example.demo.dog.domain.Dog, com.example.demo.dog.domain.QDog> dogList = this.<com.example.demo.dog.domain.Dog, com.example.demo.dog.domain.QDog>createList("dogList", com.example.demo.dog.domain.Dog.class, com.example.demo.dog.domain.QDog.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final com.example.demo.hospital.domain.QHospital hospital;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final EnumPath<com.example.demo.config.security.Role> role = createEnum("role", com.example.demo.config.security.Role.class);

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.hospital = inits.isInitialized("hospital") ? new com.example.demo.hospital.domain.QHospital(forProperty("hospital"), inits.get("hospital")) : null;
    }

}

