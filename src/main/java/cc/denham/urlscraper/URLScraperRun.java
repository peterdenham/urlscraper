package cc.denham.urlscraper;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Main Class to run URL Scraper
 * Usage:
 * URLScraperRun /path/to/SiteList.txt
 */
public class URLScraperRun {

    public static void main(String[] args) {
        int BOUND = 1000;
        int N_PRODUCERS = 1;
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(BOUND);
        String poisonPill = "-1";
        String regex = "#\\w+";
        String outputFilePath = System.getProperty("user.home");
        int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;

        if (args.length != 1) {
            System.out.println("Pass url file path as single arg");
            System.exit(0);
        }

        File f = new File(args[0]);
        if (!f.exists()) {
            System.out.println("File not found:" + args[0]);
            System.exit(0);
        }

        for (int i = 0; i < N_PRODUCERS; i++) {
            new Thread(new URLProducer(queue, f.getAbsolutePath(), poisonPill, poisonPillPerProducer)).start();
        }

        for (int j = 0; j < N_CONSUMERS; j++) {
            new Thread(new URLConsumer(queue, poisonPill, regex, outputFilePath)).start();
        }
    }
}

