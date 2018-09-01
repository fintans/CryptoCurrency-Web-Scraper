/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// https://stackoverflow.com/questions/51441099/group-timestamp-by-hour-sqlite
package com.mycompany.jerseytutorial;

import coinmarketcapapi.Coin;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import scrape.DBAgain;
import scrape.DBNew;
import scrape.DBTest;

@Path("/coin")
public class Resources {

    DBTest db = new DBTest();
    DBNew dbNew = new DBNew();
    DBAgain dbAgain = new DBAgain();

    @GET
    @Path("/{param}")
    public Response sayHelloWorld(@PathParam("param") String message) {

        String output = "Hello " + message + "!";

        return Response.status(200).entity(output).build();
    }

//            Person person = new Person();
//        person.setName( "Person Name" );
//        person.setAge( 666 );
//
//        JSONObject jsonObj = new JSONObject( person );
//        System.out.println( jsonObj );
    @GET
    @Path("/name/j/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response jsonObJ(@PathParam("param") String name) throws SQLException, ClassNotFoundException {
        Coin coin = db.getCoin(name.substring(0, 1).toUpperCase() + name.substring(1));

        JSONObject jsonObj = new JSONObject(coin);

        return Response.status(200).entity(jsonObj.toString()).header("Access-Control-Allow-Origin", "*").build();
        //return Response.ok(output).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/name/k/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dbData(@PathParam("param") String name) throws SQLException, ClassNotFoundException {
        JSONArray arr = dbAgain.getCoinData(name);

        return Response.status(200).entity(arr.toString()).header("Access-Control-Allow-Origin", "*").build();
        //return Response.ok(output).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/name/k/{param}/hour")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dateHour(@PathParam("param") String name) throws SQLException, ClassNotFoundException {
        JSONArray arr = dbAgain.get24Hr(name);

        return Response.status(200).entity(arr.toString()).header("Access-Control-Allow-Origin", "*").build();
        //return Response.ok(output).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/name/{param}/daily")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dateDaily(@PathParam("param") String name) throws SQLException, ClassNotFoundException {
        JSONArray arr = dbAgain.getDaily(name);

        return Response.status(200).entity(arr.toString()).header("Access-Control-Allow-Origin", "*").build();
        //return Response.ok(output).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/name/again/{param}/tone")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dateHourAgain(@PathParam("param") String name) throws SQLException, ClassNotFoundException {
        JSONArray arr = dbAgain.getCoinTone(name.substring(0, 1).toUpperCase() + name.substring(1));

        return Response.status(200).entity(arr.toString()).header("Access-Control-Allow-Origin", "*").build();
        //return Response.ok(output).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/name/again/avgweekly")
    @Produces(MediaType.APPLICATION_JSON)
    public Response AvgMentions1Week(@PathParam("param") String name) throws SQLException, ClassNotFoundException {
        JSONArray arr = dbAgain.getAvgMentions1Week();

        return Response.status(200).entity(arr.toString()).header("Access-Control-Allow-Origin", "*").build();
        //return Response.ok(output).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/name/again/coins50")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Coins50Under1(@PathParam("param") String name) throws SQLException, ClassNotFoundException {
        JSONArray arr = dbAgain.get50CoinsUnder1();

        return Response.status(200).entity(arr.toString()).header("Access-Control-Allow-Origin", "*").build();
        //return Response.ok(output).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/maxmentions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response maxMentionsAll(@PathParam("param") String name) throws SQLException, ClassNotFoundException {
        JSONArray arr = dbAgain.getMaxMentionsAll();

        return Response.status(200).entity(arr.toString()).header("Access-Control-Allow-Origin", "*").build();
        //return Response.ok(output).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/name/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMentions(@PathParam("param") String name) throws SQLException, ClassNotFoundException {
        Coin coin = db.getCoin(name);
        int mentions = coin.getMentions();
        int sadness = coin.getSadness();

        String hi = "Mentions for : " + name + " " + mentions + "";
        JSONObject output = new JSONObject();
        output.put("mentions", mentions);
        output.put("sadness", sadness);

        return Response.status(200).entity(output.toString()).header("Access-Control-Allow-Origin", "*").build();
        //return Response.ok(output).header("Access-Control-Allow-Origin", "*").build();
    }

    public Coin getCoin(String name) throws SQLException, ClassNotFoundException {
        Coin coin = db.getCoin(name);
        return coin;
    }

    public int percentMentions(String name) throws SQLException, ClassNotFoundException {
        Coin coin = db.getCoin(name);
        int mentions = coin.getMentions();
        double marketCap = coin.getMarketCap();
        int change = (int) (marketCap / mentions);

        return change;

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Resources re = new Resources();

        Coin coin = re.getCoin("Bitcoin");
        System.out.println(coin.getName());
        System.out.println(coin.getPrice());
        System.out.println(coin.getMentions());
        System.out.println(re.percentMentions("Bitcoin"));

    }

}
