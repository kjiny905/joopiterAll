package com.project.zoopiter.web.form.bbs;

import lombok.Data;

@Data
public class DetailForm {
  private Long bbscId;           //  게시글번호
  private String bcGubun;     //  게시판 구분(병원후기: B0101, 커뮤니티: B0102)
  private String bcTitle;         //  제목 TITLE	VARCHAR2(150 BYTE)
  private String userNick;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)
  private String bcContent;      //  내용 BCONTENT	CLOB
  private int bcHit;              //  조회수
}
