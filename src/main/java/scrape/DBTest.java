/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import coinmarketcapapi.Coin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author fintan
 */
public class DBTest {

    RedditScraper search = new RedditScraper();
    MentionsCount mentionsCounter = new MentionsCount();

    //returns coin object from db
    public Coin getCoin(String coinName) throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM COINS where NAME = " + "'" + coinName + "'" + ";");

        String name = null;
        String symbol = null;
        int rank = 0;
        double price = 0;
        double volume = 0;
        double marketCap = 0;
        double percentChange = 0;
        int mentions = 0;

        while (rs.next()) {
            name = rs.getString("NAME");
            symbol = rs.getString("SYMBOL");
            rank = rs.getInt("rank");
            price = rs.getDouble("price");
            volume = rs.getDouble("volume");
            marketCap = rs.getDouble("marketCap");
            percentChange = rs.getDouble("percentChange");
            mentions = rs.getInt("mentions");
        }

        Coin coin = new Coin();
        coin.setName(name);
        coin.setSymbol(symbol);
        coin.setRank(rank);
        coin.setPrice(price);
        coin.setVolume(volume);
        coin.setMarketCap(marketCap);
        coin.setPercentChange(percentChange);
        coin.setMentions(mentions);

        return coin;
    }

    public void createTable() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS COINS "
                    + "(ID INT PRIMARY KEY     NOT NULL,"
                    + " NAME           VARCHAR(50)    NOT NULL, "
                    + " SYMBOL         VARCHAR(50)    NOT NULL, "
                    + " RANK           INT, "
                    + " PRICE          INT, "
                    + " VOLUME         INT, "
                    + " MARKETCAP      INT, "
                    + " PERCENTCHANGE  INT, "
                    + " MENTIONS       INT) ";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    //scrapes web, sets coin mentions, and inserts into db
    public void insert() {
        List<ScrapedContent> allComments = search.comments();
        List<Coin> listMentions = mentionsCounter.mentions(allComments);

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            for (Coin coin : listMentions) {
                int id = coin.getId();
                String name = coin.getName();
                String symbol = coin.getSymbol();
                int rank = coin.getRank();
                double price = coin.getPrice();
                double volume = coin.getVolume();
                double marketcap = coin.getMarketCap();
                double percentChange = coin.getPercentChange();
                int mentions = coin.getMentions();

                stmt = c.createStatement();

                String sql = "INSERT INTO COINS (ID,NAME,SYMBOL,RANK,PRICE,VOLUME,MARKETCAP,PERCENTCHANGE,MENTIONS) "
                        + "VALUES (" + id + "," + "'" + name + "', '" + symbol + "', " + rank + "," + price + "," + volume + "," + marketcap + "," + percentChange + "," + mentions + ");";
                stmt.executeUpdate(sql);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    //scrapes web, sets coin mentions, and updates db
    public void updateTable() {

        List<ScrapedContent> allComments = search.comments();
        List<Coin> listMentions = mentionsCounter.mentions(allComments);

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            for (Coin coin : listMentions) {

                int id = coin.getId();
                String name = coin.getName();
                String symbol = coin.getSymbol();
                int rank = coin.getRank();
                double price = coin.getPrice();
                double volume = coin.getVolume();
                double marketcap = coin.getMarketCap();
                double percentChange = coin.getPercentChange();
                int mentions = coin.getMentions();

                stmt = c.createStatement();
                //update all coins, done by ID
                String sql = "UPDATE COINS set NAME = '" + name + "' ," + "SYMBOL = '" + symbol + "' ," + "MENTIONS = " + mentions + " where ID = " + id;
                stmt.executeUpdate(sql);

            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Update done successfully");
    }

    public void read() {

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM COINS;");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int mentions = rs.getInt("mentions");

                System.out.println("ID = " + id);
                System.out.println("NAME = " + name);
                System.out.println("Mentions = " + mentions);
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Read done successfully");
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBTest test = new DBTest();
//        test.createTable();
//        test.updateTable();
        test.read();
        Coin coin = test.getCoin("Bitcoin");
        System.out.println(coin.getPrice());
        System.out.println(coin.getMentions());
    }
}
