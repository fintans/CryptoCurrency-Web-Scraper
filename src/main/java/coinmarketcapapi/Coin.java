/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coinmarketcapapi;

/**
 *
 * @author fintan
 */

//used to created 100 coin objects from coinmarketcap API
public class Coin {
    
    private int id;
    private String name;
    private String symbol;
    private int rank;
    private double price;
    private double volume;
    private double marketCap;
    private double percentChange; //24hr
    private int mentions;
    private int newsMentions;

    public int getNewsMentions() {
        return newsMentions;
    }

    public void setNewsMentions(int newsMentions) {
        this.newsMentions = newsMentions;
    }

    public Coin(int mentions) {
        this.mentions = mentions;
    }

    public int getMentions() {
        return mentions;
    }

    public void setMentions(int mentions) {
        this.mentions = mentions;
    }

    public Coin() {
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(double percentChange) {
        this.percentChange = percentChange;
    }
    
    

    public void setAllCoins(int id, String name, String symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public Coin(int id, String name, String symbol, int rank, double price, double volume, double marketCap, double percentChange) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.rank = rank;
        this.price = price;
        this.volume = volume;
        this.marketCap = marketCap;
        this.percentChange = percentChange;
    }


    
    

    public Coin(String name) {
        this.name = name;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    
    
    
    
}
