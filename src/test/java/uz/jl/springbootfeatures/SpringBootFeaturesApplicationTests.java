package uz.jl.springbootfeatures;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.jl.springbootfeatures.utils.BaseUtils;

@SpringBootTest
class SpringBootFeaturesApplicationTests {

    @Autowired
    private BaseUtils baseUtils;

    @Test

    void testMd5() {
//        String pText = "Hello MD5";
//        System.out.println(String.format(baseUtils.OUTPUT_FORMAT, "Input (string)", pText));
//        System.out.println(String.format(baseUtils.OUTPUT_FORMAT, "Input (length)", pText.length()));
//
//        byte[] md5InBytes = baseUtils.digest(pText.getBytes(baseUtils.UTF_8));
//        System.out.println(String.format(baseUtils.OUTPUT_FORMAT, "MD5 (hex) ", baseUtils.bytesToHex(md5InBytes)));
//
//        System.out.println(String.format(baseUtils.OUTPUT_FORMAT, "MD5 (length)", md5InBytes.length));
    }

}
