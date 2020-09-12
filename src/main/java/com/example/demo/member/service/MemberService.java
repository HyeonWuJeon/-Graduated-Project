package com.example.demo.member.service;

import com.example.demo.config.service.ApplicationService;
import com.example.demo.config.security.Role;
import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.diagnosis.repository.DiagnosisRepository;
import com.example.demo.diagnosis.service.DiagnosisService;
import com.example.demo.hospital.repository.HospitalRepository;
import com.example.demo.hospital.service.HospitalService;
import com.example.demo.member.domain.Address;
import com.example.demo.member.domain.Member;
import com.example.demo.member.dto.MemberResponseDto;
import com.example.demo.member.dto.MemberSaveRequestDto;
import com.example.demo.member.dto.MemberUpdatePwd;
import com.example.demo.member.dto.MemberUpdateRequestDto;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.reserve.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService extends ApplicationService implements UserDetailsService  {

    private final DiagnosisRepository diagnosisRepository;
    private final MemberRepository memberRepository;
    private final DiagnosisService diagnosisService;
    private final ReserveService reserveService;
    private final HospitalService hospitalService;
    private final HospitalRepository hospitalRepository;



    // 회원가입 아이디 중복체크
    @Transactional
    public int validateDuplicateMember(String user_email) {
        String value = user_email;
        value = value.substring(1, value.length() - 1);
        HashMap<String, String> hashMap = new HashMap<>();

        String[] entry = value.split(":");

        hashMap.put(entry[0].trim(), entry[1].trim());

        String value2 = hashMap.values().toString().substring(2, hashMap.values().toString().length() - 2);


        Member findMember = memberRepository.findEmailCheck(value2);
        System.out.println("findMember확인 = " + findMember);
        if (findMember != null) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * FUNCTION : 회원 가입
     * 하아... 런타임익셉션떠도 롤백이안되서 자동코밋되므로 오류ㅜ가뜬다...
     * @param form
     * @return
     */
    public String SignUp(MemberSaveRequestDto form)  {
        HashMap<String, Object> rtnMap = returnMap();
//        try {
            MemberSaveRequestDto member = new MemberSaveRequestDto();
        //주소 지정
        Address address2 = member.setAddress(form.getCity(), form.getZipcode(), form.getStreet());

        //패스워드 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        form.SHA256_PassWord(passwordEncoder.encode(form.getPassword()));

        //권한 부여
        if (form.getRole() == Role.GUEST) {
            form.GIVE_Role(Role.GUEST);
        } else if (form.getRole() == Role.VET) {
            form.GIVE_Role(Role.VET);
        }

        //저장 + 유효성 검사
        memberRepository.save(member.builder()
                .name(form.getName())
                .birth(form.getBirth())
                .email(form.getEmail())
                .password(form.getPassword())
                .phone(form.getPhone())
                .role(form.getRole())
                .address(address2)
                .build().toEntity()); // 터지는데

        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_SUCCESS); //성공
//        }
//        catch(ConstraintViolationException e){
//            defaultExceptionHandling(rtnMap, AJAX_RESULT_ILLEGAL_STATE);
//        }
        return jsonFormatTransfer(rtnMap);
    }

    // 회원 조회
    @Transactional
    public Member findMember(Object id) {
        if (id instanceof Long) {
            return memberRepository.findById((Long) id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));
        } else {
            Member member = memberRepository.findEmailCheck((String) id);
            return member;
        }

    }
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Member userEntityWrapper = memberRepository.findEmailCheck(userEmail);

        if (userEntityWrapper == null) {
            throw new UsernameNotFoundException("User not authorized.");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(userEntityWrapper.getRole().getValue());
        UserDetails userDetails = (UserDetails) new User(userEntityWrapper.getEmail(),
                userEntityWrapper.getPassword(), Arrays.asList(authority));

        return userDetails;
    }

    // 회원 정보수정
    @Transactional
    public Long update(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));
        member.update(requestDto.getCity(), requestDto.getStreet(), requestDto.getZipcode(), requestDto.getPhone());

        return id;
    }

    // 회원 패스워드 수정
    public Long updatePwd(Long id, MemberUpdatePwd requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encodePwd = passwordEncoder.encode(requestDto.getPassword());
        member.updatePwd(encodePwd);

        return id;
    }

    // 관리자, 회원 정보수정
    public Long updateMember(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));

        member.updateMember(requestDto.getName(), requestDto.getCity(), requestDto.getStreet(), requestDto.getZipcode(), requestDto.getPhone());

        return id;
    }


    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원/수의사/관리자가 없습니다. id=" + id));

        return new MemberResponseDto(entity);
    }

    // 삭제 API
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원/수의사/관리자가 없습니다. id=" + id));
        if (member.getHospital() != null) { //수의사인데 병원을 가지고있는경우
            hospitalService.deleteHospital(member.getHospital().getId()); //예약정보 전부삭제
            memberRepository.delete(member);
        } else if (member.getHospital() == null) {                             // 수의사인데 병원이 없거나, 일반 사용자일경우
            reserveService.delete_member(member);
            List<Diagnosis> diagnosis = diagnosisRepository.findAllDesc(member);
            diagnosisService.delete(diagnosis);
            memberRepository.delete(member);
        }

    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAllDesc() {
        return memberRepository.findAllDesc().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }


    public String mockTest() {
        return "whteship";
    }

}

