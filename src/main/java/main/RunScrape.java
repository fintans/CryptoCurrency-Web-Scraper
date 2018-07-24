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
import scrape.DBAgain;
import scrape.DBTest;
import scrape.MentionsCount;
import scrape.NewsScraper;
import scrape.RedditScraper;
import scrape.ScrapedContent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fintan
 */
public class RunScrape {

    public static void main(String[] args) throws IOException, ParseException, SQLException, ClassNotFoundException {
        final RedditScraper redditScraper = new RedditScraper();
        final NewsScraper newsScraper = new NewsScraper();
        final MentionsCount mentionsCount = new MentionsCount();
        final GetCoinData getCoinData = new GetCoinData();
        final DBAgain db = new DBAgain();

        Runnable helloRunnable = new Runnable() {
            @Override
            public void run() {
                try {

                    List<ScrapedContent> redditContent = redditScraper.scrape();
                    List<ScrapedContent> newsContent = newsScraper.scrape();

                    List<String> apiList = getCoinData.getAllCoins();
                    List<Coin> coinList = getCoinData.coinDataList(apiList);

//                    db.createCoinTable();
//                    db.createScrapeTable();
                    for (Coin coin : coinList) {
                        try {
                            List<String> commentMentions = mentionsCount.counter(redditContent, coin);
                            List<String> newsMentions = mentionsCount.counterNews(newsContent, coin);

//                            mentionsCount.tone(commentMentions, coin);
//                            mentionsCount.tone(newsMentions, coin);
//                            db.insertNewCoin(coin);
                            db.insertNewScrapeInfo(coin);
//                            db.insertNewToneInfo(coin);
//                             db.updateTone(coin);

                        } catch (org.json.JSONException e) {
                        }
                    }

                } catch (IOException ex) {
                    Logger.getLogger(RunScraperNew.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(RunScraperNew.class.getName()).log(Level.SEVERE, null, ex);
                }

//        Coin coin1 = db.getCoin("Bitcoin");
//        System.out.println(coin1.getName());
//        System.out.println("mention" + coin1.getMentions());
//        System.out.println("sad " + coin1.getSadness());
//        System.out.println("fear " + coin1.getFear());
//        System.out.println("joy " + coin1.getJoy());
//        System.out.println("anger " + coin1.getAnger());
//        System.out.println("analyitcal " + coin1.getAnalytical());
//        System.out.println("tentative " + coin1.getTentative());
            }

        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 30, TimeUnit.MINUTES);

//        [19 ->] GET https://oauth.reddit.com/comments/90pcjd?sort=confidence&sr_detail=false&raw_json=1
//[<- 19] 401 application/json: '{"message": "Unauthorized", "error": 401}'
    }
}
