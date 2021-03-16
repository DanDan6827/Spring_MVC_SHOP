package com.ict.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class DAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;


	public List<VO> getList(String category) {
		List<VO>list = new ArrayList<VO>();
		list = sqlSessionTemplate.selectList("list",category);
		return list;
	}

	public MVO getLogin(MVO m_vo) {
		MVO mvo = null;
		mvo = sqlSessionTemplate.selectOne("login",m_vo);
		return mvo;
	}

	public VO getOneList(String idx) {
		VO vo = null;
		vo = sqlSessionTemplate.selectOne("onelist", idx);
		return vo;
	}

	public List<CVO> getCart(String id) {
		List<CVO>list = new ArrayList<CVO>();
		list = sqlSessionTemplate.selectList("cartall",id);
		return list;
	}

	public CVO getCartList(String id, String p_num) {
		CVO cvo = null;
		Map<String, String>map = new HashMap<String, String>();
		map.put("id", id);
		map.put("p_num", p_num);
		cvo=sqlSessionTemplate.selectOne("select_cart",map);
		return cvo;
	}

	public int getCartInsert(CVO cvo) {
		int result = 0;
		result = sqlSessionTemplate.insert("cartInsert",cvo);
		return result;
	}

	public int getCartUpdate(CVO cvo) {
		int result = 0;
		result = sqlSessionTemplate.update("cartupdate", cvo);
		return result;
	}

	public int getCartAmountUpdate(CVO cvo) {
		int result = 0;
		result = sqlSessionTemplate.update("amountupdate", cvo);
		return result;
	}

	public int getCartdelete(CVO cvo) {
		int result =0;
		result = sqlSessionTemplate.delete("deletecart", cvo);
		return result;
	}

	public int getaddproduct(VO vo) {
		int result = 0;
		result = sqlSessionTemplate.insert("add_P", vo);
		return result;
	}

	

	
	
}
