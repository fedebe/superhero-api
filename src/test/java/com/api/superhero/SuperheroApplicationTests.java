package com.api.superhero;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.api.superhero.model.Superhero;
import com.api.superhero.model.SuperheroUpdate;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application.properties")
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(OutputCaptureExtension.class)
class SuperheroApplicationTests {

    @Autowired
    private MockMvc mvc;
	
	@Test
	@Order(1)
    public void createSuperheroAndReturnOk() throws Exception {
        Superhero superhero = new Superhero(null,"Superman", new String[] { "Fliying" });

        mvc.perform(post("/api/superheroes")
                .content(toJson(superhero))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(superhero.name())))
                .andExpect(jsonPath("$.superPowers[0]", is(superhero.superPowers()[0])));
    }
	
	@Test
	@Order(2)
    public void getSuperheroAndReturnOk() throws Exception {
	    Long superheroId = 1L;

        mvc.perform(get("/api/superheroes/".concat(superheroId.toString()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Superman")))
                .andExpect(jsonPath("$.superPowers[0]", is("Fliying")));
    }
	
	@Test
	@Order(3)
    public void updateSuperheroAndReturnOk() throws Exception {
        Long superheroId = 1L;
        
        SuperheroUpdate superhero = new SuperheroUpdate("Spiderman", new String[] { "Spidey-Sense" });

        mvc.perform(patch("/api/superheroes/".concat(superheroId.toString()))
                .content(toJson(superhero))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Spiderman")))
                .andExpect(jsonPath("$.superPowers[0]", is("Spidey-Sense")));
    }
	
	@Test
    @Order(4)
    public void createSuperheroesAndReturnOk() throws Exception {
        Superhero superman = new Superhero(null,"Superman", new String[] { "Fliying" });
        Superhero deadpool = new Superhero(null,"Deadpool", new String[] { "Regenerative Healing Factor" });

        mvc.perform(post("/api/superheroes")
                .content(toJson(superman))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(superman.name())))
                .andExpect(jsonPath("$.superPowers[0]", is(superman.superPowers()[0])));
        
        mvc.perform(post("/api/superheroes")
                .content(toJson(deadpool))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(deadpool.name())))
                .andExpect(jsonPath("$.superPowers[0]", is(deadpool.superPowers()[0])));
    }
	
	@Test
    @Order(5)
    public void getAllSuperheroAndReturnOk() throws Exception {

        mvc.perform(get("/api/superheroes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value(hasItems(is("Spiderman"), is("Deadpool"), is("Superman"))));
    }
	
	@Test
    @Order(6)
    public void getAllSuperheroByWordAndReturnOk() throws Exception {
	    String word = "dead";
	    
        mvc.perform(get("/api/superheroes/by-word").param("byWord", word)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value(hasItems(is("Deadpool"), not("Spiderman"), not("Superman"))));
    }
	
	@Test
    @Order(7)
    public void deleteSuperheroAndReturnOk() throws Exception {
        Long superheroId = 3L;
        
        mvc.perform(delete("/api/superheroes/".concat(superheroId.toString()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
	
	@Test
    @Order(8)
    public void getSuperheroThatNotExistsAndReturnError() throws Exception {
        Long superheroId = 3L;
        
        mvc.perform(get("/api/superheroes/".concat(superheroId.toString()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
	
	@Test
    @Order(9)
    public void createSuperheroWithTheSameNameAndReturnError() throws Exception {
        Superhero superhero = new Superhero(null,"Superman", new String[] { "X-Ray vision" });

        mvc.perform(post("/api/superheroes")
                .content(toJson(superhero))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages[*]").value(hasItem(is("Superhero with name: Superman already exists."))));
    }
	
	@Test
    @Order(10)
    public void updateSuperheroThatNotExistsAndReturnError() throws Exception {
        Long superheroId = 3L;
        
        SuperheroUpdate superhero = new SuperheroUpdate("Spiderman", new String[] { "Spidey-Sense" });

        mvc.perform(patch("/api/superheroes/".concat(superheroId.toString()))
                .content(toJson(superhero))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
	
	@Test
    @Order(11)
    public void updateSuperheroWithTheSameNameAndReturnError() throws Exception {
        Long superheroId = 1L;
        
        SuperheroUpdate superhero = new SuperheroUpdate("Superman", new String[] { "X-Ray vision" });

        mvc.perform(patch("/api/superheroes/".concat(superheroId.toString()))
                .content(toJson(superhero))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages[*]").value(hasItem(is("Superhero with name: Superman already exists."))));
    }
	
	@Test
    @Order(12)
    public void deleteSuperheroThatNotExistsAndReturnError() throws Exception {
        Long superheroId = 3L;
        
        mvc.perform(delete("/api/superheroes/".concat(superheroId.toString()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
	
	@Test
    @Order(13)
    public void getSuperheroWithStringPathVariableAndReturnError() throws Exception {
        String superheroId = "asd";
        
        mvc.perform(delete("/api/superheroes/".concat(superheroId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
	
	@Test
    @Order(14)
    public void createSuperheroThatIsNotValidAndReturnError() throws Exception {
        String json = "{\n"
                + "    \"name\": \"\",\n"
                + "    \"superPowers\": [\n"
                + "        \"Flying\"\n"
                + "    ]\n"
                + "}";

        mvc.perform(post("/api/superheroes")
                .content(json.getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
	
	@Test
    @Order(14)
    public void updateSuperheroThatIsNotValidAndReturnError() throws Exception {
	    Long superheroId = 1L;
        String json = "{\n"
                + "    \"name\": \"\",\n"
                + "    \"superPowers\": [\n"
                + "        \"Flying\"\n"
                + "    ]\n"
                + "}";

        mvc.perform(patch("/api/superheroes/".concat(superheroId.toString()))
                .content(json.getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
	
	@Test
    @Order(15)
    public void createSuperheroAndExecutionTimeIsLogged(CapturedOutput output) throws Exception {
        Superhero superhero = new Superhero(null,"Deadpool", new String[] { "Regenerative Healing Factor" });

        mvc.perform(post("/api/superheroes")
                .content(toJson(superhero))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertTrue(output.getOut().contains("Execution time:"));
    }
	
	private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

}
