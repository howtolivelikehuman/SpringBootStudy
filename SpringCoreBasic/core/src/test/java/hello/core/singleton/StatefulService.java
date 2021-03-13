package hello.core.singleton;

public class StatefulService {
    private int price;

    //이렇게 바뀌어야함
    public int order(String name, int price){
        System.out.println("name  = " + name + "price = " + price);
        //this.price = price; //문제
        return price;
    }

    public int getPrice(){
        return price;
    }
}
