/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jerseytutorial;

import coinmarketcapapi.Coin;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import scrape.DBTest;


@Path("/coin")
public class Tester {

    DBTest db = new DBTest();

    @GET
    @Path("/{param}")
    public Response sayHelloWorld(@PathParam("param") String message) {

        String output = "Hello " + message + "!";

        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/name/{param}")
    public Response getMentions(@PathParam("param") String name) throws SQLException, ClassNotFoundException {
        Coin coin = db.getCoin(name);
        int mentions = coin.getMentions();
        String output = "Mentions for : " + name + " " + mentions + "";

        return Response.status(200).entity(output).build();
    }

    
    public Coin getCoin(String name) throws SQLException, ClassNotFoundException{
        Coin coin = db.getCoin(name);
        return coin;
    }
    
    public int percentMentions(String name) throws SQLException, ClassNotFoundException{
        Coin coin = db.getCoin(name);
        int mentions = coin.getMentions();
        double marketCap = coin.getMarketCap();
        int change = (int) (marketCap / mentions);
        
        return change;
        
    }
    
    

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Tester re = new Tester();

        Coin coin = re.getCoin("Bitcoin");
        System.out.println(coin.getName());
        System.out.println(coin.getPrice());
        System.out.println(coin.getMentions());
        System.out.println(re.percentMentions("Bitcoin"));

    }

}
