package com.bilgeadam.basurveyapp.repositories.irepository;

import com.bilgeadam.basurveyapp.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IResponseRepository extends JpaRepository<Response, Long> {
    @Query("SELECT r FROM Response r WHERE r.user.oid = ?1 AND r.question.oid = ?2")
    Optional<Response> findByUserOidAndQuestionOid(Long userOid, Long questionOid);
}
