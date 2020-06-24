package com.example.demo.member.service;

import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.diagnosis.repository.DiagnosisRepository;
import com.example.demo.diagnosis.service.DiagnosisService;
import com.example.demo.hospital.service.HospitalService;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.dto.*;

import com.example.demo.reserve.service.ReserveService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final DiagnosisRepository diagnosisRepository;
    private final MemberRepository memberRepository;
    private final DiagnosisService diagnosisService;
    private final ReserveService reserveService;
    private final HospitalService hospitalService;

    // 회원가입 아이디 중복체크
    @Transactional
    public int validateDuplicateMember(String user_email) {

        String value = user_email;
        value = value.substring(1,value.length()-1);
        HashMap<String, String> hashMap = new HashMap<>();

        String[] entry = value.split(":");

        hashMap.put(entry[0].trim(), entry[1].trim());

        String value2 = hashMap.values().toString().substring(2, hashMap.values().toString().length()-2);

        Member findMember = memberRepository.findEmailCheck(value2);

        if (findMember!=null) {
            return 1;
        }else{
            return 0;
        }
    }

    // 회원가입
    @Transactional
    public Long SignUp(MemberSaveRequestDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.SHA256_PassWord(passwordEncoder.encode(memberDto.getPassword()));

//        memberDto.GIVE_Role(Role.ADMIN);

        if(memberDto.getRole() == Role.GUEST) {
            memberDto.GIVE_Role(Role.GUEST);
        } else if(memberDto.getRole() == Role.VET) {
            memberDto.GIVE_Role(Role.VET);
        }

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Member userEntityWrapper = memberRepository.findEmailCheck(userEmail);
        if(userEntityWrapper == null ){
            throw new UsernameNotFoundException("User not authorized.");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(userEntityWrapper.getRole().getValue());
        UserDetails userDetails = (UserDetails)new User(userEntityWrapper.getEmail(),
                userEntityWrapper.getPassword(), Arrays.asList(authority));
        log.info(userDetails.getPassword());

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
    @Transactional
    public Long updatePwd(Long id, MemberUpdatePwd requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.info("입력한 : " + requestDto.getPassword());
        log.info("본래 : " +member.getPassword());
        log.info("password : " + member.getPassword().getClass());
        log.info("dto pwd class : " + requestDto.getPassword().getClass());

        String encodePwd = passwordEncoder.encode(requestDto.getPassword());
        member.updatePwd(encodePwd);

        return id;
    }

    // 관리자, 회원 정보수정
    @Transactional
    public Long updateMember(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));

        member.updateMember(requestDto.getName(), requestDto.getCity(), requestDto.getStreet(), requestDto.getZipcode(), requestDto.getPhone());

        return id;
    }


    // 정보수정
    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원/수의사/관리자가 없습니다. id=" + id));

        return new MemberResponseDto(entity);
    }

    // 삭제 API
    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원/수의사/관리자가 없습니다. id=" + id));
        if(member.getHospital() != null){ //수의사인데 병원을 가지고있는경우
            hospitalService.deleteHospital(member.getHospital().getId()); //예약정보 전부삭제
        }

        else if(member.getHospital() == null) { //수의사인데 병원이 없거나, 일반 사용자일경우
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

    @Transactional(readOnly = true)
    public Member regSession(String name){
        return memberRepository.findEmailCheck(name);
    }

}