package com.project.zoopiter.domain.bbsc.dao;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@ToString
@Slf4j
@Repository
@RequiredArgsConstructor
public class BbscDAOImpl implements BbscDAO {

  private final JdbcTemplate template;

  /**
   * 게시글 등록
   *
   * @param
   * @return
   */
  @Override
  public Long save(Bbsc bbsc) {
    StringBuffer sql = new StringBuffer();
    sql.append(" insert into bbsc ( ");
    sql.append(" BBSC_ID, ");
    sql.append(" BC_TITLE, ");
    sql.append(" BC_CONTENT, ");
    sql.append(" PET_TYPE, ");
    sql.append(" C_ATTACHMENT, ");
    sql.append(" BC_GUBUN ");
    sql.append(" USER_NICK, ");
    sql.append(" BC_CDATE, ");
    sql.append(" ) values ( ");
    sql.append(" :bbscId, ");
    sql.append(" :bcTitle, ");
    sql.append(" :bcContent, ");
    sql.append(" :petType, ");
    sql.append(" :cAttachment, ");
    sql.append(" :bcGUBUN ");
    sql.append(" :userNick, ");
    sql.append(" :bcCdate, ");
    sql.append(" ) ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"bbsc_id"});
        pstmt.setString(1, bbsc.getBcGubun());
        pstmt.setString(2, bbsc.getBcTitle());
        pstmt.setString(3, bbsc.getUserNick());
        pstmt.setString(4, bbsc.getBcContent());
        return pstmt;
      }
    },keyHolder);

    return Long.valueOf(keyHolder.getKeys().get("bbsc_id").toString());
  }

  //목록
  @Override
  public List<Bbsc> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT ");
    sql.append(" BBSC_ID, ");
    sql.append(" BC_TITLE, ");
    sql.append(" BC_CONTENT, ");
    sql.append(" PET_TYPE, ");
    sql.append(" C_ATTACHMENT, ");
    sql.append(" BC_GUBUN ");
    sql.append(" USER_NICK, ");
    sql.append(" BC_CDATE, ");
    sql.append(" FROM ");
    sql.append(" bbsc ");
    sql.append(" Order by bgroup desc, step asc ");

    List<Bbsc> list = template.query(sql.toString(), new BeanPropertyRowMapper<>(Bbsc.class));

    return list;
  }

  //카테고리별 목록
  @Override
  public List<Bbsc> findAll(String category) {
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT ");
    sql.append(" BBSC_ID, ");
    sql.append(" BC_TITLE, ");
    sql.append(" BC_CONTENT, ");
    sql.append(" PET_TYPE, ");
    sql.append(" C_ATTACHMENT, ");
    sql.append(" BC_GUBUN ");
    sql.append(" USER_NICK, ");
    sql.append(" BC_CDATE, ");
    sql.append(" FROM ");
    sql.append(" bbsc ");
    sql.append("WHERE bcategory = ? ");
    sql.append(" Order by bgroup desc, step asc ");

    List<Bbsc> list = template.query(sql.toString(), new BeanPropertyRowMapper<>(Bbsc.class),category);

    return list;
  }

  @Override
  public List<Bbsc> findAll(int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append(" SELECT ");
    sql.append("    ROW_NUMBER() OVER (ORDER BY bgroup DESC, step ASC) no, ");
    sql.append(" BBSC_ID, ");
    sql.append(" BC_TITLE, ");
    sql.append(" BC_CONTENT, ");
    sql.append(" PET_TYPE, ");
    sql.append(" C_ATTACHMENT, ");
    sql.append(" BC_GUBUN ");
    sql.append(" USER_NICK, ");
    sql.append(" BC_CDATE, ");
    sql.append("    FROM bbsc) t1 ");
    sql.append("where t1.no between ? and ? ");

    List<Bbsc> list = template.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Bbsc.class),
        startRec, endRec
    );
    return list;
  }

  @Override
  public List<Bbsc> findAll(String category, int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append(" SELECT ");
    sql.append("      ROW_NUMBER() OVER (ORDER BY bgroup DESC, step ASC) no, ");
    sql.append(" BBSC_ID, ");
    sql.append(" BC_TITLE, ");
    sql.append(" BC_CONTENT, ");
    sql.append(" PET_TYPE, ");
    sql.append(" C_ATTACHMENT, ");
    sql.append(" BC_GUBUN ");
    sql.append(" USER_NICK, ");
    sql.append(" BC_CDATE, ");
    sql.append("    FROM bbsc ");
    sql.append("   where bcategory = ? ) t1 ");
    sql.append("where t1.no between ? and ? ");

    List<Bbsc> list = template.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Bbsc.class),
        category, startRec, endRec
    );
    return list;
  }

  //검색
  @Override
  public List<Bbsc> findAll(BbscFilterCondition filterCondition) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT  ROW_NUMBER() OVER (ORDER BY bgroup DESC, step ASC) no, ");
    sql.append(" BBSC_ID, ");
    sql.append(" BC_TITLE, ");
    sql.append(" BC_CONTENT, ");
    sql.append(" PET_TYPE, ");
    sql.append(" C_ATTACHMENT, ");
    sql.append(" BC_GUBUN ");
    sql.append(" USER_NICK, ");
    sql.append(" BC_CDATE, ");
    sql.append("    FROM bbsc ");
    sql.append("     WHERE ");

    //분류
    sql = dynamicQuery(filterCondition, sql);

    sql.append(") t1 ");
    sql.append("where t1.no between ? and ? ");


    List<Bbsc> list = null;

    //게시판 전체
    if(StringUtils.isEmpty(filterCondition.getCategory())){
      list = template.query(
          sql.toString(),
          new BeanPropertyRowMapper<>(Bbsc.class),
          filterCondition.getStartRec(),
          filterCondition.getEndRec()
      );
      //게시판 분류
    }else{
      list = template.query(
          sql.toString(),
          new BeanPropertyRowMapper<>(Bbsc.class),
          filterCondition.getCategory(),
          filterCondition.getStartRec(),
          filterCondition.getEndRec()
      );
    }

    return list;
  }

  //조회
  @Override
  public Bbsc findByBbscId(Long bbscId) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT  ");
    sql.append(" BBSC_ID, ");
    sql.append(" BC_TITLE, ");
    sql.append(" BC_CONTENT, ");
    sql.append(" PET_TYPE, ");
    sql.append(" C_ATTACHMENT, ");
    sql.append(" BC_GUBUN ");
    sql.append(" USER_NICK, ");
    sql.append(" BC_CDATE, ");
    sql.append("FROM  ");
    sql.append("  bbsc ");
    sql.append("where bbsc_id = ?  ");

    Bbsc bbscItem = null;
    try {
      bbscItem = template.queryForObject(
          sql.toString(),
          new BeanPropertyRowMapper<>(Bbsc.class),
          bbscId);
    }catch (Exception e){ // 1건을 못찾으면
      bbscItem = null;
    }

    return bbscItem;
  }

  //삭제
  @Override
  public int deleteByBbscId(Long bbscId) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM bbsc ");
    sql.append(" WHERE bbsc_id = ? ");

    int updateItemCount = template.update(sql.toString(), bbscId);

    return updateItemCount;
  }

  //수정
  @Override
  public int update(Long bbscId, Bbsc bbsc) {

    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE bbsc ");
    sql.append("   SET bc_gubun = ?, ");
    sql.append("       bc_title = ?, ");
    sql.append("       bc_content = ?, ");
    sql.append("       bc_udate = systimestamp ");
    sql.append(" WHERE bbsc_id = ? ");

    int updatedItemCount = template.update(
        sql.toString(),
        bbsc.getBcGubun(),
        bbsc.getBcTitle(),
        bbsc.getBcContent(),
        bbsc.getBbscId()
    );

    return updatedItemCount;
  }

  //전체건수
  @Override
  public int totalCount() {

    String sql = "select count(*) from bbsc";

    Integer cnt = template.queryForObject(sql, Integer.class);

    return cnt;
  }

  @Override
  public int totalCount(String bcategory) {

    String sql = "select count(*) from bbs where bcategory = ? ";

    Integer cnt = template.queryForObject(sql, Integer.class, bcategory);

    return cnt;
  }

  @Override
  public int totalCount(BbscFilterCondition filterCondition) {

    StringBuffer sql = new StringBuffer();

    sql.append("select count(*) ");
    sql.append("  from bbsc  ");
    sql.append(" where  ");

    sql = dynamicQuery(filterCondition, sql);

    Integer cnt = 0;
    //게시판 전체 검색 건수
    if(StringUtils.isEmpty(filterCondition.getCategory())) {
      cnt = template.queryForObject(
          sql.toString(), Integer.class
      );
      //게시판 분류별 검색 건수
    }else{
      cnt = template.queryForObject(
          sql.toString(), Integer.class,
          filterCondition.getCategory()
      );
    }

    return cnt;
  }

  private StringBuffer dynamicQuery(BbscFilterCondition filterCondition, StringBuffer sql) {
    //분류
    if(StringUtils.isEmpty(filterCondition.getCategory())){

    }else{
      sql.append("       bcGubun = ? ");
    }

    //분류,검색유형,검색어 존재
    if(!StringUtils.isEmpty(filterCondition.getCategory()) &&
        !StringUtils.isEmpty(filterCondition.getSearchType()) &&
        !StringUtils.isEmpty(filterCondition.getKeyword())){

      sql.append(" AND ");
    }

    //검색유형
    switch (filterCondition.getSearchType()){
      case "TC":  //제목 + 내용
        sql.append("    (  bcTitle    like '%"+ filterCondition.getKeyword()+"%' ");
        sql.append("    or bcContent like '%"+ filterCondition.getKeyword()+"%' )");
        break;
      case "T":   //제목
        sql.append("       bcTitle    like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "C":   //내용
        sql.append("       bcContent like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "N":   //별칭
        sql.append("       userNick like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      default:
    }
    return sql;
  }
}

