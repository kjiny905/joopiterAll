package com.project.zoopiter.domain.comment.svc;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentSVC {
  /**
   * 게시글 등록
   *
   * @param
   * @return
   */
  Comment save(Comment comment);

  /**
   * 게시글수정
   *
   * @param
   * @param
   */
  void update(String bcContent, Comment comment);

  /**
   * 게시글 조회
   *
   * @param
   * @return
   */
  Optional<Comment> findDetail(String findDetail);

  /**
   * 전체 게시글 조회
   *
   * @return
   */
  List<Comment> findAll();
}