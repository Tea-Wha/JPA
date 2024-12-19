package com.example.real_prj.service;

import com.example.real_prj.dto.BoardReqDto;
import com.example.real_prj.dto.BoardResDto;
import com.example.real_prj.entity.Board;
import com.example.real_prj.entity.Member;
import com.example.real_prj.repository.BoardRepository;
import com.example.real_prj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    // 게시글 등록
    @Transactional
    public boolean saveBoard(BoardReqDto boardReqDto){
        try {
            Member member = memberRepository.findByEmail(boardReqDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
            Board board = new Board();
            board.setTitle(boardReqDto.getTitle());
            board.setContent(boardReqDto.getContent());
            board.setImgPath(boardReqDto.getImgPath());
            board.setMember(member);
            boardRepository.save(board);
            return true;
        }
        catch (Exception e){
            log.error("게시글 등록 실패 : {}", e.getMessage());
            return false;
        }
    }
    // 게시글 상세 조회
    public BoardResDto findByBoardId(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("해당 게시글이 존재하지 않습니다."));
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
