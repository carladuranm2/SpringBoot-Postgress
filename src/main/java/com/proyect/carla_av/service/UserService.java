package com.proyect.carla_av.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyect.carla_av.model.Person;
import com.proyect.carla_av.repository.UserRepository;



@Service
public class UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	public Object createUser(Person reqData) {
		String encodedPassword = passwordEncoder.encode(reqData.getPassword());
		reqData.setPassword(encodedPassword);
		return userRepository.save(reqData);
	}

	public Object getAllUsers() {
		return userRepository.findAll();
	}

	public Person isDataExist(Person reqData) {
		return userRepository.findByEmailAndEdad(reqData.getEmail(), reqData.getEdad());
	}

	public Object getUserById(Long id) {
		return userRepository.findById(id);
	}

	public Object updateUser(Person reqData, Person isData) {
		isData.setName(reqData.getName());
		isData.setEmail(reqData.getEmail());
		isData.setEdad(reqData.getEdad());
		isData.setPassword(reqData.getPassword());
		isData.setTelefono(reqData.getTelefono());
		isData.setDireccion(reqData.getDireccion());
		isData.setLogin(reqData.getLogin());
		isData.setRol(reqData.getRol());
		return userRepository.save(isData);
	}

	public Object deleteUserById(Long id) {
		userRepository.deleteById(id);
		return null;
	}
}
