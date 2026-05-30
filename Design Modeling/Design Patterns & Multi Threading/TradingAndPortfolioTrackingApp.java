import java.util.*;

class PortfolioManager {
    private static PortfolioManager obj;
    List<String> portfolios = new ArrayList<>();
    private PortfolioManager() {}

    public static PortfolioManager getInstance() {
        if (obj == null) obj = new PortfolioManager();
        return obj;
    }
}

class Portfolio {
    String name, type;
    double amount;

    Portfolio(String name, String type, double amount) {
        this.name = name;
        this.type = type;
        this.amount = amount;
    }

    public String toString() {
        return name+" "+type+" "+amount;
    }
}

interface Asset {
    void price();
}

class Stock implements Asset {
    public void price() {
        System.out.println("Stock Price: 100");
    }
}

class Crypto implements Asset {
    public void price() {
        System.out.println("Crypto Price: 500");
    }
}

class AssetFactory {
    public static Asset getAsset(String type) {
        if (type.equals("stock")) return new Stock();
        return new Crypto();
    }
}

interface Observer {
    void update(String msg);
}

class User implements Observer {
    String name;
    User(String name) {
        this.name = name;
    }
    public void update(String msg) {
        System.out.println(name + " got alert: " + msg);
    }
}

class Trade implements Runnable {
    String trade;
    Trade(String trade) {
        this.trade = trade;
    }
    public void run() {
        System.out.println("Processing: " + trade);
    }
}

public class TradingAndPortfolioTrackingApp {
    public static void main(String[] args) {
        PortfolioManager pm = PortfolioManager.getInstance();
        pm.portfolios.add("MyPortfolio");
        System.out.println("Portfolios: " + pm.portfolios);
        Portfolio p1 = new Portfolio("Fund1", "Stock", 10000);
        System.out.println(p1);
        Asset a = AssetFactory.getAsset("stock");
        a.price();
        User u1 = new User("Pabitra");
        u1.update("Bitcoin is high");
        Thread t1 = new Thread(new Trade("BUY Stock"));
        Thread t2 = new Thread(new Trade("SELL Crypto"));
        t1.start();
        t2.start();
        System.out.println("Main Thread Running");
    }
}