import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import org.jibble.jmegahal.*;

import java.io.*;
import java.util.List;

/**
 * Created by mhallot on 8/25/16.
 */
public class PostTextMaker {

    JMegaHal hal = new JMegaHal();
    Twitter twitter = TwitterFactory.getSingleton();

    String makeMarkov() throws IOException{

        try {
            getTimeline(); //get first 10 posts in the twitter feed.
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        hal.addDocument("file:///home/meyerhallot/IdeaProjects/memebot9000/Sheck.txt");
        hal.addDocument("file:///home/meyerhallot/IdeaProjects/memebot9000/feed.txt");
        hal.addDocument("file:///home/meyerhallot/IdeaProjects/memebot9000/samuraiCop.txt");
        String sentence = hal.getSentence();

        if (sentence.length()>=140) { //makes sure the sentance is bellow twitter's 140 characters
            sentence= sentence.substring(0,140);
            System.out.println(sentence);
        }
        return sentence;
    }

    void getTimeline() throws TwitterException, FileNotFoundException {
        // The factory instance is re-useable and thread safe.
        PrintWriter feedDoc = new PrintWriter(new FileOutputStream(new File("/home/meyerhallot/IdeaProjects/memebot9000/feed.txt"),true));
        List<Status> statuses = twitter.getHomeTimeline();
        System.out.println("Showing home timeline.");
        for (Status status : statuses) {
            System.out.println(status.getText());
            hal.add(status.getText());
            feedDoc.append(status.getText());
        }
    }

    void creatPost(){
        Status status = null;
        try {
            status = twitter.updateStatus(makeMarkov());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }


}
