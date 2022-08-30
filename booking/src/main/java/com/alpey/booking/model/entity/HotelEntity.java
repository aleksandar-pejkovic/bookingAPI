package com.alpey.booking.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "hotels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HotelEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String name;
	private String city;
	private int stars;
	@JsonIgnore
	@OneToMany(mappedBy = "hotel")
	private List<BookingEntity> bookings;

}
