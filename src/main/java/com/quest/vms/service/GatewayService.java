package com.quest.vms.service;

import com.quest.vms.common.utils.GenericResponse;
import com.quest.vms.dto.UserDTO;


public interface GatewayService {

	public GenericResponse<UserDTO> addUser(UserDTO user);

	public GenericResponse<UserDTO> getUserById(Integer id);

	public GenericResponse<?> deleteUser(Integer id);

	public GenericResponse<UserDTO> listUsers(final String pageNo, final String pageSize, final String sortBy, final String orderBy);
	
	public GenericResponse<UserDTO> updateUser(UserDTO user);	
	
	public GenericResponse<UserDTO> searchUser(String userCategory, String userName); 
	
}
