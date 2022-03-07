package test.ervinracz.accounts.accounts.application;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import test.ervinracz.accounts.accounts.domain.dtos.AccountDto;
import test.ervinracz.accounts.accounts.domain.dtos.ExchangeRatesApiResponse;
import test.ervinracz.accounts.accounts.domain.services.ExchangeRateCacheService;
import test.ervinracz.accounts.accounts.domain.services.ExchangeRateSimpleCacheService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void should_create_account() throws Exception {
    String requestJson =
        getTestJson(
            AccountDto.builder()
                .iban("NO 93 8601 1117947")
                .currency("EUR")
                .balance(BigDecimal.valueOf(100))
                .build());

    mockMvc
        .perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk());
  }

  @Test
  void should_not_create_account_because_of_invalid_currency() throws Exception {
    String requestJson =
        getTestJson(
            AccountDto.builder()
                .iban("NO 93 8601 1117947")
                .currency("EUROPE")
                .balance(BigDecimal.valueOf(100))
                .build());

    mockMvc
        .perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_not_create_account_because_of_invalid_iban() throws Exception {
    String requestJson =
        getTestJson(
            AccountDto.builder()
                .iban("N%1117947")
                .currency("EUR")
                .balance(BigDecimal.valueOf(100))
                .build());

    mockMvc
        .perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_get_all_accounts() throws Exception {
    mockMvc.perform(get("/accounts")).andExpect(status().isOk());
  }

  @Test
  void should_convert_balance_in_different_currency() throws Exception {
    String requestJson =
        getTestJson(
            AccountDto.builder()
                .iban("LU 28 001 9400644750000")
                .currency("EUR")
                .balance(BigDecimal.valueOf(100))
                .build());

    mockMvc
        .perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/accounts/LU280019400644750000/getBalanceInCurrency?currency=HUF"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().string(containsString("40010.0")));
  }

  String getTestJson(AccountDto accountDto) throws JsonProcessingException {
    return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(accountDto);
  }

  @TestConfiguration
  static class EmployeeServiceImplTestContextConfiguration {

    @Bean
    public ExchangeRateCacheService exchangeRateCacheService() {
      return new ExchangeRateSimpleCacheService(
          (accessKey, base, symbol) ->
              ResponseEntity.ok(
                  ExchangeRatesApiResponse.builder().rates(Map.of("HUF", 400.1f)).build()));
    }
  }
}
