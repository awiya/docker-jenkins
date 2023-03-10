package com.idihia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idihia.contoller.ThemeParkRideController;
import com.idihia.entity.ThemeParkRide;
import com.idihia.repository.ThemeParkRideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ThemeParkRideController.class)
@AutoConfigureMockMvc
public class ApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ThemeParkRideRepository themeParkRideRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getRides_ReturnsRideList() throws Exception {
		ThemeParkRide ride1 = new ThemeParkRide(null,"Ride 1", "A description",1,2);
		ThemeParkRide ride2 = new ThemeParkRide(null,"Ride 2", "Another description",1,2);

		given(themeParkRideRepository.findAll()).willReturn(Arrays.asList(ride1, ride2));

		mockMvc.perform(get("/rides"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name").value(ride1.getName()))
				.andExpect(jsonPath("$[0].description").value(ride1.getDescription()))
				.andExpect(jsonPath("$[1].name").value(ride2.getName()))
				.andExpect(jsonPath("$[1].description").value(ride2.getDescription()));
	}

	@Test
	public void getRide_WithValidId_ReturnsRide() throws Exception {
		ThemeParkRide ride = new ThemeParkRide(null,"Ride 1", "A description",2,2);

		given(themeParkRideRepository.findById(anyLong())).willReturn(Optional.of(ride));

		mockMvc.perform(get("/rides/{id}", 1))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value(ride.getName()))
				.andExpect(jsonPath("$.description").value(ride.getDescription()));
	}

	@Test
	public void getRide_WithInvalidId_ReturnsNotFound() throws Exception {
		given(themeParkRideRepository.findById(anyLong())).willReturn(Optional.empty());

		mockMvc.perform(get("/rides/{id}", 1))
				.andExpect(status().isNotFound());
	}

	@Test
	public void createRide_WithValidRide_ReturnsCreated() throws Exception {
		ThemeParkRide ride = new ThemeParkRide(null,"Ride 1", "A description",3,3);

		given(themeParkRideRepository.save(any(ThemeParkRide.class))).willReturn(ride);

		mockMvc.perform(post("/rides")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(ride)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value(ride.getName()))
				.andExpect(jsonPath("$.description").value(ride.getDescription()));
	}
}
