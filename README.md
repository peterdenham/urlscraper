Find hashtags in web pages
-
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
