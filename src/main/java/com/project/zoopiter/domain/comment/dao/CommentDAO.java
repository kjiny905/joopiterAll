package com.project.zoopiter.domain.comment.dao;

import com.project.zoopiter.domain.bbsc.dao.Bbsc;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentDAO {
  /**
   * 댓글등록
   * @param
   * @return
   */
  Bbsc save(Bbsc bbsc);

  /**
   * 댓글수정
   * @param
   * @param
   */
  void update(String bcContent, Bbsc bbsc);

  Comment save(Comment comment);

  void update(String bcContent, Comment comment);

  /**
   * 댓글조회
   * @param
   * @return
   */
  Optional<Bbsc> findDetail(String findDetail);

  List<Bbsc> findAll();

  /**
   * 댓글삭제
   *
   * @param ccId
   */
  void delete(Long ccId);
}
