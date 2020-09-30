package jp.ac.jc_21.stupidunion.equipmenegercontroller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import jp.ac.jc_21.stupidunion.equipmeneger.Equipmeneger;
import jp.ac.jc_21.stupidunion.equipmeneger.bean.EquipmentBean;
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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		CreateForm.from(page); // if not exists, throws ElementNotFoundException.
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
		final var bean = dataOfSubmittedOnCreateFormExistsInRepository(inputMap);
		final var outputMap = beanToMap(bean);
		dataOfStoredToRepositoryExistsInListView(outputMap);
	}
	public EquipmentBean dataOfSubmittedOnCreateFormExistsInRepository(Map<String, String> inputMap) throws Exception {
		HtmlPage page = getHtmlPage(urlRoot);
		var form = CreateForm.from(page);
		form.setType(inputMap.get("type"));
		form.setModel(inputMap.get("model"));
		form.setManufacturer(inputMap.get("manufacturer"));
		form.setSpec(inputMap.get("spec"));
		form.setPurchaceDate(inputMap.get("purchaceDate"));
		form.setLifespanInYears(inputMap.get("lifespanInYears"));
		form.submit()
				.orElseThrow(()-> new IllegalStateException("click submit button failure"));
		return repository.findAll().stream().findFirst().orElseThrow(()-> new IllegalStateException("posted data does not exist in repository"));
	}
	public void dataOfStoredToRepositoryExistsInListView(Map<String, String> inputMap) throws Exception {
		HtmlPage page = getHtmlPage(urlRoot);
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
	private Map<String, String> beanToMap(EquipmentBean bean) {
		final var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		var map = new java.util.HashMap<>(Map.of(
				"id", bean.getId() + "",
				"type", bean.getType(),
				"model", bean.getModel(),
				"manufacturer", bean.getManufacturer(),
				"spec", bean.getSpec(),
				"purchaceDate", dateFormat.format(bean.getPurchaceDate()),
				"lifespanInYears", bean.getLifespanInYears() + "",
				"depreciated", bean.isDepreciated() + "",
				"unusable", bean.isUnusable() + "",
				"lendable", bean.isLendable() + ""
		));
		map.put("installationLocation", bean.getInstallationLocation());
		final var expiryDate = bean.getExpiryDate();
		if (expiryDate == null)
			map.put("expiryDate", "");
		else
			map.put("expiryDate", dateFormat.format(bean.getExpiryDate()));
		return map;
	}
	
	private HtmlPage getHtmlPage(String requestPath) throws Exception {
		MvcResult mvcResult = mockMvc.perform(get(requestPath))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andReturn();
		String requestedUrl = mvcResult.getRequest().getRequestURL().toString();
		return webClient.getPage(requestedUrl);
	}
}
