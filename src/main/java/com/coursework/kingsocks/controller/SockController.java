package com.coursework.kingsocks.controller;


import com.coursework.kingsocks.dto.SockRequest;
import com.coursework.kingsocks.exception.InSufficientSockQuantityException;
import com.coursework.kingsocks.exception.InvalidSockRequestException;
import com.coursework.kingsocks.model.Color;
import com.coursework.kingsocks.model.Size;
import com.coursework.kingsocks.service.SockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sock")
@Tag(name = "Носки", description = "CRUD-операции для работы с товаром на складе")
public class SockController {


    private final SockService sockService;

    public SockController(SockService sockService) {
        this.sockService = sockService;
    }

    @ExceptionHandler(InvalidSockRequestException.class)
    public ResponseEntity<String> handleInvalidException(InvalidSockRequestException invalidSockRequestException) {
        return ResponseEntity.badRequest().body(invalidSockRequestException.getMessage());
    }

    @ExceptionHandler(InSufficientSockQuantityException.class)
    public ResponseEntity<String> handleInvalidException(InSufficientSockQuantityException invalidSockRequestException) {
        return ResponseEntity.badRequest().body(invalidSockRequestException.getMessage());
    }


    @PostMapping("/add")
    public void addSock(@RequestBody SockRequest sockRequest) {
        sockService.addSock(sockRequest);                                                                     //добавить
    }

    @PutMapping("/issue")
    public void issueSocks(@RequestBody SockRequest sockRequest) {                                           //отгрузить
        sockService.issueSock(sockRequest);
    }

    @GetMapping("/get")
    public int getSocksCount(@RequestParam(required = false, name = "color") Color color,                //узнать кол-во
                             @RequestParam(required = false, name = "size") Size size,
                             @RequestParam(required = false, name = "cottonMin") Integer cottonMin,
                             @RequestParam(required = false, name = "cottonMax") Integer cottonMax) {
        return sockService.getSockQuantity(color, size, cottonMin, cottonMax);
    }

    @DeleteMapping("/delete")
    public void removeDefectiveSock(@RequestBody SockRequest sockRequest){
        sockService.removeDefectiveSocks(sockRequest);
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, web!!!!";
    }
}
