package com.coursework.kingsocks.controller;


import com.coursework.kingsocks.dto.SockRequest;
import com.coursework.kingsocks.exception.InSufficientSockQuantityException;
import com.coursework.kingsocks.exception.InvalidSockRequestException;
import com.coursework.kingsocks.model.Color;
import com.coursework.kingsocks.model.Size;
import com.coursework.kingsocks.service.SockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(
            summary = "Регистрация прихода товара на склад",
            description = "Метод позволяет зарегистрировать приход товара с указанием цвета, размера носков, " +
                    "содержания хлопка в их составе и количества пар носков"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Приход товара успешно добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            )
    })
    @PostMapping("/add")
    public void addSock(@RequestBody SockRequest sockRequest) {
        sockService.addSock(sockRequest);
    }

    @Operation(
            summary = "Регистрация отпуска товара со склада",
            description = "Метод позволяет зарегистрировать отпуск товара с указанием цвета, размера носков, " +
                    "содержания хлопка в их составе и количества пар носков"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Товар успешно отпущен со склада",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Товара нет на складе в нужном количестве, или параметры запроса имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            )
    })
    @PutMapping("/issue")
    public void issueSocks(@RequestBody SockRequest sockRequest) {
        sockService.issueSock(sockRequest);
    }

    @Operation(
            summary = "Получение информации о количестве товара на складе",
            description = "Метод позволяет узнать количество пар носков на складе, соответствующих заданным параметрам"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            )
    })
    @GetMapping("/get")
    public int getSocksCount(@RequestParam(required = false, name = "color") Color color,
                             @RequestParam(required = false, name = "size") Size size,
                             @RequestParam(required = false, name = "cottonMin") Integer cottonMin,
                             @RequestParam(required = false, name = "cottonMax") Integer cottonMax) {
        return sockService.getSockQuantity(color, size, cottonMin, cottonMax);
    }

    @Operation(
            summary = "Списание бракованного товара со склада",
            description = "Метод позволяет списать испорченные (бракованные) носки со склада"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Товар списан со склада",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла внутренняя ошибка на сервере",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SockRequest.class))
                            )
                    }
            )
    })
    @DeleteMapping("/delete")
    public void removeDefectiveSock(@RequestBody SockRequest sockRequest) {
        sockService.removeDefectiveSocks(sockRequest);
    }


}
