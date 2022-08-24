package uz.jl.springbootfeatures;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.Objects;

@SpringBootTest
class SpringBootFeaturesApplicationTests {

    @Test
    void requireTest() {
        Object o = null;
        //Objects.requireNonNull(o);
        //Objects.requireNonNull(o, "Object can not be null");
        //Supplier<String> supplier = () -> "Object can not be null";
        //Objects.requireNonNull(o, supplier);
        //Object o1 = Objects.requireNonNullElse(o, "Default Data String");
        //System.out.println("o1 = " + o1);
        //Supplier<String> supplier = () -> "Object can not be null";
        //Object res = Objects.requireNonNullElseGet(o, supplier);
        //System.out.println("res = " + res);
    }


    @Test
    void checkIndexTest() {
        // a , n
        //Objects.checkIndex()
        Object a = 1;
        Object b = 2;
        Objects.compare(a, b, (o1, o2) -> 0);
    }

}
