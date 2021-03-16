package com.example.demo.disease.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDisease is a Querydsl query type for Disease
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDisease extends EntityPathBase<Disease> {

    private static final long serialVersionUID = -1961902882L;

    public static final QDisease disease = new QDisease("disease");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QDisease(String variable) {
        super(Disease.class, forVariable(variable));
    }

    public QDisease(Path<? extends Disease> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDisease(PathMetadata metadata) {
        super(Disease.class, metadata);
    }

}

