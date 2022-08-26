package uz.jl.springbootfeatures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class SpringBootFeaturesApplication implements CommandLineRunner {
    final CarMapper mapper;

    public SpringBootFeaturesApplication(CarMapper mapper) {
        this.mapper = mapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        CarDTO dto = mapper.toDTO(new Car("123", "Lambo"));
        Car car = mapper.fromDTO(dto);
        System.out.println("dto = " + dto);
        System.out.println("car = " + car);

    }
}


@Mapper(componentModel = "spring",imports = {UUID.class})
interface CarMapper {

    @Mapping(target = "id", source = "carId")
    @Mapping(target = "make", source = "carManu")
    Car fromDTO(CarDTO dto);

    @InheritInverseConfiguration
//    @Mapping(source = "id", target = "carId")
//    @Mapping(source = "make", target = "carManu")
//    @Mapping(target = "requestId", expression = "java(UUID.randomUUID().toString())")
//    @Mapping(target = "idPlusManu", expression = "java(addFields(car.getId(),car.getMake()))")
    CarDTO toDTO(Car car);

    default String addFields(String id, String name) {
        return id + name;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Car {
    private String id;
    private String make;
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class CarDTO {
    private String carId;
    private String carManu;
    private String requestId;
    private String idPlusManu;
}
