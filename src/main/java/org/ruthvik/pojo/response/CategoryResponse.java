package org.ruthvik.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class CategoryResponse {
	private long id;
	private String name;
	private String image;
	private String creationAt;
	private String updatedAt;
}

