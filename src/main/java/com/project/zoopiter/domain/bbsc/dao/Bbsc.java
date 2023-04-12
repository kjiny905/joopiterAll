package com.project.zoopiter.domain.bbsc.dao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Bbsc {
  private Long bbscId;          //  게시글 번호 BBS_ID	NUMBER(10,0)
  private String bcGubun;     //  게시판 구분(병원후기: B0101, 커뮤니티: B0102)
  private String bcTitle;         //  제목 TITLE	VARCHAR2(150 BYTE)
  private String userNick;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)
  private int bcHit;              //  조회수 HIT	NUMBER(5,0)
  private String bcContent;      //  내용 BCONTENT	CLOB
  private LocalDateTime bcCdate;  //  생성일 CDATE	TIMESTAMP(6)
  private LocalDateTime bcUdate;  //  수정일 UDATE	TIMESTAMP(6)
}
