package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public NetworkClient(){
        System.out.println("생성자 호출, url = " + url);

    }

    //서비스 시작시 호출
    public void connect(){
        System.out.println("connect : " + url);
    }

    public void call(String msg){
        System.out.println("call : " + url + " message : " + msg);
    }

    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("close");
    }

    @PreDestroy
    public void close(){
        System.out.println("NetworkClient close");
        disconnect();
    }

    @PostConstruct
    public void init(){
        System.out.println("NetworkClient init");
        connect();
        call("초기화 연결 메시지");
    }
}
