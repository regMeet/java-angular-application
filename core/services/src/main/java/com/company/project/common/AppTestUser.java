package com.company.project.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.company.project.persistence.entities.Users;
import com.company.project.services.interfaces.UserService;
import com.google.common.base.Optional;

public class AppTestUser {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/config/ApplicationContext.xml");

		UserService cityService = (UserService) context.getBean("userService");

		/** select **/
		Optional<Users> user = cityService.findById(9);
		System.out.println(user.get());
		
		/** insert **/
		Users user2 = new Users();
		user2.setName("test");
		cityService.create(user2);

		/** update **/
		user2.setName("updated");
		cityService.update(user2);

		/** delete **/
		cityService.delete(user.get());
		
		cityService.delete(user2.getIdUser());

		System.out.println("Done");
	}
}
