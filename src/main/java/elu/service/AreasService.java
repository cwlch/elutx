package elu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elu.dao.AreasMapper;
import elu.model.Areas;

/** 
* @author liangt 
* @email tianliang211@sina.com
* @createTime：2017年1月2日 下午2:37:22 
* @desc 
*/
@Service("areasService")
public class AreasService {
	
	@Autowired
	private AreasMapper areasDao;
	
	
	public List<Areas> queryAreas(Map<String, Object> map){
		return areasDao.selectByValue(map);
	}
	
	public Areas queryParent(String id,int level){
		String parentId=id.substring(0,id.length()-level*2);
		Areas area=areasDao.selectByPrimaryKey(parentId);
		return area;
	}
	
	
	

}
