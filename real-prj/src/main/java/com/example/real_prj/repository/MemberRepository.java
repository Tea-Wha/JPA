package com.example.real_prj.repository;

import com.example.real_prj.entity.Board;
import com.example.real_prj.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 기본적인 CRUD는 이미 만들어져 있음
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPwd(String pwd);
    Optional<Member> findByEmailAndPwd(String email, String pwd);

    @EntityGraph(attributePaths = "boards")
    Optional<Member> findById(Long id);
}
