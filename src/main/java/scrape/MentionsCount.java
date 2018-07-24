/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import coinmarketcapapi.Coin;
import coinmarketcapapi.GetCoinData;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author fintan
 */
// https://stackoverflow.com/questions/40209550/reading-a-json-array-in-java
public class MentionsCount {

    Coin coin = new Coin();
    GetCoinData coinData = new GetCoinData();

    //searches each ScrapedContent.getComment() for matches 
    public List<String> counter(List<ScrapedContent> comments, Coin coin) throws NullPointerException {
        //array list to store comments that contain coin mentions, for tone analysis
        List<String> mentionComment = new ArrayList<>();
        int count = 0;
        for (ScrapedContent comment : comments.subList(1, comments.size() - 1)) {
            String commentString = comment.getComment();

            if (commentString == null) {
                commentString = "will this work";
            }
            if (commentString.matches("(?i).*\\b" + coin.getName() + "\\b.*") || commentString.matches("(?i).*\\b" + coin.getSymbol() + "\\b.*")) {
                count++;
                mentionComment.add(commentString);
            }
        } //sets mentions
        coin.setMentions(count);
        return mentionComment;
    }

    public List<String> counterNews(List<ScrapedContent> comments, Coin coin) {
        List<String> mentionArticle = new ArrayList<>();
        int count = 0;
        try {
            for (ScrapedContent comment : comments.subList(0, comments.size() - 1)) {
                String commentString = comment.getComment();

                if (commentString == null) {
                    commentString = "will this work";
                }
                String a[] = commentString.split(" ");
                for (int j = 0; j < a.length; j++) {
                    if (coin.getName().equalsIgnoreCase(a[j]) || coin.getSymbol().equalsIgnoreCase(a[j])) {
                        count++;
                        mentionArticle.add(commentString);
                        break;
                    }
                }
            }
        } catch (NullPointerException e) {
        }

        coin.setNewsMentions(count);
        return mentionArticle;
    }

    //uses ibm watson to analyse comments
    public void tone(List<String> mentionComment, Coin coin) throws NullPointerException {
        // tone variables
        int fear = coin.getFear();
        int sadness = coin.getSadness();
        int anger = coin.getAnger();
        int tentative = coin.getTentative();
        int analytical = coin.getAnalytical();
        int confident = coin.getConfident();
        int joy = coin.getJoy();

        ToneAnalyzer service = new ToneAnalyzer("2017-09-21");
        service.setUsernameAndPassword("9626125d-8d1f-46d5-b0e1-8f508cff679f", "psVv2e4LU8nN");
        // analysing low number of comments due to IBM api query limit
        for (String string : mentionComment) {

            System.out.println(string);
            try {

                ToneOptions toneOptions = new ToneOptions.Builder()
                        //analyse whole string at once, not by sentence
                        .html(string).sentences(Boolean.FALSE)
                        .build();
                ToneAnalysis tone = service.tone(toneOptions).execute();
                System.out.println(tone.getDocumentTone());
                // tone analysis to JSON object
                JSONObject obj = new JSONObject(tone.getDocumentTone());
                JSONArray arr = obj.getJSONArray("tones");
                JSONObject jsonObj = arr.getJSONObject(0);
                if (jsonObj.toString().contains("sadness")) {
                    sadness++;
                } else if (jsonObj.toString().contains("fear")) {
                    fear++;
                } else if (jsonObj.toString().contains("sadness")) {
                    sadness++;
                } else if (jsonObj.toString().contains("anger")) {
                    anger++;
                } else if (jsonObj.toString().contains("tentative")) {
                    tentative++;
                } else if (jsonObj.toString().contains("analytical")) {
                    analytical++;
                } else if (jsonObj.toString().contains("confident")) {
                    confident++;
                } else if (jsonObj.toString().contains("joy")) {
                    joy++;
                }
            } catch (JSONException je) {
            }
        }
        //System.out.println("fear" + fear + "sad " + sadness + "ander" + anger + "tentative" + tentative + "joy" + joy + "confident" + confident);
        //sets coins tone variables
        coin.setFear(fear);
        coin.setSadness(sadness);
        coin.setAnger(anger);
        coin.setTentative(tentative);
        coin.setAnalytical(analytical);
        coin.setConfident(confident);
        coin.setJoy(joy);
    }

    public static void main(String[] args) throws IOException, ParseException {
        Coin coin = new Coin("Bitcoin", "BTC");
        GetCoinData coinData = new GetCoinData();
        MentionsCount mCount = new MentionsCount();
        NewsScraper news = new NewsScraper();

        RedditScraper search = new RedditScraper();

        List<String> apiList = coinData.getAllCoins();
        List<Coin> list = coinData.coinDataList(apiList);

        List<ScrapedContent> newsComments = news.scrape();
        //List<ScrapedContent> allComments = search.scrape();

        List<String> commentTest = mCount.counterNews(newsComments, coin);

        System.out.println("Mentions: " + coin.getMentions());

        System.out.println("NewsMentions: " + coin.getNewsMentions());
        System.out.println("Tone: " + "fear: " + coin.getFear() + " sad: " + coin.getSadness() + ". joy: " + coin.getJoy() + ". confident" + coin.getConfident() + ", analytical: " + coin.getAnalytical() + ", anger" + coin.getAnger());
    }
}
