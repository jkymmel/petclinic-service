package io.github.jkymmel.idu0075.petclinic.server.visit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/visit")
public class VisitController {

    @Resource
    private VisitService visitService;


    @GetMapping
    private ResponseEntity<List<Visit>> findAll() {
        return new ResponseEntity<>(visitService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/:uuid")
    private ResponseEntity<Visit> findByUuid(@PathVariable String uuid) {
        return visitService.findByUuid(uuid)
                .map(visit -> new ResponseEntity<>(visit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    private ResponseEntity<Visit> save(@RequestBody Visit visit) {
        return new ResponseEntity<>(visitService.save(visit), HttpStatus.OK);
    }
}