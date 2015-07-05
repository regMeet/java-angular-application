package com.company.project.services.implementations;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.project.services.implementations.UserServiceImpl;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring-context.xml")
//@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class UserServiceImplTest {

	@Autowired
	UserServiceImpl userService;

}
