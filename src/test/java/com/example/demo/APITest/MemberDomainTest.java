package com.example.demo.APITest;


import com.example.demo.hospital.domain.Hospital;
import com.example.demo.hospital.dto.HospitalSaveRequestDto;
import com.example.demo.member.domain.Member;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SpringBootTest
public class MemberDomainTest {

    static HospitalSaveRequestDto hospital = new HospitalSaveRequestDto();
    @PersistenceContext
    EntityManager em;

    @Test
    public void HospitalSetting(){
        hospital.setName("병원");
        hospital.setAddress("강남");
        hospital.setTel("010");
        em.persist(hospital);
        System.out.println(hospital.getName());
        Member member = new Member();
        member.regHospital(hospital.toEntity());

       assertThat(member.getHospital().getName()).isEqualTo(hospital.getName()); //Hamcrest

    }
}
