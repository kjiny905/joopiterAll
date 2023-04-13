package com.project.zoopiter.web.form.bbsc;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListForm {
  private Long bbscId;           //  게시글번호
  private String bcGubun;     //  게시판 구분(병원후기: B0101, 커뮤니티: B0102)
  private String bcTitle;         //  제목 TITLE	VARCHAR2(150 BYTE)
  private String userNickname;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)
  private LocalDateTime bcCdate;  //  작성일
  private int bcHit;              //  조회수
}
