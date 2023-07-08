package com.coder.sanam.models;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="tutorials")
public class Data {
	@Id
	@Column(name= "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name= "description")
	private String description;
	@Column(name= "fees")
	private String fees;
	@Column(name= "published")
	private Boolean isPublished;
	
	public Data(String description, String fees, Boolean isPublished) {
		this.description = description;
		this.fees = fees;
		this.isPublished = isPublished;
	} 
}
