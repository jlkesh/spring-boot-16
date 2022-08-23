package uz.jl.springbootfeatures;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SpringBootApplication
public class SpringBootFeaturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }


}

@Controller
class FileUploadController {


    @GetMapping("/upload")
    public String getFileUploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestPart(name = "file") MultipartFile file, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "file uploaded");
        System.out.println(file.getName());
        return "redirect:/upload";
    }
//    @GetMapping("/download")
}

@ControllerAdvice
class GlobalExceptionHandler  {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMultipartException(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/upload";
    }

}