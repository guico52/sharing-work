package com.guico.sharingwork.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.guico.sharingwork.common.Result;
import com.guico.sharingwork.service.IMessageService;

import com.guico.sharingwork.util.PakoGzipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/{userId}/{gridKey}")
@Component
public class WebSocketServer {
    static Log log = LogFactory.get(WebSocketServer.class);

    private static WebSocketServer webSocketServer;

    private static int onlineCount = 0;

    private static ConcurrentHashMap<String, Map<String, WebSocketServer>> webSocketMap = new ConcurrentHashMap<>();

    private Session session;

    private String userId = "";

    private String gridKey = "";

    private IMessageService messageProcess;

    @Autowired
    public void setMessageProcess(IMessageService messageProcess) {
        this.messageProcess = messageProcess;
    }

    @PostConstruct
    public void init(){
        webSocketServer = this;
        webSocketServer.messageProcess = this.messageProcess;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId,@PathParam("gridKey") String gridKey){
        this.session = session;
        this.userId = userId;
        this.gridKey = gridKey;
        if(webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).put(gridKey,this);
        }else{
            Map<String, WebSocketServer> map = new ConcurrentHashMap<>();
            map.put(gridKey,this);
            webSocketMap.put(userId,map);
        }
        addOnlineCount();
        log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    @OnClose
    public void onClose(){
        if(webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).remove(gridKey);
            if(webSocketMap.get(userId).isEmpty()){
                webSocketMap.remove(userId);
            }
        }
        subOnlineCount();
        log.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session){
        if(StrUtil.isNotBlank(message)){
            try {
                if("rub".equals(message)) {
                    return;
                }
                String unMessage = PakoGzipUtil.unCompressURI(message);
                log.info("收到用户"+userId+"的消息:"+unMessage);
                JSONObject jsonObject = JSONUtil.parseObj(unMessage);
                if(!"mv".equals(jsonObject.getStr("t"))){
                    webSocketServer.messageProcess.process(this.gridKey, jsonObject);
                }
                Map<String, WebSocketServer> sessionMap = webSocketMap.get(userId);
                if(StrUtil.isNotBlank(unMessage)){
                    sessionMap.forEach(
                            (key, value) -> {
                                if(!key.equals(this.userId)){
                                    try{
                                        //如果是mv,代表发送者的表格位置信息
                                        if ("mv".equals(jsonObject.getStr("t"))) {
                                            value.sendMessage(JSONUtil.toJsonStr(Result.move(userId, userId, unMessage)));

                                            //如果是切换sheet，则不发送信息
                                        } else if(!"shs".equals(jsonObject.getStr("t"))) {
                                            value.sendMessage(JSONUtil.toJsonStr(Result.update(userId, userId, unMessage)));
                                        }
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void OnError(Session session, Throwable error){
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }

    private void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
