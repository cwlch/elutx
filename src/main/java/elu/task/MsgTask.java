package elu.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import elu.dao.DriverRecordMapper;
import elu.dao.UserRecordMapper;
import elu.model.DriverRecord;
import elu.model.UserRecord;

@Component  
public class MsgTask {
	
	@Autowired
	private DriverRecordMapper driverRecordDao;
	@Autowired
	private UserRecordMapper userRecordDao;
	
    @Scheduled(cron="0 0/5 * * * ?") //间隔5秒执行  
    public void taskCycle(){  
    	driverRecordStatusUpdate();
    	userRecordStatusUpdate();
    } 
   
  
   /**
    * 司机订单更新
    */
   public void driverRecordStatusUpdate(){
	   List<DriverRecord> list = driverRecordDao.queryDriverRecordList();
       if(list != null && list.size() > 0){
       	for(DriverRecord driverRecord : list){
       		if(driverRecord.getdDate() >= (new Date()).getTime()){
       			driverRecord.setdStatus(2);
       			int count = driverRecordDao.updateByPrimaryKeySelective(driverRecord);
       			if(count > 0){
       				System.out.println("司机订单状态失效更新成功，id："+driverRecord.getId()+",dStatus:"+driverRecord.getdStatus());
       			}else{
       				System.out.println("司机订单状态失效更新失败，id："+driverRecord.getId()+",dStatus:"+driverRecord.getdStatus());
       			}
       		}
       	}
       }
   }
   
   /**
    * 乘客订单更新
    */
   public void userRecordStatusUpdate(){
	   List<UserRecord> list = userRecordDao.queryUserRecordList();
       if(list != null && list.size() > 0){
       	for(UserRecord userRecord : list){
       		if(userRecord.getuDate() >= (new Date()).getTime()){
       			userRecord.setuStatus(2);
       			int count = userRecordDao.updateByPrimaryKeySelective(userRecord);
       			if(count > 0){
       				System.out.println("乘客订单状态失效更新成功，id："+userRecord.getId()+",uStatus:"+userRecord.getuStatus());
       			}else{
       				System.out.println("乘客订单状态失效更新失败，id："+userRecord.getId()+",uStatus:"+userRecord.getuStatus());
       			}
       		}
       	}
       }
   }
}  
