///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package scrape;
//
///**
// *
// * @author fintan
// */
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package scrape;
//
//import coinmarketcapapi.Coin;
//import coinmarketcapapi.GetCoinData;
//import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
//import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
//import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.List;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
///**
// *
// * @author fintan
// */
//// https://stackoverflow.com/questions/40209550/reading-a-json-array-in-java
//public class XOldCode {
//
//    Coin coin = new Coin();
//    GetCoinData coinData = new GetCoinData();
//
//    public List<String> counter(List<ScrapedContent> comments, Coin coin) throws NullPointerException {
//        List<String> mentionComment = new ArrayList<>();
//        int count = 0;
//        for (ScrapedContent comment : comments.subList(1, comments.size() - 1)) {
//            String commentString = comment.getComment();
//
//            if (commentString == null) {
//                commentString = "will this work";
//            }
//            if (commentString.matches("(?i).*\\b" + coin.getName() + "\\b.*") || commentString.matches("(?i).*\\b" + coin.getSymbol() + "\\b.*")) {
//                count++;
//                mentionComment.add(commentString);
//            }
//        }
//        coin.setMentions(count);
//        return mentionComment;
//    }
//
//    public void counterNews(List<ScrapedContent> comments, Coin coin) throws NullPointerException {
//
//        int count = 0;
//        for (ScrapedContent comment : comments.subList(0, comments.size() - 1)) {
//            String commentString = comment.getComment();
//
//            if (commentString == null) {
//                commentString = "will this work";
//            }
//            String a[] = commentString.split(" ");
//            for (int j = 0; j < a.length; j++) {
//                if (coin.getName().equalsIgnoreCase(a[j]) || coin.getSymbol().equalsIgnoreCase(a[j])) {
//                    count++;
//                }
//            }
//        }
//
//        coin.setNewsMentions(count);
//    }
//
//    //uses ibm watson to analyse comments
//    public void tone(List<ScrapedContent> content, Coin coin) throws NullPointerException {
//        // tone variables
//        int fear = coin.getFear();
//        int sadness = coin.getSadness();
//        int anger = coin.getAnger();
//        int tentative = coin.getTentative();
//        int analytical = coin.getAnalytical();
//        int confident = coin.getConfident();
//        int joy = coin.getJoy();
//
//        ToneAnalyzer service = new ToneAnalyzer("2017-09-21");
//        service.setUsernameAndPassword("9626125d-8d1f-46d5-b0e1-8f508cff679f", "psVv2e4LU8nN");
//        // analysing low number of comments due to IBM api query limit
//        for (ScrapedContent comment : content.subList(1, 5)) {
//            String commentString = comment.getComment();
//            if (commentString == null) {
//                commentString = "will this work";
//            }
//            // if comment contains coin, then tone analyse those comments
//            if (commentString.contains(coin.getName()) || commentString.contains(coin.getSymbol())) {
//                System.out.println(commentString);
//                ToneOptions toneOptions = new ToneOptions.Builder()
//                        //analyse whole string at once, not by sentence
//                        .html(commentString).sentences(Boolean.FALSE)
//                        .build();
//                ToneAnalysis tone = service.tone(toneOptions).execute();
//                System.out.println(tone.getDocumentTone());
//                // tone analysis to JSON object
//                JSONObject obj = new JSONObject(tone.getDocumentTone());
//                JSONArray arr = obj.getJSONArray("tones");
//                JSONObject jsonObj = arr.getJSONObject(0);
//                if (jsonObj.toString().contains("sadness")) {
//                    System.out.println("sad found");
//                    sadness++;
//                } else if (jsonObj.toString().contains("fear")) {
//                    fear++;
//                } else if (jsonObj.toString().contains("sadness")) {
//                    sadness++;
//                } else if (jsonObj.toString().contains("anger")) {
//                    anger++;
//                } else if (jsonObj.toString().contains("tentative")) {
//                    tentative++;
//                } else if (jsonObj.toString().contains("analytical")) {
//                    analytical++;
//                } else if (jsonObj.toString().contains("confident")) {
//                    confident++;
//                } else if (jsonObj.toString().contains("joy")) {
//                    joy++;
//                } else {
//                    System.out.println("no sad");
//                }
//            }
//        }
//        //System.out.println("fear" + fear + "sad " + sadness + "ander" + anger + "tentative" + tentative + "joy" + joy + "confident" + confident);
//        //sets coins tone variables
//        coin.setFear(fear);
//        coin.setSadness(sadness);
//        coin.setAnger(anger);
//        coin.setTentative(tentative);
//        coin.setAnalytical(analytical);
//        coin.setConfident(confident);
//        coin.setJoy(joy);
//
//    }
//
//    public static void main(String[] args) throws IOException, ParseException {
//        Coin coin = new Coin("Bitcoin", "BTC");
//        GetCoinData coinData = new GetCoinData();
//        MentionsCount mCount = new MentionsCount();
//        NewsScraper news = new NewsScraper();
//
//        RedditScraper search = new RedditScraper();
//
//        List<String> apiList = coinData.getAllCustomers();
//        List<Coin> list = coinData.coinDataList(apiList);
//
//        //List<ScrapedContent> newsComments = news.scrape();
//        List<ScrapedContent> allComments = search.scrape();
//
//        List<String> commentTest = mCount.counter(allComments, coin);
//
//        mCount.tone(allComments, coin);
//        for (String string : commentTest) {
//            System.out.println("HERE IS COMMENR" + string);
//        }
//
//        System.out.println("Mentions: " + coin.getMentions());
//
//        System.out.println("NewsMentions: " + coin.getNewsMentions());
//        System.out.println("Tone: " + "fear: " + coin.getFear() + " sad: " + coin.getSadness() + ". joy: " + coin.getJoy() + ". confident" + coin.getConfident() + ", analytical: " + coin.getAnalytical() + ", anger" + coin.getAnger());
//    }
//}



