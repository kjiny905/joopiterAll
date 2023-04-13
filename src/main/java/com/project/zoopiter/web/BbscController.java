package com.project.zoopiter.web;

import com.project.zoopiter.domain.bbsc.dao.Bbsc;
import com.project.zoopiter.domain.bbsc.dao.BbscFilterCondition;
import com.project.zoopiter.domain.bbsc.svc.BbscSVC;
import com.project.zoopiter.domain.common.code.Code;
import com.project.zoopiter.domain.common.code.CodeDAO;
import com.project.zoopiter.domain.common.paging.FindCriteria;
import com.project.zoopiter.web.form.bbsc.AddForm;
import com.project.zoopiter.web.form.bbsc.DetailForm;
import com.project.zoopiter.web.form.bbsc.EditForm;
import com.project.zoopiter.web.form.bbsc.ListForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/bbsc")
public class BbscController {

  private final BbscSVC bbscSVC;

  private final CodeDAO codeDAO;


  @Autowired
  @Qualifier("fc10") //동일한 타입의 객체가 여러개있을때 빈이름을 명시적으로 지정해서 주입받을때
  private FindCriteria fc;


  //게시판 코드,디코드 가져오기
  @ModelAttribute("classifier")
  public List<Code> classifier(){
    return codeDAO.code("B01");
  }

  @ModelAttribute("bbsTitle")
  public Map<String,String> bbsTitle(){
    List<Code> codes = codeDAO.code("B01");
    Map<String,String> btitle = new HashMap<>();
    for (Code code : codes) {
      btitle.put(code.getCode(), code.getDecode());
    }
    return btitle;
  }


  //작성양식
  @GetMapping("/add")
  public String addForm(
      Model model,
      @RequestParam(required = false) Optional<String> category,
      HttpSession session) {

    String cate = getCategory(category);

    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);

    AddForm addForm = new AddForm();
    addForm.setUserNick(loginMember.getUserNick());
    model.addAttribute("addForm", addForm);
    model.addAttribute("category", cate);

    return "board/board_com-add";
  }


  //작성처리
  @PostMapping("/add")
  public String add(
      //@Valid
      @ModelAttribute AddForm addForm,
      @RequestParam(required = false) Optional<String> category,
      BindingResult bindingResult,      // 폼객체에 바인딩될때 오류내용이 저장되는 객체
      HttpSession session,
      RedirectAttributes redirectAttributes) throws IOException {
    log.info("addForm={}",addForm);

    if(bindingResult.hasErrors()){
      log.info("add/bindingResult={}",bindingResult);
      return "board/board_com-add";
    }

    String cate = getCategory(category);

    Bbsc bbsc = new Bbsc();
    BeanUtils.copyProperties(addForm, bbsc);

    //세션 가져오기
    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
    //세션 정보가 없으면 로그인페이지로 이동
    if(loginMember == null){
      return "redirect:/login";
    }

    //세션에서 이메일,별칭가져오기
//    bbsc.setEmail(loginMember.getEmail());
    bbsc.setUserNick(loginMember.getUserNick());


//    Long originId = 0l;
//    //파일첨부유무
//    if(addForm.getFiles().size() == 0) {
//      originId = bbscSVC.save(bbsc);
//    }else{
//      originId = bbscSVC.save(bbsc, addForm.getFiles());
//    }
//    redirectAttributes.addAttribute("id", originId);
//    redirectAttributes.addAttribute("category",cate);
//    // <=서버응답 302 get http://서버:port/bbs/10
//    // =>클라이언트요청 get http://서버:port/bbs/10
    return "redirect:/bbsc/{id}";
  }

  //전체목록
  @GetMapping({"/list",
      "/list/{reqPage}",
      "/list/{reqPage}//",
      "/list/{reqPage}/{searchType}/{keyword}"})
  public String listAndReqPage(
      @PathVariable(required = false) Optional<Integer> reqPage,
      @PathVariable(required = false) Optional<String> searchType,
      @PathVariable(required = false) Optional<String> keyword,
      @RequestParam(required = false) Optional<String> category,
      Model model) {
    log.info("/list 요청됨{},{},{},{}",reqPage,searchType,keyword,category);

    String cate = getCategory(category);

    //FindCriteria 값 설정
    fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청없으면 1
    fc.setSearchType(searchType.orElse(""));  //검색유형
    fc.setKeyword(keyword.orElse(""));        //검색어

    List<Bbsc> list = null;
    //게시물 목록 전체
    if(category == null || StringUtils.isEmpty(cate)) {

      //검색어 있음
      if(searchType.isPresent() && keyword.isPresent()){
        BbscFilterCondition filterCondition = new BbscFilterCondition(
            "",fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            searchType.get(),
            keyword.get());
        fc.setTotalRec(bbscSVC.totalCount(filterCondition));
        fc.setSearchType(searchType.get());
        fc.setKeyword(keyword.get());
        list = bbscSVC.findAll(filterCondition);

        //검색어 없음
      }else {
        //총레코드수
        fc.setTotalRec(bbscSVC.totalCount());
        list = bbscSVC.findAll(fc.getRc().getStartRec(), fc.getRc().getEndRec());
      }

      //카테고리별 목록
    }else{
      //검색어 있음
      if(searchType.isPresent() && keyword.isPresent()){
        BbscFilterCondition filterCondition = new BbscFilterCondition(
            category.get(),fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            searchType.get(),
            keyword.get());
        fc.setTotalRec(bbscSVC.totalCount(filterCondition));
        fc.setSearchType(searchType.get());
        fc.setKeyword(keyword.get());
        list = bbscSVC.findAll(filterCondition);
        //검색어 없음
      }else {
        fc.setTotalRec(bbscSVC.totalCount(cate));
        list = bbscSVC.findAll(cate, fc.getRc().getStartRec(), fc.getRc().getEndRec());
      }
    }

    List<ListForm> partOfList = new ArrayList<>();
    for (Bbsc bbsc : list) {
      ListForm listForm = new ListForm();
      BeanUtils.copyProperties(bbsc, listForm);
      partOfList.add(listForm);
    }

    model.addAttribute("list", partOfList);
    model.addAttribute("fc",fc);
    model.addAttribute("category", cate);

    return "board/board_com";
  }

  //조회
  @GetMapping("/{bbscId}")
  public String detail(
      @PathVariable Long bbscId,
      @RequestParam(required = false) Optional<String> category,
      Model model) {

    String cate = getCategory(category);

    Bbsc detailBbsc = bbscSVC.findByBbscId(bbscId);
    DetailForm detailForm = new DetailForm();
    BeanUtils.copyProperties(detailBbsc, detailForm);
    model.addAttribute("detailForm", detailForm);
    model.addAttribute("category", cate);

    //첨부조회
//    List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(detailBbs.getBcategory(), detailBbs.getBbsId());
//    if(attachFiles.size() > 0){
//      log.info("attachFiles={}",attachFiles);
//      model.addAttribute("attachFiles", attachFiles);
//    }

    return "board/board_com-detail";
  }

  //삭제
  @GetMapping("/{bbscId}/del")
  public String del(
      @PathVariable Long bbscId,
      @RequestParam(required = false) Optional<String> category) {

    bbscSVC.deleteByBbscId(bbscId);
    String cate = getCategory(category);
    return "redirect:/bbsc/list?category="+cate;
  }

  //수정양식
  @GetMapping("/{bbscId}/edit")
  public String editForm(
      @PathVariable Long bbscId,
      @RequestParam(required = false) Optional<String> category,
      Model model){
    String cate = getCategory(category);
    Bbsc bbsc = bbscSVC.findByBbscId(bbscId);

    EditForm editForm = new EditForm();
    BeanUtils.copyProperties(bbsc,editForm);
    model.addAttribute("editForm", editForm);
    model.addAttribute("category",cate);

    //첨부조회
//    List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(bbs.getBcategory(), bbs.getBbsId());
//    if(attachFiles.size() > 0){
//      log.info("attachFiles={}",attachFiles);
//      model.addAttribute("attachFiles", attachFiles);
//    }

    return "board/board_com-edit";
  }

  //수정처리
  @PostMapping("/{bbscId}/edit")
  public String edit(
      @PathVariable Long bbscId,
      @RequestParam(required = false) Optional<String> category,
      @Valid @ModelAttribute EditForm editForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {

    if(bindingResult.hasErrors()){
      return "board/board_com-edit";
    }

    String cate = getCategory(category);
    Bbsc bbsc = new Bbsc();
    BeanUtils.copyProperties(editForm, bbsc);
    bbscSVC.update(bbscId,bbsc);

//    if(editForm.getFiles().size() == 0) {
//      bbscSVC.update(bbscId, bbsc);
//    }else{
//      bbscSVC.update(bbscId, bbsc, editForm.getFiles());
//    }
    redirectAttributes.addAttribute("bbscId",bbscId);
    redirectAttributes.addAttribute("category", cate);

    return "redirect:/bbsc/{bbscId}";
  }

  //쿼리스트링 카테고리 읽기, 없으면 ""반환
  private String getCategory(Optional<String> category) {
    String cate = category.isPresent()? category.get():"";
    log.info("category={}", cate);
    return cate;
  }
}



