Find hashtags in web pages
-
Brief

Write a program that will parse the main page of each URL looking
for certain patterns. We would like to find all references to a hash tag (e.g. #javascript)
For each website, an output file should be generated showing the list of strings that match
the pattern. For example, if you chose to find all hash tags, a text file should be generated
for each web site that will list all the hashtag strings found in the site.


Files
- URLScraperRun - main program
- URLProducer - parses URL file
- URLConsumer - parses web pages, writes output to files
- SiteList.txt test file for parsing

Dependencies
- jsoup

Producer/Consumer threads
- Producer pulls all valid URL's from url file, adds to queue
- Consumer takes a url, parse web page for hashtags, write hashtags to file

Settings
- URLScraperRun:regex - currently matches hashtags
- URLScraperRun:outputFilePath - output dir for files to be written to

Improvements:
- System.out used for logging, should use a logger library
- For readability, output text file names use url HOST. These may not be unique however.
- Malformed URL's are silently ignored, could write to an error.log