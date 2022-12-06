package ch.heig.amtteam10.labeldetector.controller;

import ch.heig.amtteam10.labeldetector.core.AWSClient;
import ch.heig.amtteam10.labeldetector.core.Label;
import ch.heig.amtteam10.labeldetector.core.exceptions.FailDownloadFileException;
import ch.heig.amtteam10.labeldetector.dao.ProcessDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LabelDetectorController {
    @PostMapping("/process")
    public @ResponseBody ResponseEntity<Label[]> processImage(@RequestBody ProcessDAO tmp) {
        try {
            var labels = AWSClient.getInstance().labelDetector().execute(tmp.imageUrl(), tmp.maxLabels(), tmp.minConfidence());
            return ResponseEntity.ok(labels);
        } catch (FailDownloadFileException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
