package com.project.zoopiter.domain.comment.dao;

import com.project.zoopiter.domain.bbsc.dao.Bbsc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.xml.stream.events.Comment;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public abstract class CommentDAOImpl implements CommentDAO {

  private final NamedParameterJdbcTemplate template;

  /**
   * 댓글등록
   * @param
   * @return
   */
  @Override
  public Comment save(Comment comment) {
    StringBuffer sql = new StringBuffer();
    sql.append(" insert into comment ( ");
    sql.append(" CC_ID, ");
    sql.append(" BBSC_ID, ");
    sql.append(" CC_CONTENT, ");
    sql.append(" USER_NICK, ");
    sql.append(" CC_CDATE, ");
    sql.append(" CC_UDATE, ");
    sql.append(" ) values ( ");
    sql.append(" :ccId, ");
    sql.append(" :bbscId, ");
    sql.append(" :ccContent, ");
    sql.append(" :userNick, ");
    sql.append(" :ccCdate, ");
    sql.append(" :ccUdate, ");
    sql.append(" ) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"CC_ID"});

    String ccId = String.valueOf(keyHolder.getKey());

//    comment.setCcId(ccId);
    return comment;
  }

  /**
   * 댓글수정
   * @param
   * @param
   */
  @Override
  public void update(String bcContent, Comment comment) {
    return;
  }

  /**
   * 댓글조회
   *
   * @param
   * @return
   */
  @Override
  public Optional<Bbsc> findDetail(String findDetail) {
    return null;
  }

  /**
   * 댓글삭제
   * @param ccId
   */
  @Override
  public void delete(Long ccId) {
    return;
  }
}

