package com.guico.sharingwork.repository;

import com.guico.sharingwork.entity.WorkBookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkBookRepository extends MongoRepository<WorkBookEntity, String> {
}
