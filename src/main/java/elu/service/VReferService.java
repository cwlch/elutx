package elu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elu.dao.VReferMapper;
import elu.model.VRefer;

/** 
* @author liangt 
* @email tianliang211@sina.com
* @createTime：2017年5月10日 下午10:56:38 
* @desc 
*/
@Service("verifyCodeService")
public class VReferService {
	
	@Autowired
	private VReferMapper vReferDao;
	
	public List<VRefer> selectLimit(){
		return vReferDao.selectLimit();
	}
	
	

}
