package com.project.zoopiter.domain.comment.svc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class CommentSVCImpl implements CommentSVC {

  private CommentSVCImpl commentSVC;

  /**
   * 게시글 등록
   *
   * @param
   * @return
   */
  @Override
  public Comment save(Comment comment) {
    return commentSVC.save(comment);
  }

  /**
   * 게시글수정
   *
   * @param
   * @param
   */
  @Override
  public void update(String bcContent, Comment comment) {
    return;
  }

  /**
   * 게시글 조회
   *
   * @param
   * @return
   */
  @Override
  public Optional<Comment> findDetail(String userEmail) {
    return null;
  }

  /**
   * 전체 게시글 조회
   *
   * @return
   */
  @Override
  public List<Comment> findAll() {
    return null;
  }
}
