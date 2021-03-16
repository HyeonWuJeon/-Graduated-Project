package com.example.demo.diagnosis.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiagnosis is a Querydsl query type for Diagnosis
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDiagnosis extends EntityPathBase<Diagnosis> {

    private static final long serialVersionUID = 178679422L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiagnosis diagnosis = new QDiagnosis("diagnosis");

    public final com.example.demo.dog.domain.QDog dog;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.demo.member.domain.QMember member;

    public QDiagnosis(String variable) {
        this(Diagnosis.class, forVariable(variable), INITS);
    }

    public QDiagnosis(Path<? extends Diagnosis> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiagnosis(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiagnosis(PathMetadata metadata, PathInits inits) {
        this(Diagnosis.class, metadata, inits);
    }

    public QDiagnosis(Class<? extends Diagnosis> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dog = inits.isInitialized("dog") ? new com.example.demo.dog.domain.QDog(forProperty("dog"), inits.get("dog")) : null;
        this.member = inits.isInitialized("member") ? new com.example.demo.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

