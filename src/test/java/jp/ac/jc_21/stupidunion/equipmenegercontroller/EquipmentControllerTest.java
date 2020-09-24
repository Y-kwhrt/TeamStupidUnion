package jp.ac.jc_21.stupidunion.equipmenegercontroller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import jp.ac.jc_21.stupidunion.equipmeneger.Equipmeneger;
import jp.ac.jc_21.stupidunion.equipmeneger.formdata.EquipmentFormData;
import jp.ac.jc_21.stupidunion.equipmeneger.repository.IEquipmentRepository;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = Equipmeneger.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EquipmentControllerTest {
	static final String tableName = "equipment";
	static final String urlRoot = "/"+ tableName;
	static final String urlCreate = urlRoot + "/create";
	@Autowired
	MockMvc mockMvc;
	@Autowired
	WebApplicationContext wac;
	WebClient webClient;

	@Autowired
	IEquipmentRepository repository;

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
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andReturn();

		String url = result.getRequest().getRequestURL().toString();
		HtmlPage page = webClient.getPage(url);

		HtmlForm createForm = getCreateForm(page);

		HtmlSubmitInput submitButton = getSubmitButton(createForm);
		assertThat(submitButton).isNotNull();

		Map<String, List<HtmlElement>> inputForms =
				Stream.of("type", "model", "manufacturer", "spec", "purchaceDate", "lifespanInYears")
				.collect(Collectors.toMap(
						it -> it,
						it -> createForm.getElementsByAttribute("INPUT", "name", it)
				));
		assertThat(inputForms)
				.allSatisfy((key, value) ->
					assertThat(value).hasSize(1)
				);
	}

	@Test
	public void listIsEmpty() throws Exception {
		MvcResult result = mockMvc.perform(get(urlRoot))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andReturn();
		List<EquipmentFormData> list = (List<EquipmentFormData>) result.getModelAndView().getModel().get("equipments");

		assertThat(list).isEmpty();
	}

	@Test
	public void submitAndView() throws Exception {
		Map<String, String> inputMap = Map.of(
				"type", "Type",
				"model", "Model",
				"manufacturer", "Manufacturer",
				"spec", "Spec",
				"purchaceDate", "2020-10-10",
				"lifespanInYears", "1"
		);

		postCreateForm(inputMap);
		dataOfStoredToRepositoryExistsInListView(inputMap);
	}
	public void postCreateForm(Map<String, String> inputMap) throws Exception {
		MvcResult result = mockMvc.perform(get(urlRoot))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andReturn();
		String url = result.getRequest().getRequestURL().toString();
		HtmlPage page = webClient.getPage(url);
		HtmlForm form = getCreateForm(page);

		HtmlSubmitInput submitButton = (HtmlSubmitInput) form.getElementsByAttribute("INPUT", "type", "submit")
				.stream()
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("create form submit button not found"));

		inputMap.forEach((key, value) -> form.getInputByName(key).setValueAttribute(value));
		submitButton.click();
	}
	public void dataOfStoredToRepositoryExistsInListView(Map<String, String> inputMap) throws Exception {
		MvcResult result = mockMvc.perform(get(urlRoot))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andReturn();
		String url = result.getRequest().getRequestURL().toString();
		HtmlPage page = webClient.getPage(url);
		List<HtmlSpan> listContents =
				page.getElementsByTagName("SPAN")
						.stream()
						.map(span -> (HtmlSpan) span)
						.filter(span -> span.hasAttribute("class"))
						.collect(Collectors.toList());

		Map<String, List<HtmlSpan>> keyAndContentsMap =
			inputMap.keySet()
					.stream()
					.collect(Collectors.toMap(
							key -> key,
							key -> listContents.stream()
									.filter(span -> span.getAttribute("class").contains(tableName +"-"+ key))
									.collect(Collectors.toList())
					));
		assertThat(keyAndContentsMap)
				.allSatisfy((key, contents) ->{
					assertThat(contents).hasSize(1);
					var content = contents.get(0);
					assertThat(content.getTextContent()).isEqualTo(inputMap.get(key));
				});
	}

	private HtmlForm getCreateForm(HtmlPage page) throws Exception {
		return (HtmlForm) page.getElementsByTagName("FORM")
				.stream()
				.filter(element -> element.getAttribute("action").equals(urlCreate))
				.findFirst().orElseThrow(() -> new IllegalStateException("create form not found"));
	}
	private HtmlSubmitInput getSubmitButton(HtmlForm form) throws Exception {
		return (HtmlSubmitInput) form.getElementsByAttribute("INPUT", "type", "submit")
				.stream()
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("create form submit button not found"));
	}
}
