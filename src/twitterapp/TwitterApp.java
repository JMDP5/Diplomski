/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterObjectFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author aleksandar
 */
public class TwitterApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("6IXbmMgwMc2kEJAtWmAN8EDtS");
        cb.setOAuthConsumerSecret("Vwq1GaRNKguOJ6IGzzOzoYJCVJRpJJ1yZrPS1Wv4GO8BJFkV6u");
        cb.setOAuthAccessToken("1905047696-00K6kaWYfxH36h8lgU3WHbaXzsmzwn6vvsBCEFS");
        cb.setOAuthAccessTokenSecret("6EKpJEj3k8VlC9yWHWfPgwSpNm25RljpDf4Lvb4vaetdo");
        File file = new File("tweets.txt");
        final BufferedWriter output = new BufferedWriter(new FileWriter(file));
        
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
        StatusListener listener = new StatusListener() {
            
            @Override
            public void onStatus(Status status) {
                try {
                    User user = status.getUser();
//                    String rawJSON = TwitterObjectFactory.getRawJSON(user);
                    if ("en-gb".equals(user.getLang()) || "en".equals(user.getLang())) {
                        output.write(status.getText());
                        output.write("\n \n");
                    }

//                    String username = status.getUser().getScreenName();
//                    output.write(username + "\n");
//                    Date dateCreated = status.getCreatedAt();
//                    String profileLocation = user.getLocation();
//                    long tweetId = status.getId();
//                    String content = status.getText();
//                    output.write(content  + "\n \n \n");
                } catch (IOException ex) {
                    Logger.getLogger(TwitterApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            @Override
            public void onDeletionNotice(StatusDeletionNotice sdn) {
                
            }
            
            @Override
            public void onTrackLimitationNotice(int i) {
                
            }
            
            @Override
            public void onScrubGeo(long l, long l1) {
                
            }
            
            @Override
            public void onStallWarning(StallWarning sw) {
                
            }
            
            @Override
            public void onException(Exception excptn) {
                
            }
        };
        
        FilterQuery fq = new FilterQuery();
        
        String keywords[] = {"Germany", "Brasil"};
        
        fq.track(keywords);
        
        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }
    
}
