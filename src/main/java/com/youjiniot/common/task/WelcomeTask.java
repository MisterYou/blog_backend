package com.youjiniot.common.task;

import com.youjiniot.domain.Response;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WelcomeTask implements Job {
    @Autowired
    SimpMessagingTemplate template;

    public void sayWelcome() throws Exception {
        template.convertAndSend("/topic/getResponse", new Response("Welcome,websocket!"));
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
