package com.project.zoopiter.domain.bbsc.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class BbscDAOImplTest {

  @Autowired
  private BbscDAO bbscDAO;



  @Test
  @DisplayName("게시글 건수-전체")
  void totalCountWithSearch(){
    BbscFilterCondition filterCondition = new BbscFilterCondition(
        "","TC","제목"
    );

    int cnt = bbscDAO.totalCount(filterCondition);
    log.info("count={}", cnt);
  }

  @Test
  @DisplayName("게시글 건수-카테고리별")
  void totalCountWithSearchByCategory(){
    BbscFilterCondition filterCondition = new BbscFilterCondition(
        "B0104","TC","제목"
    );

    int cnt = bbscDAO.totalCount(filterCondition);
    log.info("count={}", cnt);
  }
}