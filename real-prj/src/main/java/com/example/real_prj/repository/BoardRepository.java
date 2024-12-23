package com.example.real_prj.repository;

import com.example.real_prj.dto.BoardReqDto;
import com.example.real_prj.entity.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByTitleContaining(String keyword); // 제목 검색
    List<Board> findByTitleOrContentContaining(String title, String content);

    @EntityGraph(attributePaths = "comments")
    Optional<Board> findById(Long id);
}
