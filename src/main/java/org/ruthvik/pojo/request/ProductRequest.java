package org.ruthvik.pojo.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class ProductRequest {
	private String title;
	private double price;
	private String description;
	private long categoryId;
	private List<String> images;
}
