package com.coursework.kingsocks.service;

import com.coursework.kingsocks.dto.SockRequest;
import com.coursework.kingsocks.exception.InSufficientSockQuantityException;
import com.coursework.kingsocks.exception.InvalidSockRequestException;
import com.coursework.kingsocks.model.Sock;
import com.coursework.kingsocks.model.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SockService {

    private final Map<Sock, Integer> socksMap = new HashMap<>();

    public void addSock(SockRequest sockRequest) {
        validateRequest(sockRequest);
        Sock sock = mapToSock(sockRequest);
        if (socksMap.containsKey(sock)) {
            socksMap.put(sock, socksMap.get(sock) + sockRequest.getQuantity());
        } else {
            socksMap.put(sock, sockRequest.getQuantity());
        }

    }

    public void issueSock(SockRequest sockRequest) {
        decreaseSockQuantity(sockRequest);
    }
    public void removeDefectiveSocks(SockRequest sockRequest) {
        decreaseSockQuantity(sockRequest);
    }

    private  void decreaseSockQuantity(SockRequest sockRequest){
        validateRequest(sockRequest);
        Sock sock = mapToSock(sockRequest);
        int sockQuantity = socksMap.getOrDefault(sock, 0);
        if (sockQuantity >= sockRequest.getQuantity()) {
            socksMap.put(sock, sockQuantity - sockRequest.getQuantity());
        } else {
            throw new InSufficientSockQuantityException("Параметры запроса отсутствуют или имеют некорректный формат");
        }
    }

    public int getSockQuantity(Color color, Size size, Integer cottonMin, Integer cottonMax) {

        int total = 0;
        for (Map.Entry<Sock, Integer> entry : socksMap.entrySet()) {
            if (color != null && !entry.getKey().getColor().equals(color)) {
                continue;
            }
            if (size != null && !entry.getKey().getSize().equals(size)) {
                continue;
            }
            if (cottonMin != null && entry.getKey().getCottonPart() < cottonMin) {
                continue;
            }
            if (cottonMax != null && entry.getKey().getCottonPart() > cottonMax) {
                continue;
            }
            total += entry.getValue();
        }
        return total;
    }

    private void validateRequest(SockRequest sockRequest) {
        if (sockRequest.getColor() == null || sockRequest.getSize() == null) {
            throw new InvalidSockRequestException("Все поля должны быть заполнены");
        }
        if (sockRequest.getCottonPart() < 0 || sockRequest.getCottonPart() > 100) {
            throw new InvalidSockRequestException("Процент хлопка должен быть от 0 до 100");
        }
        if (sockRequest.getQuantity() <= 0) {
            throw new InvalidSockRequestException("Списание должно быть только положительным");
        }
    }


    private Sock mapToSock(SockRequest sockRequest) {
        return new Sock(
                sockRequest.getColor(),
                sockRequest.getSize(),
                sockRequest.getCottonPart());
    }


}
