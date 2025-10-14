package edu.citadel.main.steps;

import edu.citadel.main.SpringCucumberConfig;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ApiSteps extends SpringCucumberConfig {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    private MvcResult lastResult;
    private String lastBody;
    private final Map<String, String> saved = new HashMap<>();

    @Before
    public void setupMvc() {
        if (mvc == null) {
            mvc = MockMvcBuilders.webAppContextSetup(context).build();
        }
        lastResult = null;
        lastBody = null;
    }

    @Given("the API is up")
    public void the_api_is_up() {
        // No-op: context boots via @SpringBootTest
    }

    @When("I GET {string}")
    public void i_get(String path) throws Exception {
        lastResult = mvc.perform(get(resolvePath(path))).andReturn();
        lastBody = lastResult.getResponse().getContentAsString();
    }

    @When("I POST {string} with JSON:")
    public void i_post_with_json(String path, String docString) throws Exception {
        lastResult = mvc.perform(
                        post(resolvePath(path))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(docString))
                .andReturn();
        lastBody = lastResult.getResponse().getContentAsString();
    }

    @When("I GET {string} using path vars:")
    public void i_get_with_path_vars(String path, io.cucumber.datatable.DataTable table) throws Exception {
        String resolved = applyPathVars(path, table);
        lastResult = mvc.perform(get(resolvePath(resolved))).andReturn();
        lastBody = lastResult.getResponse().getContentAsString();
    }

    @Then("the response status is {int}")
    public void the_response_status_is(Integer code) throws Exception {
        Assertions.assertThat(lastResult).isNotNull();
        lastResult.getResponse().setCharacterEncoding("UTF-8");
        Assertions.assertThat(lastResult.getResponse().getStatus()).isEqualTo(code);
    }

    @Then("the response is JSON")
    public void the_response_is_json() {
        String ct = lastResult.getResponse().getContentType();
        Assertions.assertThat(ct).contains("application/json");
    }

    @Then("the JSON array has at least {int} elements")
    public void json_array_has_at_least(int min) {
        List<?> list = JsonPath.read(lastBody, "$");
        Assertions.assertThat(list.size()).isGreaterThanOrEqualTo(min);
    }

    @Then("the JSON at {string} equals {string}")
    public void json_at_equals(String jsonPath, String expected) {
        Object value = JsonPath.read(lastBody, jsonPath);
        Assertions.assertThat(Objects.toString(value, null)).isEqualTo(expected);
    }

    @Then("I save the JSON at {string} as {string}")
    public void save_json_value_as(String jsonPath, String key) {
        Object value = JsonPath.read(lastBody, jsonPath);
        saved.put(key, Objects.toString(value, null));
    }

    @Given("I have a saved value {string}")
    public void i_have_saved_value(String key) {
        Assertions.assertThat(saved).containsKey(key);
    }

    // ---------- helpers ----------

    private String resolvePath(String path) {
        // Replace {{var}} in path using saved values.
        Pattern p = Pattern.compile("\\{\\{([^}]+)}}");
        Matcher m = p.matcher(path);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String key = m.group(1);
            String val = saved.getOrDefault(key, "");
            m.appendReplacement(sb, Matcher.quoteReplacement(val));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String applyPathVars(String path, io.cucumber.datatable.DataTable table) {
        Map<String, String> map = table.asMaps().get(0);
        for (Map.Entry<String, String> e : map.entrySet()) {
            String val = e.getValue();
            if (val.startsWith("{{") && val.endsWith("}}")) {
                val = saved.getOrDefault(val.substring(2, val.length() - 2), "");
            }
            path = path.replace("{" + e.getKey() + "}", val);
        }
        return path;
    }
}
