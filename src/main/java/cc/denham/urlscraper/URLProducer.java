package cc.denham.urlscraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

/**
 * Parses site list file and adds URL's to a Queue
 */
public class URLProducer implements Runnable {
    private final String poisonPill;
    private final int poisonPillPerProducer;
    private final String fileName;
    private BlockingQueue<String> numbersQueue;


    URLProducer(BlockingQueue<String> numbersQueue, String fileName, String poisonPill, int poisonPillPerProducer) {
        this.numbersQueue = numbersQueue;
        this.poisonPill = poisonPill;
        this.poisonPillPerProducer = poisonPillPerProducer;
        this.fileName = fileName;
    }

    public void run() {
        try {
            findUrls();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findUrls() throws InterruptedException, IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(e -> {
                try {
                    new URL(e);
                    numbersQueue.put(e);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (MalformedURLException ex) {
                    System.out.println("Skipping:" + e);
                }
            });
        }

        for (int j = 0; j < poisonPillPerProducer; j++) {
            numbersQueue.put(poisonPill);
        }
    }
}
