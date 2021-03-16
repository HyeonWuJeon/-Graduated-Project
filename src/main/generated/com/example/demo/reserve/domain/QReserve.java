package com.example.demo.reserve.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReserve is a Querydsl query type for Reserve
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReserve extends EntityPathBase<Reserve> {

    private static final long serialVersionUID = -1855073058L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReserve reserve = new QReserve("reserve");

    public final StringPath address = createString("address");

    public final StringPath date = createString("date");

    public final StringPath description = createString("description");

    public final com.example.demo.hospital.domain.QHospital hospital;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.demo.member.domain.QMember member;

    public final StringPath name = createString("name");

    public final StringPath tel = createString("tel");

    public QReserve(String variable) {
        this(Reserve.class, forVariable(variable), INITS);
    }

    public QReserve(Path<? extends Reserve> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReserve(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReserve(PathMetadata metadata, PathInits inits) {
        this(Reserve.class, metadata, inits);
    }

    public QReserve(Class<? extends Reserve> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hospital = inits.isInitialized("hospital") ? new com.example.demo.hospital.domain.QHospital(forProperty("hospital"), inits.get("hospital")) : null;
        this.member = inits.isInitialized("member") ? new com.example.demo.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

