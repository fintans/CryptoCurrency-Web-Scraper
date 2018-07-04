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
import scrape.DBTest;
import scrape.MentionsCount;
import scrape.NewsScraper;
import scrape.RedditScraper;
import scrape.ScrapedContent;

/**
 *
 * @author fintan
 */
public class RunScrape {

    public static void main(String[] args) throws IOException, ParseException, SQLException, ClassNotFoundException {
        RedditScraper redditScraper = new RedditScraper();
        NewsScraper newsScraper = new NewsScraper();
        MentionsCount mentionsCount = new MentionsCount();
        GetCoinData getCoinData = new GetCoinData();
        DBTest db = new DBTest();

        List<ScrapedContent> redditContent = redditScraper.scrape();
        //newsScraper.scrape();

        List<Coin> coinList = getCoinData.coinDataList();
        db.create();

        for (Coin coin : coinList) {
            try {
                mentionsCount.counter(redditContent, coin);
                mentionsCount.tone(redditContent, coin);
                //db.insertNew(coin);
                db.updateTable(coin);

            } catch (org.json.JSONException e) {
            }

//            System.out.println("Name" + coin.getName());
//            System.out.println(", Mentions" + coin.getMentions());
//            System.out.println("");
//            System.out.println("Tone: " + "fear: " + coin.getFear() + " sad: " + coin.getSadness() + ". joy: " + coin.getJoy() + ". confident" + coin.getConfident() + ", analytical: " + coin.getAnalytical() + ", tentaticve: " + coin.getTentative() + ", anger" + coin.getAnger());
        }
        Coin coin1 = db.getCoin("Bitcoin");
        System.out.println(coin1.getName());
        System.out.println("mention" + coin1.getMentions());
        System.out.println("sad " + coin1.getSadness());
        System.out.println("fear " + coin1.getFear());
        System.out.println("joy " + coin1.getJoy());
        System.out.println("anger " + coin1.getAnger());
        System.out.println("analyitcal " + coin1.getAnalytical());
        System.out.println("tentative " + coin1.getTentative());

    }

}
