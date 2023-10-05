package com.example.lotto.analyzer.FetchDataServiceTest;

import com.example.lotto.analyzer.Entity.*;;
import com.example.lotto.analyzer.externalApi.Base.DrawResultGameExtendedRepository;
import com.example.lotto.analyzer.externalApi.FetchDataService;
import com.example.lotto.analyzer.utils.UrlWithParametersCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
public class FetchDataServiceTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FetchDataService fetchDataService;
    @SpyBean
    private DrawResultGameExtendedRepository drawResultGameExtendedRepository;
    @MockBean
    private Clock clock;

    private final String pathToResponseData = "src/test/java/com/example/lotto/analyzer/data/apiResponse/";
    private final String pathToRepositoryData = "src/test/java/com/example/lotto/analyzer/data/repositoryData/";

    @BeforeEach
    void setUp() {
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        when(clock.instant()).thenReturn(ZonedDateTime.of(
                2023,
                10,
                31,
                23,
                30,
                0,
                0,
                ZoneId.systemDefault()
        ).toInstant());
    }

    @ParameterizedTest
    @MethodSource("fetchAllGames")
    @Transactional
    void shouldFetchAllGamesByType(GameType gameType, String mockResponsePath, String expectedResultPath) throws IOException, JSONException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //given
        String mockResponse = new String(Files.readAllBytes(Paths.get(pathToResponseData + mockResponsePath)));
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(requestTo(UrlWithParametersCreator.getApiUrlWithParameters(gameType)))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));

        //when
        fetchDataService.fetchAllGamesByType(gameType);
        String actualResult = objectMapper.writeValueAsString(drawResultGameExtendedRepository.findAll(gameType));
        String expectedResult = new String(Files.readAllBytes(Paths.get(pathToRepositoryData + expectedResultPath)));
        //then
        assertEquals(10, drawResultGameExtendedRepository.findAll(gameType).size());
        JSONAssert.assertEquals(expectedResult, actualResult, false);
        mockServer.verify();
    }

    private static Stream<Arguments> fetchAllGames() {
        return Stream.of(
                Arguments.of(GameType.Lotto, "draw_data_all_lotto_games_response.json", "after_fetch_find_all_lotto.json"),
                Arguments.of(GameType.LottoPlus, "draw_data_all_lotto_plus_games_response.json", "after_fetch_find_all_lotto_plus.json"),
                Arguments.of(GameType.EuroJackpot, "draw_data_all_euro_jackpot_games_response.json", "after_fetch_find_all_euro_jackpot.json"),
                Arguments.of(GameType.EkstraPensja, "draw_data_all_ekstra_pensja_games_response.json", "after_fetch_find_all_ekstra_pensja.json"),
                Arguments.of(GameType.EkstraPremia, "draw_data_all_ekstra_premia_games_response.json", "after_fetch_find_all_ekstra_premia.json")
        );
    }

    @ParameterizedTest
    @MethodSource("fetchMissingGames")
    @Transactional
    void shouldFetchAllGamesByTypeWhenLatestDrawDateIsNull(GameType gameType, String mockResponsePath, int expectedSize) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //given
        String mockResponse = new String(Files.readAllBytes(Paths.get(pathToResponseData + mockResponsePath)));
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(requestTo(UrlWithParametersCreator.getApiUrlWithParameters(gameType, expectedSize)))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));
        when(drawResultGameExtendedRepository.findLatestDrawDate(gameType))
                .thenReturn(Optional.of(
                        OffsetDateTime.of(
                                2023, Month.OCTOBER.getValue(), 1, 23, 23, 0, 0, ZoneId.systemDefault().getRules().getOffset(Instant.now())
                        ))
                );
        //when
        fetchDataService.fetchMissingGamesByType(gameType);
        //then
        assertEquals(10, drawResultGameExtendedRepository.findAll(gameType).size());
    }

    private static Stream<Arguments> fetchMissingGames() {
        return Stream.of(
                Arguments.of(GameType.Lotto, "draw_data_all_lotto_games_response.json", 13),
                Arguments.of(GameType.LottoPlus, "draw_data_all_lotto_plus_games_response.json", 13),
                Arguments.of(GameType.EuroJackpot, "draw_data_all_euro_jackpot_games_response.json", 9),
                Arguments.of(GameType.EkstraPremia, "draw_data_all_ekstra_premia_games_response.json", 30),
                Arguments.of(GameType.EkstraPensja, "draw_data_all_ekstra_pensja_games_response.json", 30)
        );
    }
}
