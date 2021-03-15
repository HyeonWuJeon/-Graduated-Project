package com.example.demo.member.dto;

import com.example.demo.config.BaseTimeEntity;
import com.example.demo.config.security.Role;
import com.example.demo.member.domain.Address;
import com.example.demo.member.domain.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;


@Getter
public class MemberDto extends BaseTimeEntity {

    @Data
    //UNIT TEST 어노테이션
    @NoArgsConstructor
    public static class Request{

        private Long id;

        @NotBlank
        private String name;

        @Length(max = 200) @NotBlank(message = "이메일을 확인해 주세요.:)")
        private String email; // LINE :: 이메일

        @Length(max = 200) @NotBlank(message = "패스워드를 확인해 주세요.:)") @Pattern(regexp = "[0-9]{5,10}", message = "5~10자리의 숫자만 입력가능합니다")
        private String password; // LINE :: 패스워드

        private String low_pwd; // LINE :: 패스워드

        @Length(max = 200)
        private String pwdChk; // LINE :: 패스워드

        private String birth;

        private Role role;

        private Address address;

        @Length(max = 11)
        private String phone;

        // LINE :: 주소
        @NotBlank(message = "주소를 확인해 주세요") private String city;
        @NotBlank(message = "주소를 확인해 주세요") private String zipcode;
        @NotBlank(message = "주소를 확인해 주세요") private String street;

        /**
         * FUNCTION : 패스워드 암호화
         * @param password
         */
        public void SHA256_PassWord(String password) {
            this.password = password;
        }

        /**
         * FUNCTION : 권한 확인 & 변경
         * @param role
         */
        public void GIVE_Role(Role role) {
            this.role = role;
        }

        /**
         * FUNCTION : 주소정보 저장
         * @param zipcode
         * @param city
         * @param street
         */
        public void setAddress(String city, String zipcode, String street) {
            address = new Address(city, zipcode, street);
        }


        @Builder
        public Request(String birth, String phone, String name, Role role, @Length(max = 200) @NotBlank(message = "패스워드를 확인해 주세요.:)") @Pattern(regexp = "[0-9]{5,10}", message = "5~10자리의 숫자만 입력가능합니다") String password, String low_pwd, @Length(max = 200) String pwdChk, @Length(max = 200) @NotBlank(message = "이메일을 확인해 주세요.:)") String email,  @NotBlank(message = "주소를 확인해 주세요") String city, @NotBlank(message = "주소를 확인해 주세요") String zipcode, @NotBlank(message = "주소를 확인해 주세요") String street, Address address) {
            this.name = name;
            this.password = password;
            this.birth = birth;
            this.phone = phone;
            this.email = email;
            this.city = city;
            this.zipcode = zipcode;
            this.street = street;
            this.address =address;
            this.role =role;
        }

        public Member toEntity() {
            return Member.builder()
                    .email(email)
                    .password(password)
                    .address(address)
                    .role(role)
                    .enabled(true)
                    .build();
        }
    }

    @Getter
    public static class updatePwd {

        private String password;
        private String password2;

        @Builder
        public updatePwd(String password, String password2) {
            this.password=password;
            this.password2=password2;
        }
    }

    @Getter
    public static class updateInfo {

        private String name;
        private String city;
        private String zipcode;
        private String street;
        private String phone;

        @Builder
        public updateInfo(String name,  String street, String zipcode, String city, String phone) {
            this.name = name;
            this.city = city;
            this.zipcode = zipcode;
            this.street = street;
            this.phone = phone;
        }
    }

    @Getter
    public static class Response implements UserDetails,  GrantedAuthority {
        private Long id;
        private String name;
        private String password;
        private String phone;
        private String birth;
        private String pwd; // LINE :: 패스워드
        private String email; // LINE :: 이메일
        private Address address; // LINE :: 주소
        private Role role; // LINE :: 권한
        private String authority; // LINE :: 인증
        private boolean enabled; // LINE :: USER 상태확인
        /**
         * CONSTRUCTOR :: 사용자 정보 조회
         * @param entity
         */
        public Response(Member entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.email = entity.getEmail();
            this.password = entity.getPassword();
            this.address = entity.getAddress();
            this.phone = entity.getPhone();
            this.birth = entity.getBirth();
            this.role = entity.getRole();
            this.enabled = entity.isEnabled();
        }

        private Collection<? extends GrantedAuthority> authorities; // user 권한

        /**
         * CONSTRUCTOR :: 사용자 정보 인증
         * @param u
         * @param singleton
         * @param <T>
         */
        public <T> Response(Member u, Set<T> singleton) {
            this.email = u.getEmail();
            this.pwd = u.getPassword();
            this.authorities = (Collection<? extends GrantedAuthority>) singleton;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return this.getPwd();
        }

        @Override
        public String getUsername() {
            return email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return enabled;
        } // 계정이 만기되었는지 확인

        @Override
        public boolean isAccountNonLocked() {
            return enabled;
        } // 계정이 잠겨있는지 확인

        @Override
        public boolean isCredentialsNonExpired() {
            return enabled;
        } // 자격이 만기되었는지 확인

//        @Override
//        public boolean isEnabled() {
//            return true;
//        }



    }
}

