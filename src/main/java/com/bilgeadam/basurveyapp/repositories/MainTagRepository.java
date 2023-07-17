package com.bilgeadam.basurveyapp.repositories;

import com.bilgeadam.basurveyapp.entity.enums.Tags;
import com.bilgeadam.basurveyapp.entity.tags.MainTag;
import com.bilgeadam.basurveyapp.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MainTagRepository extends BaseRepository<MainTag, Long>{
    @Query(value = "select count(*)>0 from MainTag a where a.tagClass=?1 and a.tagName=?2")
    Boolean isTagClassAndTagName(Tags tagClass, String tagName);
    Optional<List<MainTag>> findOptionalByTagName(String tagName);
    Optional<MainTag> findOptionalByTagNameAndTagClass(String tagName, Tags tagClass);
    Optional<List<MainTag>> findOptionalByTagClass(Tags tagClass);

}

