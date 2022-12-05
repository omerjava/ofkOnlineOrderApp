package com.ofkMasay.Controller;

import com.mysql.cj.x.protobuf.MysqlxCursor;
import com.ofkMasay.Entity.MenuItem;
import com.ofkMasay.Entity.OpenHours;
import com.ofkMasay.Service.OpenHoursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class OpenHoursController {

    private final OpenHoursService openHoursService;

    public OpenHoursController(OpenHoursService openHoursService) {
        this.openHoursService = openHoursService;
    }

    @PostMapping("/openHour")
    public ResponseEntity<Void> createOpenHours(@RequestBody OpenHours openHours){
        if (openHoursService.createOpenHours(openHours)) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/openHours")
    public ResponseEntity<List<OpenHours>> getAllOpenHours(){
        return new ResponseEntity<>(openHoursService.getAllOpenHours(), HttpStatus.OK);
    }

    @GetMapping("/openHour/{id}")
    public ResponseEntity<OpenHours> getOpenHoursById(@PathVariable Long id){
        return new ResponseEntity<>(openHoursService.getOpenHoursById(id), HttpStatus.OK);
    }

    @PutMapping("/openHour/{id}")
    public ResponseEntity<Void> updateOpenHours(@PathVariable Long id, @RequestBody OpenHours openHours){
        if(openHoursService.updateOpenHours(id, openHours)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/openHour/{id}")
    public ResponseEntity<Void> deleteOpenHours(@PathVariable Long id){
        if(openHoursService.deleteOpenHours(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}