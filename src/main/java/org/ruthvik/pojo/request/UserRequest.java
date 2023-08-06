package org.ruthvik.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
public final class UserRequest {
	private String name;
	private String email;
	private String password;
	private String avatar;
}
