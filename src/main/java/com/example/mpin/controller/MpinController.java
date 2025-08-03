package com.example.mpin.controller;

import com.example.mpin.model.MpinEntry;
import com.example.mpin.services.MpinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/mpin")
// Allow Angular to access backend
@RequiredArgsConstructor
public class










































































































































































































































































































































































































































































































































































































































































































MpinController {

    private final MpinService service;

    @GetMapping("/loadAll")
    public ResponseEntity<List<MpinEntry>> loadAllEntries() {
        return ResponseEntity.ok(service.getAllEntries());
    }

    @GetMapping("/search/{serviceId}")
    public ResponseEntity<Map<String, Object>> searchByServiceId(@PathVariable String serviceId) {
        Map<String, Object> response = new HashMap<>();

        try {
            return service.getEntryByServiceId(serviceId)
                    .map(entry -> {
                        response.put("message", "Record found.");
                        response.put("data", entry); // Jackson will convert object to JSON
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        response.put("message", "No record found with Service ID: " + serviceId);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    });
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error retrieving record with Service ID: " + serviceId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }




    @DeleteMapping("/delete/{serviceId}")
    public ResponseEntity<Map<String, String>> deleteByServiceId(@PathVariable String serviceId) {
        Map<String, String> response = new HashMap<>();

        try {
            boolean deleted = service.deleteByServiceId(serviceId);
            if (deleted) {
                response.put("message", "Record with Service ID " + serviceId + " deleted successfully.");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "No record found with Service ID: " + serviceId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error deleting record with Service ID: " + serviceId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Map<String, String>> deleteById(@PathVariable("id") int id)
//    { Map<String, String> response = new HashMap<>();
//        try
//        { boolean deleted = service.deleteMapping(id);
//        if (deleted)
//        { response.put("message", "Record with ID " + id + " deleted successfully."); return ResponseEntity.ok(response);
//        } else
//        { response.put("message", "No record found with ID " + id);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); } }
//        catch (Exception e)
//        {
//            e.printStackTrace(); response.put("message", "Error deleting record with ID " + id); return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//            }}


    @GetMapping("/ping")    
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("pong");
    }
}
