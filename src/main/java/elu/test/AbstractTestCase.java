package elu.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lzx
 * @date 2017年3月26日18:37:23
 * 测试用例基类，用于定义一些配置属性
 * 为了不污染数据库，事务定义为自动回滚
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring*.xml" })
@TransactionConfiguration
@Transactional
public class AbstractTestCase extends AbstractTransactionalJUnit4SpringContextTests{
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
