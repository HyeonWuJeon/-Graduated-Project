package com.example.demo.symptom.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSymptom is a Querydsl query type for Symptom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSymptom extends EntityPathBase<Symptom> {

    private static final long serialVersionUID = 875769150L;

    public static final QSymptom symptom = new QSymptom("symptom");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QSymptom(String variable) {
        super(Symptom.class, forVariable(variable));
    }

    public QSymptom(Path<? extends Symptom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSymptom(PathMetadata metadata) {
        super(Symptom.class, metadata);
    }

}

