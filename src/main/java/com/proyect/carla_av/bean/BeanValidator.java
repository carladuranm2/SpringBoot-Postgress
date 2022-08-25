package com.proyect.carla_av.bean;


import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import com.proyect.carla_av.model.Person;

@Component
public class BeanValidator {
	
	public Validator getValidator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

	public ArrayList<String> userValidate(Person user) {
		ArrayList<String> arrayList = new ArrayList<>();
		Set<ConstraintViolation<Person>> constraintViolations = getValidator().validate(user);
		for (ConstraintViolation<Person> constraintViolation : constraintViolations) {
			if (constraintViolation.getPropertyPath().toString().equals("name")) {
				arrayList.add(constraintViolation.getMessage());
			}
			if (constraintViolation.getPropertyPath().toString().equals("email")) {
				arrayList.add(constraintViolation.getMessage());
			}
		}
		return arrayList;
	}

}
