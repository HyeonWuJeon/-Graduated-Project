package com.example.demo.diagnosis.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMacak is a Querydsl query type for Macak
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMacak extends EntityPathBase<Macak> {

    private static final long serialVersionUID = -1361237050L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMacak macak = new QMacak("macak");

    public final QDiagnosis _super;

    // inherited
    public final com.example.demo.dog.domain.QDog dog;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final com.example.demo.member.domain.QMember member;

    public final StringPath percent = createString("percent");

    public QMacak(String variable) {
        this(Macak.class, forVariable(variable), INITS);
    }

    public QMacak(Path<? extends Macak> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMacak(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMacak(PathMetadata metadata, PathInits inits) {
        this(Macak.class, metadata, inits);
    }

    public QMacak(Class<? extends Macak> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiagnosis(type, metadata, inits);
        this.dog = _super.dog;
        this.id = _super.id;
        this.member = _super.member;
    }

}

