package com.proyect.carla_av.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.proyect.carla_av.service.UserService;
import com.proyect.carla_av.model.Person;
import com.proyect.carla_av.bean.BeanValidator;
import com.proyect.carla_av.bean.ResultDTO;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.HEAD,RequestMethod.PUT,RequestMethod.POST,RequestMethod.OPTIONS,RequestMethod.DELETE})
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private BeanValidator beanValidator;
	
	

	@RequestMapping(method = RequestMethod.POST, value = "/createUser")
	public ResponseEntity<?> createUser(@RequestBody Person reqData) {
		System.err.println(":::  UserController.createUser :::");
		ResultDTO<?> responsePacket = null;
		try {
			System.err.println("::: primero");
			ArrayList<String> errorList = beanValidator.userValidate(reqData);
			if (errorList.size() != 0) {
				System.err.println("::: segundo");
				ResultDTO<ArrayList<String>> errorPacket = new ResultDTO<>(errorList, "Data must not be null", false);
				return new ResponseEntity<>(errorPacket, HttpStatus.BAD_REQUEST);
			}
			Person isData = userService.isDataExist(reqData);
			if (isData == null) {
				System.err.println("::: tercero");
				responsePacket = new ResultDTO<>(userService.createUser(reqData), "User Created Successfully", true);
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>("Record already exist", false);
				System.err.println("::: quinto");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.err.println(e);
			responsePacket = new ResultDTO<>(e.getMessage(), false);
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/allUsers")
	public ResponseEntity<?> allUsers() {
		System.err.println(":::  UserController.allUsers :::");
		ResultDTO<?> responsePacket = null;
		try {
			responsePacket = new ResultDTO<>(userService.getAllUsers(), "Users fetched successfully !!", true);
			return new ResponseEntity<>(responsePacket, HttpStatus.OK);
		} catch (Exception e) {
			responsePacket = new ResultDTO<>(e.getMessage(), false);
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getUserById/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
		System.err.println(":::  UserController.getUserById :::");
		ResultDTO<?> responsePacket = null;
		try {
			responsePacket = new ResultDTO<>(userService.getUserById(id), "User fetched successfully !!", true);
			return new ResponseEntity<>(responsePacket, HttpStatus.OK);
		} catch (Exception e) {
			responsePacket = new ResultDTO<>(e.getMessage(), false);
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/updateUser")
	public ResponseEntity<?> updateUser(@RequestBody Person reqData) {
		System.err.println(":::  UserController.updateUser :::");
		ResultDTO<?> responsePacket = null;
		try {
			Person isData = userService.isDataExist(reqData);
			if (isData != null) {
				responsePacket = new ResultDTO<>(userService.updateUser(reqData, isData), "User Updated Successfully",
						true);
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>("Record not exist", false);
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			responsePacket = new ResultDTO<>(e.getMessage(), false);
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteUserById/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
		System.err.println(":::  UserController.deleteUserById :::");
		ResultDTO<?> responsePacket = null;
		try {
			responsePacket = new ResultDTO<>(userService.deleteUserById(id), "User deleted successfully !!", true);
			return new ResponseEntity<>(responsePacket, HttpStatus.OK);
		} catch (Exception e) {
			responsePacket = new ResultDTO<>(e.getMessage(), false);
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}

}

