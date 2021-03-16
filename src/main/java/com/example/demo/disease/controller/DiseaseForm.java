package com.example.demo.disease.controller;

import com.example.demo.dog.domain.Dog;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.sql.Array;
import java.util.ArrayList;

@Getter
@Setter
public class DiseaseForm {

//    private String choice;
    private Long id;
    private ArrayList<String> symptom; // 증상을 입력받을 배열
}
