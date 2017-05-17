package elu.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;


/**
 * 启动监听器
 *
 */
@Service("quartzStartupListener")
public class QuartzStartupListener implements ApplicationListener<ContextRefreshedEvent> {

	protected static final Logger logger = Logger.getLogger(QuartzStartupListener.class);
	
	@Autowired
	private UpdateCacheTask updateCacheTask;
	
    public void onApplicationEvent(ContextRefreshedEvent evt) {
//        if (evt.getApplicationContext().getParent() == null) {
//            quartzInit();
//        }
    	  quartzInit();
    }

    /**
     * 定时任务初始化，初始主任务信息
     */
    private void quartzInit() {
    	updateCacheTask.taskCycle();
    	
    }
    
}

