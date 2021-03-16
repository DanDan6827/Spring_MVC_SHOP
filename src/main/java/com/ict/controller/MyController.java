package com.ict.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ict.db.CVO;
import com.ict.db.DAO;
import com.ict.db.MVO;
import com.ict.db.VO;

@Controller
public class MyController {
	@Autowired
	private DAO dao;

	
 @RequestMapping("/") public ModelAndView indexCommand() { 
		 ModelAndView mv = new ModelAndView("redirect:/list");
		 String category = "ele002";
		 mv.addObject("category",category);
	 
		 return mv;
	 }
 
 @RequestMapping("/list")
 public ModelAndView listCommand(@RequestParam("category")String category) {
	 ModelAndView mv = new ModelAndView("product_list");
	 List<VO>list = dao.getList(category);
	 mv.addObject("list", list);
	 
	 return mv;
 }
 @RequestMapping("/login_ok")
 public ModelAndView loginOkCommand(MVO m_vo,
		 HttpServletRequest request) {
	 String login = null;
	
	 MVO mvo = new MVO();
	 mvo = dao.getLogin(m_vo);
	 if(mvo==null) {
			return new ModelAndView("error");
		}else {
			//관리자인지 일반회원인지 검사
			if (m_vo.getId().equals("admin")&&m_vo.getPw().equals("admin")) {
				request.getSession().setAttribute("admin", "ok");
				return new ModelAndView("admin");
			}
			login= "ok";
			request.getSession().setAttribute("mvo", mvo);
			request.getSession().setAttribute("login", login);
			return new ModelAndView("redirect:/");
 }
 }	
 
 @RequestMapping("/content")
 public ModelAndView contentCommand(@RequestParam("idx")String idx ) {
	ModelAndView mv = new ModelAndView("product_content");
	 VO vo = new VO();
	 vo = dao.getOneList(idx);
	 mv.addObject("vo",vo);
	 return mv;
	 
 }
 @RequestMapping("/viewCart")
 public ModelAndView viewCartCommand(HttpServletRequest request) {
	 ModelAndView mv = new ModelAndView("viewcart");
	 MVO mvo = (MVO)request.getSession().getAttribute("mvo");
	 String id = mvo.getId();
	 List<CVO>clist = null;
	 clist = dao.getCart(id);
	 mv.addObject("clist", clist);
	 return mv;
	 
 }
 @RequestMapping("/addCart")
 public ModelAndView addCartCommand(@ModelAttribute("idx")String idx,HttpSession session) {
	 //id구하기
	 MVO mvo = (MVO)session.getAttribute("mvo");
	 String id = mvo.getId();
	 
	 //idx를 이용해서 제품정보 구하기
	 VO vo = dao.getOneList(idx);
	 
	 //해당아이디를 가진 사람이 해당제품이 카트에있는지 검색
	 CVO cvo = dao.getCartList(id,vo.getP_num());
	 if (cvo==null) {//현재카트에 같은제품이 없다. 제품추가
		 CVO c_vo=new CVO();
		 c_vo.setP_num(vo.getP_num());
		 c_vo.setP_name(vo.getP_name());
		 c_vo.setP_price(String.valueOf(vo.getP_price()));
		 c_vo.setP_saleprice(String.valueOf(vo.getP_saleprice()));
		 c_vo.setAmount(String.valueOf(1));
		 c_vo.setId(id);
		 int result = dao.getCartInsert(c_vo);
		 
	}else {//현재카트에있다. 수량증가
		int result = dao.getCartUpdate(cvo);
	}
	 return new ModelAndView("redirect:/content");
 }
 @RequestMapping("/edit")
 public ModelAndView editCommand(CVO cvo) {
	ModelAndView mv = new ModelAndView("redirect:/viewCart");
	int result = dao.getCartAmountUpdate(cvo);
	return mv;
 }
 @RequestMapping("/delete")
 public ModelAndView deleteCommand(CVO cvo) {
	 ModelAndView mv = new ModelAndView("redirect:/viewCart");
	 int result = dao.getCartdelete(cvo);
	 return mv;
	 
 }
 @RequestMapping(value = "/product_add",method = RequestMethod.POST)
 public ModelAndView addproductCommand(VO vo , HttpServletRequest request) {
	 try {
		String path = request.getSession().getServletContext().getRealPath("/resources/images");
		MultipartFile file1 = vo.getFile1();
		MultipartFile file2 = vo.getFile2();
		
		vo.setP_image_l(file1.getOriginalFilename());
		vo.setP_image_s(file2.getOriginalFilename());
		
		byte[]in1 = file1.getBytes();
		File out = new File(path,vo.getP_image_l());
		FileCopyUtils.copy(in1, out);
		
		byte[]in2 = file2.getBytes();
		File out2 = new File(path,vo.getP_image_s());
		FileCopyUtils.copy(in2, out2);
		
		int result = dao.getaddproduct(vo);
		return new ModelAndView("redirect:/");
		
		
	} catch (Exception e) {
		System.out.println("케치문에러");
		System.out.println(e);
	}
	 return null;
	 
	 
 }
}
