package com.example.demo.diagnosis.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAir is a Querydsl query type for Air
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAir extends EntityPathBase<Air> {

    private static final long serialVersionUID = 865610391L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAir air = new QAir("air");

    public final QDiagnosis _super;

    // inherited
    public final com.example.demo.dog.domain.QDog dog;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final com.example.demo.member.domain.QMember member;

    public final StringPath percent = createString("percent");

    public QAir(String variable) {
        this(Air.class, forVariable(variable), INITS);
    }

    public QAir(Path<? extends Air> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAir(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAir(PathMetadata metadata, PathInits inits) {
        this(Air.class, metadata, inits);
    }

    public QAir(Class<? extends Air> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiagnosis(type, metadata, inits);
        this.dog = _super.dog;
        this.id = _super.id;
        this.member = _super.member;
    }

}

