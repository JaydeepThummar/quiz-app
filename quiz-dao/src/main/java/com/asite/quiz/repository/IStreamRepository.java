package com.asite.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asite.quiz.entity.StreamEntity;

@Repository
public interface IStreamRepository extends JpaRepository<StreamEntity, Long> {

}
