package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDao {

  @PersistenceContext
  private EntityManager entityManager;

  //create a question.

  public QuestionEntity createQuestion(QuestionEntity questionEntity) {
    entityManager.persist(questionEntity);
    return questionEntity;
  }


  // Get question by uuid.

  public QuestionEntity getQuestion(final String questionuuid) {
    try {
      return entityManager.createNamedQuery("QuestionEntityByUuid", QuestionEntity.class)
          .setParameter("uuid", questionuuid).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  //Get question by content.

  public QuestionEntity getQuestionByContent(final String content) {
    try {
      return entityManager.createNamedQuery("QuestionEntityByContent", QuestionEntity.class)
          .setParameter("content", content).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  //update a question.

  public QuestionEntity updateQuestion(final QuestionEntity questionEntity) {
    return entityManager.merge(questionEntity);
  }

  // Find all the questions in the system.

  public List<QuestionEntity> findAll() {
    return entityManager.createQuery("SELECT a FROM QuestionEntity a", QuestionEntity.class)
        .getResultList();
  }

  //find all questions posted by auser.

  public List<QuestionEntity> findAllByUser(UserEntity user) {
    return entityManager.createNamedQuery("QuestionEntitiesByUser", QuestionEntity.class)
        .setParameter("user", user).getResultList();
  }

  //Delete a question.

  @OnDelete(action = OnDeleteAction.CASCADE)
  public QuestionEntity deleteQuestion(final QuestionEntity questionEntity) {
    entityManager.remove(questionEntity);
    return questionEntity;
  }

}
