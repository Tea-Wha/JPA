package com.example.real_prj.service;

import com.example.real_prj.dto.MemberReqDto;
import com.example.real_prj.dto.MemberRestDto;
import com.example.real_prj.entity.Member;
import com.example.real_prj.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor // 생성자를 통한 의존성 주입을 받기 위해서 생성자를 롬복을 통해서 생성
public class ViewService {
    private final MemberRepository memberRepository;

    // 회원 전체 조회
    public List<MemberRestDto> getMemberList(){
        // DB로 부터 모든 회원 정보를 Member Entity 객체로 읽어옴
        List<Member> members = memberRepository.findAll();
        // 프론트엔드에 정보를 전달하기 위해 DTO List를 생성
       List<MemberRestDto> memberRestDtoList = new ArrayList<>();
       for (Member member : members){
           memberRestDtoList.add(convertEntityToDto(member));
       }
       return memberRestDtoList;
    }
    // 회원 상세 조회
    public MemberRestDto getMemberDetail(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("해당회원이 존재하지 않습니다."));
        return convertEntityToDto(member);
    }

    // 회원 정보 수정
    public boolean modifyMember(MemberReqDto memberReqDto){
        try{
            Member member = memberRepository.findByEmail(memberReqDto.getEmail())
                    .orElseThrow(()-> new RuntimeException("해당 회원이 존재하지 않습니다."));
            member.setName(memberReqDto.getName());
            member.setImgPath(memberReqDto.getImgPath());
            memberRepository.save(member);
            return true;
        }
        catch (Exception e){
            log.error("회원 정보 수정 : {}", e.getMessage());
            return false;
        }
    }

    // 회원 삭제
    public boolean deleteMember(String email) {
        try{
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(()->new RuntimeException("해당 회원이 존재하지 않습니다."));
            memberRepository.delete(member);
            return true;
        }
        catch (Exception e){
            log.error("회원 삭제에 실패 했습니다. : {}", e.getMessage());
            return false;
        }
    }


    // Member Entity => MemberRestDto
    private MemberRestDto convertEntityToDto(Member member){
        MemberRestDto memberRestDto = new MemberRestDto();
        memberRestDto.setEmail(member.getEmail());
        memberRestDto.setName(member.getName());
        memberRestDto.setRegDate(member.getRegDate());
        memberRestDto.setImagePath(member.getImgPath());
        return memberRestDto;
    }
}
