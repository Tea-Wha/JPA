package com.example.real_prj.service;

import com.example.real_prj.dto.BoardReqDto;
import com.example.real_prj.dto.BoardResDto;
import com.example.real_prj.entity.Board;
import com.example.real_prj.entity.Member;
import com.example.real_prj.repository.BoardRepository;
import com.example.real_prj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
            // 프론트엔드에서 전달한 이메일로 회원 정보 확인 및 가져옴
            Member member = memberRepository.findByEmail(boardReqDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
            Board board = new Board();
            board.setTitle(boardReqDto.getTitle());
            board.setContent(boardReqDto.getContent());
            board.setImgPath(boardReqDto.getImgPath());
            board.setMember(member);
            boardRepository.save(board); // insert
            return true;
        }
        catch (Exception e){
            log.error("게시글 등록 실패 : {}", e.getMessage());
            return false;
        }
    }
    // 게시글 상세 조회
    public BoardResDto findByBoardId(Long id){
        // 댓글 목록을 게시글에 들어갔을 때 처음부터 전부 불러와도 되고
        // 한 번 더 호출하여 (총 2번) 불러와도 된다.
        Board board = boardRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("해당 게시글이 존재하지 않습니다."));
        return convertEntityToDto(board);
    }
    // 게시글 전체 조회
    public List<BoardResDto> findAllBoard(){
        List<Board> boards = boardRepository.findAll();
        List<BoardResDto> boardResDtoList = new ArrayList<>();
        for (Board board : boards){
            // convertEntityToDto를 통해서 BoardResDto로 반환 받아서 List에 추가
            boardResDtoList.add(convertEntityToDto(board));
        }
        return boardResDtoList;
    }
    // 게시글 검색
    public List<BoardResDto> searchBoard(String keyword){
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardResDto> boardResDtoList = new ArrayList<>();
        for (Board board : boards){
            boardResDtoList.add(convertEntityToDto(board));
        }
        return boardResDtoList;
    }
    // 게시글 페이지 수 조회
    public int getBoardPageCount(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return boardRepository.findAll(pageRequest).getTotalPages();
    }
    
    // 게시글 페이징
    public List<BoardResDto> pagingBoardList(int page, int size){
        // 이미 게시글 번호에 따라 정렬이 되어있는 상태 
        // 다른 정렬 조건은 어떻게 하는 것이 부하가 가장 덜 걸릴지에 관하여 고민하고 구현해야함
        // 어떤 부분에서 처리를 해야 할지 고민하는 것이 필요
        Pageable pageable = PageRequest.of(page, size);
        List<Board> boards = boardRepository.findAll(pageable).getContent();
        List<BoardResDto> boardResDtoList = new ArrayList<>();
        for (Board board : boards){
            boardResDtoList.add(convertEntityToDto(board));
        }
        return boardResDtoList;
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
