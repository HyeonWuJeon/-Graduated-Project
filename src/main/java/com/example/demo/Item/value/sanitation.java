package com.example.demo.item.value;


import com.example.demo.item.domain.Item;
import org.springframework.context.annotation.Description;

import javax.persistence.Entity;

@Entity
@Description("S")
public class sanitation extends Item {

    private String type;        // 종류 (ex. 밴드, 알약, 연고..)
    private String bestBefore;  // 유통기한
}
