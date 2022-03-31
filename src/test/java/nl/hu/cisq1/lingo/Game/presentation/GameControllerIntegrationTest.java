package nl.hu.cisq1.lingo.Game.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.cisq1.lingo.Game.application.GameService;
import nl.hu.cisq1.lingo.Game.data.GameRepository;
import nl.hu.cisq1.lingo.Game.presentation.dto.GuessDto;
import nl.hu.cisq1.lingo.Words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerIntegrationTest {

    @Autowired
    private GameRepository gameRepository;

    @MockBean
    private WordService wordService;

    @Autowired
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Starting game gives back correct response.")
    void testStartGame() throws Exception {
        when(wordService.provideRandomWord(5))
                .thenReturn("groep");

        RequestBuilder request =  MockMvcRequestBuilders
                .post("/games");

        String expectedHint = "g....";

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score", is(0)))
                .andExpect(jsonPath("$.gState", is("PLAYING")))
                .andExpect(jsonPath("$.lengthWord", is(5)))
                .andExpect(jsonPath("$.feedbackHistory", hasSize(0)))
                .andExpect(jsonPath("$.currentHint.hint", is(expectedHint)));
    }

    @Test
    @DisplayName("Guessing correctly test.")
    void testGuessCorrect() throws Exception {
        when(wordService.provideRandomWord(5))
                .thenReturn("groep");

        long gameId = gameService.startNewGame().gameId;

        GuessDto guess = new GuessDto();
        guess.guess = "groep";
        String guessBody = new ObjectMapper().writeValueAsString(guess);

        RequestBuilder guessRequest = MockMvcRequestBuilders
                .patch("/games/" + gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(guessBody);

        mockMvc.perform(guessRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedbackHistory", hasSize(1)));
    }

}