package com.example.real_prj.service;

import com.example.real_prj.dto.BoardResDto;
import com.example.real_prj.dto.CommentResDto;
import com.example.real_prj.dto.MemberReqDto;
import com.example.real_prj.dto.MemberResDto;
import com.example.real_prj.entity.Board;
import com.example.real_prj.entity.Comment;
import com.example.real_prj.entity.Member;
import com.example.real_prj.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor // 생성자를 통한 의존성 주입을 받기 위해서 생성자를 롬복을 통해서 생성
public class ViewService {
    private final MemberRepository memberRepository;

    // 회원 전체 조회
    public List<MemberResDto> getMemberList(){
        // DB로 부터 모든 회원 정보를 Member Entity 객체로 읽어옴
        List<Member> members = memberRepository.findAll();
        // 프론트엔드에 정보를 전달하기 위해 DTO List를 생성
       List<MemberResDto> memberResDtoList = new ArrayList<>();
       for (Member member : members){
           memberResDtoList.add(convertEntityToDto(member));
       }
       return memberResDtoList;
    }
    // 회원 상세 조회
    public MemberResDto getMemberDetail(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("해당회원이 존재하지 않습니다."));
        return convertEntityToDto(member);
    }

    // 회원 상세 조회 및 작성 게시글 조회
    public MemberResDto getMemberDetailBoards(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("해당회원이 존재하지 않습니다."));
        return convertEntityToDtowBoards(member);
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
    private MemberResDto convertEntityToDto(Member member){
        MemberResDto memberResDto = new MemberResDto();
        memberResDto.setEmail(member.getEmail());
        memberResDto.setName(member.getName());
        memberResDto.setRegDate(member.getRegDate());
        memberResDto.setImagePath(member.getImgPath());
        return memberResDto;
    }

    // Member Entity => MemberRestDto
    private MemberResDto convertEntityToDtowoBoards(Member member){
        MemberResDto memberResDto = new MemberResDto();
        memberResDto.setEmail(member.getEmail());
        memberResDto.setName(member.getName());
        memberResDto.setRegDate(member.getRegDate());
        memberResDto.setImagePath(member.getImgPath());
        memberResDto.setBoards(new ArrayList<>());
        return memberResDto;
    }

    // Member Entity => MemberRestDto
    private MemberResDto convertEntityToDtowBoards(Member member){
        MemberResDto memberResDto = new MemberResDto();
        memberResDto.setEmail(member.getEmail());
        memberResDto.setName(member.getName());
        memberResDto.setRegDate(member.getRegDate());
        memberResDto.setImagePath(member.getImgPath());

        List<BoardResDto> boardResDtoList = new ArrayList<>();
        List<Board> boards = member.getBoards();
        for (Board board : boards){
            boardResDtoList.add(convertEntityToDto(board));
        }
        memberResDto.setBoards(boardResDtoList);
        return memberResDto;
    }

    private BoardResDto convertEntityToDto(Board board){
        BoardResDto boardResDto = new BoardResDto();
        boardResDto.setBoardId(board.getId());
        boardResDto.setTitle(board.getTitle());
        boardResDto.setContent(board.getContent());
        boardResDto.setImgPath(board.getImgPath());
        boardResDto.setRegDate(board.getRegDate());
        boardResDto.setEmail(board.getMember().getEmail());
        return boardResDto;
    }
}
// 1. POST MAN으로 회원 존재여부 확인, 회원가입, 로그인
// 2. 회원 전체 조회 및 회원 이메일 조회 만들기 (POST MAN)
// 3. 스웨거 등록 후 회원 존재여부 확인, 회원가입, 로그인, 회원 전체조회, 개별 회원 조회, 회원 삭제
// 4. MemberController : 회원 전체 조회, 개별 회원 조회, 회원 정보 수정, 회원 삭제