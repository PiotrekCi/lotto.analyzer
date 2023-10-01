// ToDo fix it after generyfying repo
//package com.example.lotto.analyzer.DataServiceTest;
//
//
//import com.example.lotto.analyzer.externalApi.FetchDataService;
//import com.example.lotto.analyzer.Entity.GameType;
//import com.example.lotto.analyzer.externalApi.Repository.DrawResultGameRepository;
//import com.example.lotto.analyzer.externalApi.Repository.LottoDrawResultRepository;
//import com.example.lotto.analyzer.utils.UrlWithParametersCreator;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.json.JSONException;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
//
//@SpringBootTest
//public class DataServiceTest {
//    @Autowired
//    private RestTemplate restTemplate;
//    @Autowired
//    private FetchDataService dataService;
//
//    @Mock
//    private DrawResultGameRepository resultGameRepository;
//
//    @Autowired
//    private LottoDrawResultRepository lottoDrawResultRepository;
//
//    private final String pathToResponseData = "src/test/java/com/example/lotto/analyzer/data/apiResponse/";
//    private final String pathToRepositoryData = "src/test/java/com/example/lotto/analyzer/data/repositoryData/";
//
//    @Test
//    @Transactional
//    void shouldFetchAllGamesByType() throws IOException, JSONException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.findAndRegisterModules();
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        //given
//        String mockResponse = new String(Files.readAllBytes(Paths.get(pathToResponseData + "draw_data_all_lotto_games_response.json")));
//        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
//        mockServer.expect(requestTo(UrlWithParametersCreator.getApiUrlWithParameters(GameType.Lotto)))
//                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));
//
//        //when
//        dataService.fetchAllGamesByType(GameType.Lotto);
//        String actualResult = objectMapper.writeValueAsString(lottoDrawResultRepository.findAll());
//        String expectedResult = new String(Files.readAllBytes(Paths.get(pathToRepositoryData + "after_fetch_find_all_lotto.json")));
//        //then
//        assertEquals(10, lottoDrawResultRepository.findAll().size());
//        JSONAssert.assertEquals(expectedResult, actualResult, false);
//        mockServer.verify();
//    }
//}
