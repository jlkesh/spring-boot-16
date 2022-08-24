package uz.jl.springbootfeatures;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.jl.springbootfeatures.models.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
class SpringBootFeaturesApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void checkForKeyExistanse() throws JsonProcessingException {
        JacksonTestController jacksonTestController = new JacksonTestController();
        List<Book> objects = jacksonTestController.getObjects();
        String data = objectMapper.writeValueAsString(objects);
        //Assertions.assertThat(data).contains("PageCount");
    }

    @Test
    void checkForKeyNonExistanse() throws JsonProcessingException {
        JacksonTestController jacksonTestController = new JacksonTestController();
        List<Book> objects = jacksonTestController.getObjects();
        String data = objectMapper.writeValueAsString(objects);
//        assertThat(data).contains("sfet243t43");
    }

    @Test
    public void whenSerializingUsingJsonAnyGetter_thenCorrect()
            throws JsonProcessingException {

        ExtendableBean bean = new ExtendableBean("My bean");
        bean.getProperties().put("attr1", "val1");
        bean.getProperties().put("attr2", "val2");
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
    }


    @Test
    public void whenSerializingUsingJsonGetter_thenCorrect()
            throws JsonProcessingException {
        MyBean bean = new MyBean(1, "My bean");
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String result = objectMapper.writeValueAsString(bean);
        System.out.println(result);
    }


    static class ExtendableBean {
        public String name;

        public ExtendableBean(String name) {
            this.name = name;
        }

        private Map<String, String> properties = new HashMap<>();

        @JsonAnyGetter
        public Map<String, String> getProperties() {
            return properties;
        }
    }


    @JsonRootName(value = "data")
    @JsonPropertyOrder({"my_name", "id"})
    @AllArgsConstructor
    public class MyBean {
        public int id;
        private String name;

        @JsonGetter("my_name")
        public String getTheName() {
            return name;
        }
    }
}
