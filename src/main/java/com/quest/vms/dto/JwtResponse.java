package com.quest.vms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
	
	private String jwt;
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	

}
