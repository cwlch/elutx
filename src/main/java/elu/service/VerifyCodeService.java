package elu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elu.dao.VerifyCodeMapper;
import elu.model.VerifyCode;

/** 
* @author lzx 
* @createTime：2017-3-25 23:18:06 
* @desc 
*/
@Service("verifyCodeService")
public class VerifyCodeService {
	
	@Autowired
	private VerifyCodeMapper verifyCodeDao;
	
	public boolean insertVfCode( VerifyCode vfCode){
		int count = verifyCodeDao.insertSelective(vfCode);
		if(count > 0){
			return true;
		}
		return false;
	}

	public VerifyCode queryVerifyCodeByUid(String uid) {
		VerifyCode code = verifyCodeDao.selectByPrimaryKey(uid);
		return code;
	}

	public boolean updateVfCodeById(VerifyCode oldCode) {
		int count = verifyCodeDao.updateByPrimaryKeySelective(oldCode);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	
	
}
