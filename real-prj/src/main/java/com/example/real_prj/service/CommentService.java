package com.example.real_prj.service;

import com.example.real_prj.dto.CommentReqDto;
import com.example.real_prj.dto.CommentResDto;
import com.example.real_prj.entity.Board;
import com.example.real_prj.entity.Comment;
import com.example.real_prj.entity.Member;
import com.example.real_prj.repository.BoardRepository;
import com.example.real_prj.repository.CommentRepository;
import com.example.real_prj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j // log message
@Service // 스프링 컨테이너에 빈 등록
@RequiredArgsConstructor // 생성자를 자동으로 생성
public class CommentService {
    private final MemberRepository memberRepository; // member 정보를 가져오기 위해서
    private final BoardRepository boardRepository; // 게시글 정보를 가져오기 위해서
    private final CommentRepository commentRepository; // 댓글 작성을 위해서

    // 댓글 등록
    @Transactional // 동시에 여러 댓글을 작성할 수 있다.
    public boolean commentWrite(CommentReqDto commentReqDto){
        try{
            Member member = memberRepository.findByEmail(commentReqDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
            Board board = boardRepository.findById(commentReqDto.getBoardId())
                    .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
            Comment comment = new Comment();
            comment.setBoard(board);
            comment.setMember(member);
            comment.setContent(commentReqDto.getContent());
            commentRepository.save(comment);
            return true;
        }
        catch (Exception e){
            log.error("댓글 등록 실패 : {}",e.getMessage());
            return false;
        }
    }
    // 댓글 삭제
    public boolean commentDelete(Long id, String email){
        try{
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("해당 댓글이 존재하지 않습니다."));
            String commentAuth = comment.getMember().getEmail();
            if (commentAuth.equals(email)){
                commentRepository.delete(comment);
                return true;
            }
            else{
                log.error("댓글 삭제 권한이 없습니다.");
                return false;
            }
        }
        catch (Exception e){
            log.error("댓글 삭제 실패 : {}", e.getMessage());
            return false;
        }
    }

    // 댓글 목록 조회
    public List<CommentResDto> commentViewAll(Long id){
            Board board = boardRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
            List<Comment> comments = commentRepository.findByBoard(board);
            List<CommentResDto> commentResDtoList = new ArrayList<>();
            for (Comment comment : comments){
                commentResDtoList.add(convertEntityToDto(comment));
            }
            return commentResDtoList;
    }

    private CommentResDto convertEntityToDto (Comment comment){
        CommentResDto commentResDto = new CommentResDto();
        commentResDto.setEmail(comment.getMember().getEmail());
        commentResDto.setBoardId(comment.getBoard().getId());
        commentResDto.setCommentId(comment.getCommentId());
        commentResDto.setContent(comment.getContent());
        commentResDto.setRegDate(comment.getRegDate());
        return commentResDto;
    }
}


