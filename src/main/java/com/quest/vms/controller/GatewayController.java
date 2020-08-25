package com.quest.vms.controller;

import static com.quest.vms.common.utils.VmsConstants.GATEWAY_URL_PATH;
import static com.quest.vms.common.utils.VmsConstants.ID;
import static com.quest.vms.common.utils.VmsConstants.USER;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.UserDTO;
import com.quest.vms.service.GatewayService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/" + GATEWAY_URL_PATH)
@Api(value = "Visitor Management System", description = "Operations pertaining to Visitor Management System")
@Slf4j
public class GatewayController {
	
	@Autowired
	private GatewayService gatewayService;

	@ApiOperation(value = "Add a User to system")
	@PostMapping(USER)
	public ResponseEntity<GenericResponse<UserDTO>> addUser(@Valid @RequestBody UserDTO user) {
		try {
			GenericResponse<UserDTO> createUserGenericRes = gatewayService.addUser(user);
			return ResponseEntity.status(createUserGenericRes.getStatusCode()).body(createUserGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Get User by Id")
	@GetMapping(USER + "/{" + ID + "}")
	public ResponseEntity<GenericResponse<UserDTO>> getUserById(@PathVariable(value = ID) Integer id) {
		try {
			GenericResponse<UserDTO> getUserGenericRes = gatewayService.getUserById(id);
			return ResponseEntity.status(getUserGenericRes.getStatusCode()).body(getUserGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Get All users from system")
	@GetMapping(USER)
	public ResponseEntity<GenericResponse<UserDTO>> listUsers(
			@RequestParam(value = "index", defaultValue = "0", required = false) String index,
			@RequestParam(value = "size", defaultValue = "10", required = false) String size,
			@RequestParam(value = "sortBy", defaultValue = "firstName", required = false) String sort,
			@RequestParam(value = "orderBy", defaultValue = "ASC", required = false) String orderBy) {

		log.info("list user");
		try {
			GenericResponse<UserDTO> listUserGenericRes = gatewayService.listUsers(index, size, sort, orderBy);
			return ResponseEntity.status(listUserGenericRes.getStatusCode()).body(listUserGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@ApiOperation(value = "Delete User from system")
	@DeleteMapping(USER + "/{id}")
	public ResponseEntity<GenericResponse<?>> deleteUser(@PathVariable(value = "id") Integer userId) {
		try {
			GenericResponse<?> userToBeDeleted = gatewayService.deleteUser(userId);
			return ResponseEntity.status(userToBeDeleted.getStatusCode()).body(userToBeDeleted);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation(value = "Update User details")
	@PutMapping(USER)
	public ResponseEntity<GenericResponse<UserDTO>> updateuser(@RequestBody UserDTO user) {
		try {
			GenericResponse<UserDTO> updateUserGenericResponse = gatewayService.updateUser(user);
			return ResponseEntity.status(updateUserGenericResponse.getStatusCode())
					.body(updateUserGenericResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@ApiOperation(value = "Get filtered users from system")
	@GetMapping("/listUser")
	public ResponseEntity<GenericResponse<UserDTO>> searchUser(
			// approved or not
			@RequestParam(value = "userCategory", required = false) String userCategory,
			@RequestParam(value = "userName", required = false) String userName){
		log.info("list user");
		try {
			GenericResponse<UserDTO> listUserGenericRes = gatewayService.searchUser(userCategory, userName);
			return ResponseEntity.status(listUserGenericRes.getStatusCode()).body(listUserGenericRes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


	
}
