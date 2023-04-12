package com.project.zoopiter.domain.bbsc.svc;

import com.project.zoopiter.domain.bbsc.dao.Bbsc;
import com.project.zoopiter.domain.bbsc.dao.BbscDAO;
import com.project.zoopiter.domain.bbsc.dao.BbscFilterCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BbscSVCImpl implements BbscSVC {

  private final BbscDAO bbscDAO;


  //원글
  @Override
  public Long save(Bbsc bbsc) {
    return bbscDAO.save(bbsc);
  }

//  //원글-첨부파일
//  @Override
//  public Long save(Bbsc bbsc, List<MultipartFile> files) {
//
//    //1)원글 저장
//    Long bbscId = save(bbsc);
//
//    //2)첨부 저장
//    uploadFileSVC.addFile(bbsc.getBcGubun(),bbscId,files);
//
//    return bbscId;
//  }


  //목록
  @Override
  public List<Bbsc> findAll() {
    return bbscDAO.findAll();
  }

  @Override
  public List<Bbsc> findAll(int startRec, int endRec) {
    return bbscDAO.findAll(startRec, endRec);
  }

  @Override
  public List<Bbsc> findAll(String category, int startRec, int endRec) {
    return bbscDAO.findAll(category, startRec, endRec);
  }

  @Override
  public List<Bbsc> findAll(BbscFilterCondition filterCondition) {
    return bbscDAO.findAll(filterCondition);
  }

  //상세조회
  @Override
  public Bbsc findByBbscId(Long bbscId) {
    Bbsc findedItem = bbscDAO.findByBbscId(bbscId);
//    bbscDAO.increaseHitCount(bbscId);   //조회수증가
    return findedItem;
  }

  /**
   * 삭제
   *
   * @param bbscId 게시글번호
   * @return 삭제건수
   */
  @Override
  public int deleteByBbscId(Long bbscId) {
    int affectedRow =  bbscDAO.deleteByBbscId(bbscId);

    return affectedRow;
  }

  //수정
  @Override
  public int update(Long bbscId, Bbsc bbsc) {
    return bbscDAO.update(bbscId, bbsc);
  }

  //전체건수
  @Override
  public int totalCount() {
    return bbscDAO.totalCount();
  }

  @Override
  public int totalCount(String bcategory) {
    return bbscDAO.totalCount(bcategory);
  }

  @Override
  public int totalCount(BbscFilterCondition filterCondition) {
    return bbscDAO.totalCount(filterCondition);
  }
}
