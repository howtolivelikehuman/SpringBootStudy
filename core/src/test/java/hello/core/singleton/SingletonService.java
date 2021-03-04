package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    //외부에서 new로 만들지 못하게 막기
    private SingletonService(){
    }

    public static SingletonService getInstance(){
        return instance;
    }

}
