package kg.arbocdi.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/")
public class BuilderController {
    @Autowired
    private BuilderService builderService;

    @PostMapping(path = "init")
    public List createMaterials() throws ExecutionException, InterruptedException {
        return builderService.init();
    }

    @PostMapping(path = "build")
    public List build() throws ExecutionException, InterruptedException {
        return builderService.build();
    }
}
