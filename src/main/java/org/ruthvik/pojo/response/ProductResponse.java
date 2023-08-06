package org.ruthvik.pojo.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class ProductResponse {
	private long id;
	private String title;
	private double price;
	private String description;
	private long categoryId;
	private List<String> images;
	private CategoryResponse category;
	private String creationAt;
	private String updatedAt;
}
