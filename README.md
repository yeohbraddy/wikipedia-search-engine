## To Run

- Java 18
- mvn clean install && mvn compile exec:run
- xelda -s "{search term}"

## Design

```
We recommend writing a few sentences describing why you
went with the solution you chose, including design decisions,
things you tried that didn’t work, considerations you took into
account, and what the strengths and weaknesses of your
solution are.
```

### Design Choices

I used Spring because:

- I was quite familiar with the framework in terms of creating APIs and whatnot
- Spring offers really nice APIs and methods that makes the code cleaner, and development a lot easier
- Spring interacts nicely with databases and it's easier to make MVC apps

I used Maven because:

- I never touched Gradle and was quite comfortable with Maven
- I like using Maven to manage builds and dependencies

I used MongoDB because:

- The response from Wikipedia was document-like and it made sense to store it like that, and didn't want to deal with complex nested models that is associated with SQL
-

Searching for a term is based on PatriciaTries:

- I used a trie because it is very efficient for pattern / text matching, and PatriciaTrie is an improved version of tries in terms of efficiency
- Each node in the tree is a string / word in the sentence and its value is the pageId of the document
- Pattern to search for is scan via the tree and found pageIds are stored in a set to be queried against MongoDB to retrieve the whole document

I ran into blockers trying to figure out which endpoint to query from Wikipedia since there were many parameters, and each of those parameters had their own arguments. I spent some time playing in their sandbox to figure out how to generate 200 random articles in one request, that the extract / content contained the main information without noisy characters etc., and that each page had an extract for search purposes.

I also ran into blockers trying to translate the JSON response into relevant objects to store in the database

Finally, I ran into blockers trying to get the MongoDB repository implementation to work correctly as it was throwing exceptions when trying to save.

### Considerations

- I considered how many requests I was going to make when retrieving articles and ideally not bombard their endpoint
  - The endpoint I used only allows a maximum of 20 extracts per request, so given that we want 200 random articles, I had to do 200 / 20 = 10 GET requests which was significantly under their max limit of 200 requests per second [see here](https://www.mediawiki.org/wiki/Wikimedia_REST_API#Terms_and_conditions)
- I considered how much content I want to retriev from the Wikipedia articles, and decided title and extract would be sufficient based on playing around in the sandbox
- How to rank the search responses, and to implement the search feature

### Strengths and Weaknesses

#### Strengths

- Search is quite performant in terms of speed and relevancy

#### Weaknesses

- No tests
- Would be nice to include link to the article in the CLI after searching
- Since we split it via strings, searching for more than one word results in duplication of searches, e.g title = "I like food", pageId = 123 = node("I", 123), node("like", 123), node("food", 123) hence the usage of a set to prevent returning 3 of the same articles
- Uses trie instead of tf-idf, and no lemming, removing stop-words, etc.

### If More Time

- Testing
- Improve CLI (more arguments, more granular search, prettify the printing) / implement front-end
- Dockerize solution
- See if there is a way to send one request for N articles rather than N / 20 times
