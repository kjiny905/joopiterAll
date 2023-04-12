package com.project.zoopiter.domain.bbsc.svc;

import com.project.zoopiter.domain.bbsc.dao.Bbsc;
import com.project.zoopiter.domain.bbsc.dao.BbscFilterCondition;

import java.util.List;

public interface BbscSVC {

  /**
   * 원글작성
   * @param bbsc
   * @return 게시글 번호
   */
  Long save(Bbsc bbsc);

  /**
   * 원글작성-첨부파일 있는경우
   * @param bbsc
   * @param files 첨파일
   * @return 게시글 번호
   */
//  Long save(Bbsc bbsc, List<MultipartFile> files);

  /**
   * 목록
   * @return
   */
  List<Bbsc> findAll();
  List<Bbsc> findAll(int startRec, int endRec);
  List<Bbsc> findAll(String category,int startRec, int endRec);

  /**
   * 검색
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  List<Bbsc> findAll(BbscFilterCondition filterCondition);

  /**
   * 상세 조회
   * @param bbscId 게시글번호
   * @return
   */
  Bbsc findByBbscId(Long bbscId);

  /**
   * 삭제
   * @param bbscId 게시글번호
   * @return 삭제건수
   */
  int deleteByBbscId(Long bbscId);

  /**
   * 수정
   * @param bbscId 게시글 번호
   * @param bbsc 수정내용
   * @return 수정건수
   */

  int update(Long bbscId, Bbsc bbsc);

  /**
   * 전체건수
   * @return 게시글 전체건수
   */
  int totalCount();
  int totalCount(String bcategory);
  int totalCount(BbscFilterCondition filterCondition);

}