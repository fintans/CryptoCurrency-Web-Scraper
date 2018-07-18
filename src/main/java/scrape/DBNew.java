/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// https://stackoverflow.com/questions/6514876/most-efficient-conversion-of-resultset-to-json
package scrape;

import coinmarketcapapi.Coin;
import coinmarketcapapi.GetCoinData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fintan
 */
public class DBNew {

    RedditScraper search = new RedditScraper();
    MentionsCount mentionsCounter = new MentionsCount();

    public JSONArray getCoinData(String coinName) throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:new.db");
        Statement stmt = c.createStatement();
        // ResultSet rs = stmt.executeQuery("SELECT * FROM SCRAPEINFO where NAME = " + "'" + coinName + "'" + ";");
        ResultSet rs = stmt.executeQuery("SELECT * FROM SCRAPEINFO INNER JOIN COININFO ON SCRAPEINFO.ID=COININFO.ID WHERE name = " + "'" + coinName + "'" + ";");

        String name = null;
        String symbol = null;
        int rank = 0;
        double price = 0;
        double volume = 0;
        double marketCap = 0;
        double percentChange = 0;
        int mentions = 0;
        int fear = 0;
        int sadness = 0;
        int anger = 0;
        int tentative = 0;
        int analytical = 0;
        int confident = 0;
        int joy = 0;

        JSONArray json = new JSONArray();
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            int numColumns = rsmd.getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 1; i <= numColumns; i++) {
                String column_name = rsmd.getColumnName(i);
                obj.put(column_name, rs.getObject(column_name));
            }
            json.put(obj);
        }
        return json;
    }

    //returns coin object from db
    public Coin getCoin(String coinName) throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:new.db");
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM SCRAPEINFO where NAME = " + "'" + coinName + "'" + ";");

        String name = null;
        String symbol = null;
        int rank = 0;
        double price = 0;
        double volume = 0;
        double marketCap = 0;
        double percentChange = 0;
        int mentions = 0;
        int fear = 0;
        int sadness = 0;
        int anger = 0;
        int tentative = 0;
        int analytical = 0;
        int confident = 0;
        int joy = 0;

        while (rs.next()) {
            name = rs.getString("NAME");
            symbol = rs.getString("SYMBOL");
            rank = rs.getInt("rank");
            price = rs.getDouble("price");
            volume = rs.getDouble("volume");
            marketCap = rs.getDouble("marketCap");
            percentChange = rs.getDouble("percentChange");
            mentions = rs.getInt("mentions");
            fear = rs.getInt("fear");
            sadness = rs.getInt("sadness");
            anger = rs.getInt("anger");
            tentative = rs.getInt("tentative");
            analytical = rs.getInt("analytical");
            confident = rs.getInt("confident");
            joy = rs.getInt("joy");
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
        coin.setFear(fear);
        coin.setSadness(sadness);
        coin.setAnger(anger);
        coin.setTentative(tentative);
        coin.setAnalytical(analytical);
        coin.setConfident(confident);
        coin.setJoy(joy);

        return coin;
    }

    //updates db
    public void updateTable(Coin coin) {

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            int id = coin.getId();
            String name = coin.getName();
            String symbol = coin.getSymbol();
            int rank = coin.getRank();
            double price = coin.getPrice();
            double volume = coin.getVolume();
            double marketcap = coin.getMarketCap();
            double percentChange = coin.getPercentChange();
            int mentions = coin.getMentions();
            int fear = coin.getFear();
            int sadness = coin.getSadness();
            int anger = coin.getAnger();
            int tentative = coin.getTentative();
            int analytical = coin.getAnalytical();
            int confident = coin.getConfident();
            int joy = coin.getJoy();

            stmt = c.createStatement();
            //update all coins, done by ID
            String sql = "UPDATE COININFO set NAME = '" + name + "' ," + "SYMBOL = '" + symbol
                    + "' ," + "RANK = " + rank + " ," + "PRICE = " + price + " ,"
                    + "VOLUME = " + volume + ", MARKETCAP = " + marketcap + " ,"
                    + "PERCENTCHANGE = " + percentChange + " ," + "MENTIONS = " + mentions + " ," + "FEAR = " + fear
                    + ", sadness = " + sadness + " ," + "anger = " + anger + " ," + "tentative = " + tentative
                    + ", analytical = " + analytical + " ," + "confident = " + confident + " ," + "joy = " + joy
                    + " where ID = " + id;

            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Update done successfully");
    }

    public void createCoinTable() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:new.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS COININFO "
                    + "(ID INT PRIMARY KEY     NOT NULL,"
                    + " NAME           VARCHAR(50)    NOT NULL, "
                    + " SYMBOL         VARCHAR(50)    NOT NULL) ";

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public void createScrapeTable() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:new.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS SCRAPEINFO "
                    + "(ID INT     NOT NULL,"
                    + " RANK           INT, "
                    + " PRICE          INT, "
                    + " VOLUME         INT, "
                    + " MARKETCAP      INT, "
                    + " PERCENTCHANGE  INT, "
                    + " MENTIONS       INT, "
                    + " FEAR           INT, "
                    + " SADNESS        INT, "
                    + " ANGER          INT, "
                    + " TENTATIVE      INT, "
                    + " ANALYTICAL     INT, "
                    + " CONFIDENT      INT, "
                    + " JOY            INT, "
                    + " TIMESTAMP      INT, "
                    + " FOREIGN KEY(ID) REFERENCES COININFO(ID) ) ";

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public void insertNewCoin(Coin coin) {

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:new.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            int id = coin.getId();
            String name = coin.getName();
            String symbol = coin.getSymbol();

            stmt = c.createStatement();

            String sql = "INSERT INTO COININFO (ID,NAME,SYMBOL) "
                    + "VALUES (" + id + "," + "'" + name + "', '" + symbol + "' " + ");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public void insertNewScrapeInfo(Coin coin) {

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:new.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            int id = coin.getId();
            int rank = coin.getRank();
            double price = coin.getPrice();
            double volume = coin.getVolume();
            double marketcap = coin.getMarketCap();
            double percentChange = coin.getPercentChange();
            int mentions = coin.getMentions();
            int fear = coin.getFear();
            int sadness = coin.getSadness();
            int anger = coin.getAnger();
            int tentative = coin.getTentative();
            int analytical = coin.getAnalytical();
            int confident = coin.getConfident();
            int joy = coin.getJoy();

            stmt = c.createStatement();

            String sql = "INSERT INTO SCRAPEINFO (ID,RANK,PRICE,VOLUME,MARKETCAP,PERCENTCHANGE,MENTIONS,FEAR,SADNESS,ANGER,TENTATIVE,ANALYTICAL,CONFIDENT,JOY,TIMESTAMP) "
                    + "VALUES (" + id + "," + rank + "," + price + "," + volume + "," + marketcap + "," + percentChange + "," + mentions + "," + fear + "," + sadness + "," + anger + "," + tentative + "," + analytical + "," + confident + "," + joy + "," + "(CURRENT_TIMESTAMP)" + ");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public void insertNewScrapeInfoOften(Coin coin) {

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:new.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            int id = coin.getId();
            int rank = coin.getRank();
            double price = coin.getPrice();
            double volume = coin.getVolume();
            double marketcap = coin.getMarketCap();
            double percentChange = coin.getPercentChange();
            int mentions = coin.getMentions();
            int fear = coin.getFear();
            int sadness = coin.getSadness();
            int anger = coin.getAnger();
            int tentative = coin.getTentative();
            int analytical = coin.getAnalytical();
            int confident = coin.getConfident();
            int joy = coin.getJoy();

            stmt = c.createStatement();

            String sql = "INSERT INTO SCRAPEINFO (ID,RANK,PRICE,VOLUME,MARKETCAP,PERCENTCHANGE,MENTIONS,FEAR,SADNESS,ANGER,TENTATIVE,ANALYTICAL,CONFIDENT,JOY,TIMESTAMP) "
                    + "VALUES (" + id + "," + rank + "," + price + "," + volume + "," + marketcap
                    + "," + percentChange + "," + mentions + "," + fear + "," + sadness
                    + "," + anger + "," + tentative + "," + analytical + "," + confident
                    + "," + joy + "," + "(CURRENT_TIMESTAMP)" + ");";

            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public void readNew() {

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
        DBNew test = new DBNew();

        JSONArray arr = test.getCoinData("Bitcoin");
        System.out.println(arr);
//        test.createCoinTable();
//        test.createScrapeTable();
//
//        GetCoinData coinData = new GetCoinData();
//
//        List<Coin> coinList = coinData.coinDataList();
//
//        for (Coin coin : coinList) {
//      //      test.insertNewCoin(coin);
//         //   test.insertNewScrapeInfo(coin);
//              test.insertNewScrapeInfoOften(coin);
//        }
//
////        test.read();
////        Coin coin = test.getCoin("Bitcoin");
////        System.out.println(coin.getPrice());
////        System.out.println(coin.getMentions());
    }
}
