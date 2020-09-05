package com.example.demo.member.domain;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable //H2 디비
@Getter
@RequiredArgsConstructor
public class Address {

    @Column(name = "city")
    String city;
    @Column(name = "zipcode")
    String zipcode;
    @Column(name = "street")
    String street;

    @Builder
    public Address(String city, String zipcode, String street) {
        this.city = city;
        this.zipcode = zipcode;
        this.street = street;
    }

    /**
     * ??
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) &&
                Objects.equals(getZipcode(), address.getZipcode()) &&
                Objects.equals(getStreet(), address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getZipcode(), getStreet());
    }
}
