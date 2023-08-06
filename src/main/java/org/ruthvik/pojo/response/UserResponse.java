package org.ruthvik.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class UserResponse {
	private long id;
	private String name;
	private String role;
	private String email;
	private String password;
	private String avatar;
	private String creationAt;
	private String updatedAt;
	
}
