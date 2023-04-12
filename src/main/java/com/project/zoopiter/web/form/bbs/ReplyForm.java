package com.project.zoopiter.web.form.bbs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ReplyForm {

  @NotBlank
  @Size(min=5,max = 11)
  private String bcGubun;     //  게시판 구분(병원후기: B0101, 커뮤니티: B0102)
  @NotBlank
  @Size(min=5,max=50)
  private String bcTitle;         //  제목 TITLE	VARCHAR2(150 BYTE)
  @NotBlank
  @Size(min=3,max=15)
  private String userNick;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)
  @NotBlank
  @Size(min=5)
  private String bcContent;      //  내용 BCONTENT	CLOB

  private List<MultipartFile> files;  // 첨부파일
}
