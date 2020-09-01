package com.quest.vms.service;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.JwtResponse;
import com.quest.vms.dto.LoginRequest;
import com.quest.vms.dto.UserDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GatewayServiceImpl implements GatewayService {

	@Value("${addUserUrl}")
	String addUserUrl;

	@Value("${getUserUrl}")
	String getUserUrl;

	@Value("${listUsersUrl}")
	String listUsersUrl;

	@Value("${deleteUserUrl}")
	String deleteUserUrl;

	@Value("${updateUserUrl}")
	String updateUserUrl;
	
	@Value("${filterListUser}")
	String filterListUser;
	
	@Value("${authUserUrl}")
	String authUserUrl;

	private RestTemplate restTemplate;

	public GatewayServiceImpl() {
		restTemplate = new RestTemplate();
	}

	@Override
	public GenericResponse<UserDTO> addUser(final UserDTO userDto) {
		@SuppressWarnings("unchecked")
		GenericResponse<UserDTO> createUserGenericRes = restTemplate.postForObject(addUserUrl, userDto,
				GenericResponse.class);
		return createUserGenericRes;
	}

	@Override
	public GenericResponse<UserDTO> getUserById(final Integer userId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", userId);
		@SuppressWarnings("unchecked")
		GenericResponse<UserDTO> getUserGenericRes = restTemplate.getForObject(getUserUrl,
				GenericResponse.class, params);
		return getUserGenericRes;
	}

	@Override
	public GenericResponse<UserDTO> listUsers(final String index, final String size, final String sortBy,
			final String orderBy) {
		Map<String, String> params = new HashMap<>();
		params.put("index", index);
		params.put("size", size);
		params.put("sortBy", sortBy);
		params.put("orderBy", orderBy);
		@SuppressWarnings("unchecked")
		GenericResponse<UserDTO> listUserGenericRes = restTemplate.getForObject(listUsersUrl,
				GenericResponse.class, params);
		return listUserGenericRes;
	}

	
	@Override
	public GenericResponse<?> deleteUser(Integer userId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", userId);
		@SuppressWarnings("unchecked")
		GenericResponse<UserDTO> userToBeDeleted = restTemplate.getForObject(getUserUrl, GenericResponse.class,
				params);
		if (userToBeDeleted.getData() == null) {
			userToBeDeleted.setMessage("Delete User Fails");
		} else {
			restTemplate.delete(deleteUserUrl, params);
			userToBeDeleted.setMessage("Delete user success");
			userToBeDeleted.setStatusCode(HttpStatus.OK.value());
		}
		return userToBeDeleted;
	}

	@Override
	public GenericResponse<UserDTO> updateUser(final UserDTO userDto) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", userDto.getUuid());
		@SuppressWarnings("unchecked")
		GenericResponse<UserDTO> updatedUser = restTemplate.getForObject(getUserUrl, GenericResponse.class,
				params);
		if (updatedUser.getData() == null) {
			updatedUser.setMessage("user not found");
		} else {
			restTemplate.put(updateUserUrl, userDto);
		}
		return updatedUser;
	}
	
	@Override
	public GenericResponse<UserDTO> searchUser(String userCategory, String userName) {
		Map<String, String> params = new HashMap<>();
		params.put("userCategory", userCategory);
		params.put("userName", userName);
		@SuppressWarnings("unchecked")
		GenericResponse<UserDTO> listUserGenericRes = restTemplate.getForObject(filterListUser,
				GenericResponse.class, params);
		return listUserGenericRes;
	}

	@Override
	public GenericResponse<JwtResponse> authenticateUser(LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		log.info("GatewayServiceImpl :: authenticateUser ");
		@SuppressWarnings("unchecked")
		GenericResponse<JwtResponse> authUserGenericRes = restTemplate.postForObject(authUserUrl, loginRequest,
				GenericResponse.class);
		return authUserGenericRes;
	}
	
	}
