package com.youjiniot.controller;



import com.youjiniot.common.task.WelcomeTask;
import com.youjiniot.domain.Message;
import com.youjiniot.domain.Response;
import com.youjiniot.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebSocketController {
	@Autowired
	SimpMessagingTemplate template;
	
	@Autowired
	WelcomeTask welcomeTask;

	@MessageMapping("/welcome") // 浏览器发送请求通过@messageMapping 映射/welcome 这个地址。
	@SendTo("/topic/getResponse") // 服务器端有消息时,会订阅@SendTo 中的路径的浏览器发送消息。
	public Response say(Message message) throws Exception {
		Thread.sleep(1000);
		return new Response("Welcome, " + message.getName() + "!");
	}


	@RequestMapping("/welcome")
	@ResponseBody
	public R say02() {
		try {
			welcomeTask.sayWelcome();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return R.ok();
	}
}