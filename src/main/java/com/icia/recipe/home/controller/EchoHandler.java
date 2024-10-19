package com.icia.recipe.home.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.recipe.home.dto.AlertMessage;
import com.icia.recipe.home.dto.TradeDto;
import com.icia.recipe.home.service.TradeService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class EchoHandler extends TextWebSocketHandler {
    @Autowired
    TradeService tSer;

    private List<WebSocketSession> sessions = new ArrayList<>();
    private Map<String, WebSocketSession> userSessionMap = new HashMap<>();
    private Map<String, List<String>> notificationBuffer = new HashMap<>();
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Socket 연결 성공");
        log.info("session{}", session);
        sessions.add(session);
        log.info("sessions{}", sessions);
        log.info(">>>>" + sendPushUsername(session)); //현재 접속한 사람의 username이 출력됨
        String senderId = sendPushUsername(session);
        userSessionMap.put(senderId, session);
        log.info("userSessionMap {}", userSessionMap);

        if (notificationBuffer.containsKey(senderId)) {
            List<String> bufferedMessages = notificationBuffer.get(senderId);
            for (String msg : bufferedMessages) {
                session.sendMessage(new TextMessage(msg));
            }
            notificationBuffer.remove(senderId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("session = " + sendPushUsername(session));
        String msg = message.getPayload(); //js에서 넘어온 메세지
        log.info("msg = " + msg);
        AlertMessage alertMessage = objectMapper.readValue(msg, AlertMessage.class);
        WebSocketSession sendedPushSession = userSessionMap.get(alertMessage.getM_id());//로그인 상태일때 알람 보냄
        LocalDateTime now = LocalDateTime.now();
        // 초를 2자리로 포맷하기 위한 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 포맷팅된 문자열 생성
        String formattedDate = now.format(formatter);
        // 초를 2자리로 잘라내기 (소수점 이하를 제외)
        String result = formattedDate.substring(0, 19); // "yyyy-MM-dd HH:mm:ss" 형식의 첫 19글자까지 추출
        if (alertMessage.getType().equals(AlertMessage.MessageType.SEND)) {
            log.info("JSON이라서 등장");
            log.info(alertMessage.getTradesend());
            alertMessage.setMessage("<div class='me-3' id='notification_"+alertMessage.getT_num()+"_"+alertMessage.getT_item()+"_"+alertMessage.getT_itemcount()+"'>" +
                    "<i class='fas fa-file-alt text-white'></i>" +
                    "<div id='socketAlertDiv'>" +
                    "<span id='current-time' class='small text-gray-500'>"
                    + result +
                    "</span>&nbsp;" +
                    "<button id='accept' onclick='accept("+alertMessage.getT_num()+",\""+alertMessage.getT_item()+"\","+alertMessage.getT_itemcount()+
                    ", \"" + alertMessage.getTradesend() + "\", \""+alertMessage.getM_id()+"\")'>수락</button>&nbsp;" +
                    "<button id='refuse' onclick='refuse(" + alertMessage.getT_num()+",\""+alertMessage.getT_item()+"\","+alertMessage.getT_itemcount()+", \"" + alertMessage.getTradesend() + "\", \""+alertMessage.getM_id()+"\")'>거절</button>" +
                    "<a class='dropdown-item d-flex align-items-center' href='#'>" +
                    "<div id='socketAlert' class='alert alert-warning' role='alert'>" +
                    alertMessage.getTradesend() + "님이 "+alertMessage.getT_item()+" "+alertMessage.getT_itemcount()+alertMessage.getT_unit()+" 과 "+alertMessage.getT_change()+"를 교환신청 하였습니다." +
                    "</div>" +
                    "</a>" +
                    "</div>" +
                    "</div>");
            tSer.alertSave(alertMessage);
            if (sendedPushSession != null) {
                sendedPushSession.sendMessage(new TextMessage(alertMessage.getMessage()));
            } else {
                notificationBuffer.computeIfAbsent(alertMessage.getM_id(), k -> new ArrayList<>()).add(alertMessage.getMessage());
            }
        }else if(alertMessage.getType().equals(AlertMessage.MessageType.refuse)){
            log.info("거절 socket 입장");
            alertMessage.setMessage("<div class='me-3' id='notification'>" +
                    "<i class='fas fa-file-alt text-white'></i>" +
                    "</div>" +
                    "<div id='socketAlertDiv'>" +
                    "<span id='current-time' class='small text-gray-500'>"
                    + result +
                    "</span>" +
                    "<a class='dropdown-item d-flex align-items-center' href='#'>" +
                    "<div id='socketAlert' class='alert alert-warning' role='alert'>" +
                    alertMessage.getTradesend()+"님이 교환 신청을 거절하였습니다." +
                    "</div>" +
                    "</a>" +
                    "</div>");
            if (sendedPushSession != null) {
                sendedPushSession.sendMessage(new TextMessage(alertMessage.getMessage()));
            } else {
                notificationBuffer.computeIfAbsent(alertMessage.getM_id(), k -> new ArrayList<>()).add(alertMessage.getMessage());
            }
        }else if(alertMessage.getType().equals(AlertMessage.MessageType.accept)){
            log.info("수락 socket 입장");
            alertMessage.setMessage("<div class='me-3' id='notification'>" +
                    "<i class='fas fa-file-alt text-white'></i>" +
                    "</div>" +
                    "<div id='socketAlertDiv'>" +
                    "<span id='current-time' class='small text-gray-500'>"
                    + result +
                    "</span>" +
                    "<a class='dropdown-item d-flex align-items-center' href='#'>" +
                    "<div id='socketAlert' class='alert alert-warning' role='alert'>" +
                    alertMessage.getTradesend()+"님이 교환 신청을 수락하였습니다." +
                    "</div>" +
                    "</a>" +
                    "</div>");
            if (sendedPushSession != null) {
                sendedPushSession.sendMessage(new TextMessage(alertMessage.getMessage()));
            } else {
                notificationBuffer.computeIfAbsent(alertMessage.getM_id(), k -> new ArrayList<>()).add(alertMessage.getMessage());
            }
        }else if(alertMessage.getType().equals(AlertMessage.MessageType.EMPTY)){
            log.info("재고0개 socket 입장");
            log.info(alertMessage.getF_title());
            alertMessage.setMessage("<div class='me-3' id='notification'>" +
                    "<i class='fas fa-file-alt text-white'></i>" +
                    "</div>" +
                    "<div id='socketAlertDiv'>" +
                    "<span id='current-time' class='small text-gray-500'>"
                    + result +
                    "</span>" +
                    "<a class='dropdown-item d-flex align-items-center' href='#'>" +
                    "<div id='socketAlert' class='alert alert-warning' role='alert'>" +
                    alertMessage.getF_title()+" 품절" +
                    "</div>" +
                    "<button class='btn btn-outline-primary' onclick='inventoryGo(\""+alertMessage.getF_title()+"\")'>주문</button>" +
                    "<button class='btn btn-outline-danger'>닫기</button>" +
                    "</a>" +
                    "</div>");
//            tSer.managementAlert();
            if (sendedPushSession != null) {
                sendedPushSession.sendMessage(new TextMessage(alertMessage.getMessage()));
            } else {
                notificationBuffer.computeIfAbsent(alertMessage.getM_id(), k -> new ArrayList<>()).add(alertMessage.getMessage());
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Socket 연결 해제");
        sessions.remove(session);
        String userId = sendPushUsername(session);
        userSessionMap.remove(userId, session);
    }

    //알람을 보내는 유저
    private String sendPushUsername(WebSocketSession session) {
        String loginUsername;
        if (session.getPrincipal() == null) {
            loginUsername = null;
        } else {
            loginUsername = session.getPrincipal().getName();
        }
        return loginUsername;
    }
}
