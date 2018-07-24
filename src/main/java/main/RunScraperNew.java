/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import coinmarketcapapi.Coin;
import coinmarketcapapi.GetCoinData;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import scrape.DBNew;
import scrape.DBTest;
import scrape.MentionsCount;
import scrape.NewsScraper;
import scrape.RedditScraper;
import scrape.ScrapedContent;

/**
 *
 * @author fintan
 */
public class RunScraperNew {

    public static void main(String[] args) throws IOException, ParseException, SQLException, ClassNotFoundException {
        final RedditScraper redditScraper = new RedditScraper();
        final NewsScraper newsScraper = new NewsScraper();
        final MentionsCount mentionsCount = new MentionsCount();
        final GetCoinData getCoinData = new GetCoinData();
        final DBNew db = new DBNew();

        Runnable helloRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> apiList = getCoinData.getAllCoins();
                    List<Coin> coinList = getCoinData.coinDataList(apiList);

                    List<ScrapedContent> redditContent = redditScraper.scrape();
                    List<ScrapedContent> newsContent = newsScraper.scrape();

//                    db.createCoinTable();
//                    db.createScrapeTable();
                    for (Coin coin : coinList) {

                        try {
                            mentionsCount.counter(redditContent, coin);
                            //mentionsCount.counterNews(newsContent, coin);

                            //  mentionsCount.tone(redditContent, coin);
                            // mentionsCount.tone(newsContent, coin);
                            //db.insertNewCoin(coin);
                            db.insertNewScrapeInfoOften(coin);

                        } catch (org.json.JSONException e) {
                        }

                    }
                } catch (IOException ex) {
                    Logger.getLogger(RunScraperNew.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(RunScraperNew.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 10, TimeUnit.MINUTES);
    }
}
//        List<ScrapedContent> redditContent = redditScraper.scrape();
//        List<ScrapedContent> newsContent = newsScraper.scrape();
//
//        List<Coin> coinList = getCoinData.coinDataList();
//        db.createCoinTable();
//        db.createScrapeTable();
//
////        for (Coin coin : coinList) {
////            db.insertNewCoin(coin);
////            db.insertNewScrapeInfo(coin);
////        }
//        for (Coin coin : coinList) {
//            try {
//                mentionsCount.counter(redditContent, coin);
//                mentionsCount.counter(newsContent, coin);
//
////                mentionsCount.tone(redditContent, coin);
////                mentionsCount.tone(newsContent, coin);
//                db.insertNewScrapeInfoOften(coin);
//
//            } catch (org.json.JSONException e) {
//            }
//
////            System.out.println("Name" + coin.getName());
////            System.out.println(", Mentions" + coin.getMentions());
////            System.out.println("");
////            System.out.println("Tone: " + "fear: " + coin.getFear() + " sad: " + coin.getSadness() + ". joy: " + coin.getJoy() + ". confident" + coin.getConfident() + ", analytical: " + coin.getAnalytical() + ", tentaticve: " + coin.getTentative() + ", anger" + coin.getAnger());
//        }
//        Coin coin1 = db.getCoin("Bitcoin");
//        System.out.println(coin1.getName());
//        System.out.println("mention" + coin1.getMentions());
//        System.out.println("sad " + coin1.getSadness());
//        System.out.println("fear " + coin1.getFear());
//        System.out.println("joy " + coin1.getJoy());
//        System.out.println("anger " + coin1.getAnger());
//        System.out.println("analyitcal " + coin1.getAnalytical());
//        System.out.println("tentative " + coin1.getTentative());

