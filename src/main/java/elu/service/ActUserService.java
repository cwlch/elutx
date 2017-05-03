package elu.service;

import elu.dao.ActUserMapper;
import elu.model.ActUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

 /**
 * Created by Ch on 17/4/25.
 */
@Service("actUserService")
public class ActUserService {

    @Autowired
    private ActUserMapper actUserDao;

    public int insert(ActUser record){
        return actUserDao.insert(record);
    }

    public List<ActUser> selectByValue(Map map){
        return actUserDao.selectByValue(map);
    }
}
