package elu.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import elu.model.Areas;
import elu.service.AreasService;
//import elu.util.PinYinTool;

@Component  
public class ChartUpdateTask {
	
	@Autowired
	private AreasService areasService;
	
//    @Scheduled(cron="0 29 18 ? * *") //间隔5秒执行  
    public void taskCycle(){  
    	System.out.println("aa---------------");
//    	updateHeadChart();
    } 
   
  
   /**
    * 修改地址首字母
    */
//   public void updateHeadChart(){
//	   Map map = new HashMap();
//	   List<Areas> list = areasService.queryAreas(map);
//	   System.out.println("list  size:"+list.size());
//	   Areas areasNew = null;
//	   for(Areas areas:list){
//		   areasNew = new Areas();
//		   String id = areas.getId();
//		   String areaName = areas.getAreaName();
//		   if(!StringUtils.isEmpty(areaName)){
//			   String chart = PinYinTool.getPinYinHeadChar(areaName.substring(0,1)).toUpperCase();
//			   System.out.println("获取 "+areaName+" 首字母 ："+chart);
//			   areasNew.setId(areas.getId());
//			   areasNew.setHeaderChart(chart);
//			  int count = areasService.updateAreas(areasNew);
//			  if(count == 1){
//				  System.out.println("修改成功。。。");
//			  }
//		   }
//	   }
//
//   }

}  
