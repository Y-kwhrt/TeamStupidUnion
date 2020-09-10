package jp.ac.jc_21.stupidunion.equipmenegercontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import jp.ac.jc_21.stupidunion.equipmeneger.Equipmeneger;

@ContextConfiguration(classes = Equipmeneger.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EquipmentControllerTest {
	static final String urlRoot = "/equipment";
	@Autowired
	MockMvc mockMvc;
	@Autowired
	WebApplicationContext wac;
	WebClient webClient;

	@BeforeAll
	public void テスト前処理() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/templates");
		viewResolver.setSuffix(".html");
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@BeforeEach
	void 前処理() {
		webClient = MockMvcWebClientBuilder.webAppContextSetup(wac).build();
	}

	@Test
	public void createFormExists() throws Exception {
		MvcResult result = mockMvc.perform(get(urlRoot))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)).andReturn();

		String url = result.getRequest().getRequestURL().toString();
		HtmlPage page = webClient.getPage(url);

		Map<String, DomElement> inputForms =
				Arrays
				.asList("type", "model", "manufacturer", "spec", "purchaceDate", "lifespanInYears")
				.stream()
				.collect(Collectors.toMap(it -> it, it -> page.getElementById(it)));
		
		assertThat(inputForms).doesNotContainValue(null);
	}
}