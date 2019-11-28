package by.attrade.controller.rest;

import by.attrade.config.PictureMediaConfig;
import by.attrade.domain.dto.PictureMediaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pictureMedia")
public class PictureMediaRestController {
    @Autowired
    private PictureMediaConfig pictureMediaConfig;

    @GetMapping
    public List<PictureMediaDTO> list() {
        return pictureMediaConfig.getPictureMedias();
    }
}
