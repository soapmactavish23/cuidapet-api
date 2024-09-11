package com.hkprogrammer.api.core.facades;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class PushNotificationFacade {

    public void sendMessage(List<String> devices, String title, String body, Map<String, Object> payload) {
        for (String device : devices) {
            if (device != null) {
                // Criação da mensagem com todos os campos
                Message.Builder messageBuilder = Message.builder()
                        .setToken(device)
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build())
                        .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")  // Adiciona ação de clique
                        .putData("id", "1")  // Exemplo de ID estático
                        .putData("status", "done")  // Exemplo de status
                        .putData("payload", payload.toString());  // Adiciona o payload como string

                Message message = messageBuilder.build();

                try {
                    // Envia a mensagem via Firebase Messaging
                    String response = FirebaseMessaging.getInstance().sendAsync(message).get();
                    log.info("Successfully sent message: " + response);
                } catch (InterruptedException | ExecutionException e) {
                    log.error("Error sending message", e);
                }
            }
        }
    }
}
