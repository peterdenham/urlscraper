package cc.denham.urlscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Thread to consume URL Queue
 * Parses URL
 * Parses web page
 * Finds regex matches
 * Writes matches to file
 */
public class URLConsumer implements Runnable {
    private final String poisonPill;
    private BlockingQueue<String> queue;
    private String regex;
    private String outputFilePath;

    URLConsumer(BlockingQueue<String> queue, String poisonPill, String regex, String outputFilePath) {
        this.queue = queue;
        this.poisonPill = poisonPill;
        this.regex = regex;
        this.outputFilePath = outputFilePath;
    }

    public void run() {
        Pattern pattern = Pattern.compile(regex);
        while (true) {
            try {
                String url = queue.take();
                if (url.equals(poisonPill)) { // exit
                    return;
                }

                System.out.println(Thread.currentThread().getName() + " Processing: " + url);

                // connect to URL
                Document doc = null;
                doc = Jsoup.connect(url).get();

                // look for regex matches
                List<String> allMatches = new ArrayList<String>();
                Matcher m = pattern
                        .matcher(doc.body().text());
                while (m.find()) {
                    allMatches.add(m.group());
                }

                // write hashtags to file
                URL urly = new URL(url);
                Files.write(Paths.get(outputFilePath + FileSystems.getDefault().getSeparator() + urly.getHost() + ".txt"), allMatches);
                System.out.println("Wrote file:" + urly.getHost() + ".txt");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

