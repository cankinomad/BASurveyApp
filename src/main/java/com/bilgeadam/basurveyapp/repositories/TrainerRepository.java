package com.bilgeadam.basurveyapp.repositories;

import com.bilgeadam.basurveyapp.entity.Student;
import com.bilgeadam.basurveyapp.entity.Trainer;
import com.bilgeadam.basurveyapp.entity.User;
import com.bilgeadam.basurveyapp.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends BaseRepository<Trainer, Long> {
    @Query("SELECT t FROM Trainer t WHERE t.state = 'ACTIVE' AND t.user.oid = ?1")
    Optional<Trainer> findTrainerByUserOid(Long oid);

    @Query(value = "SELECT * FROM trainers WHERE state = 'ACTIVE' AND  is_master_trainer = 'true'", nativeQuery = true)
    List<Trainer> findAllMasterTrainers();

    @Query(value = "SELECT * FROM trainers WHERE state = 'ACTIVE' AND  is_master_trainer = 'false'", nativeQuery = true)
    List<Trainer> findAllAssistantTrainers();
}
