package com.example.real_prj.repository;

import com.example.real_prj.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByTitleContaining(String keyword); // 제목 검색


}
