## To Run

- Java 18
- mvn clean install && mvn compile exec:run
- xelda -s {search term}

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

- The response from Wikipedia was document-like and it made sense to store it like that
- (_While writing this README, I realised we were not supposed to use full-text search, sorry!_) Having used ElasticSearch, I remembered something along the liens of searching through large texts using NoSQL and I remember vaguely Spring offered methods to do so. Therefore it would speed up and make implementation a lot easier

The search is based on score that is handled by MongoDB in terms of relevancy of the inputted search term, e.g "Easter egg" > "\*easter\*" or "\*egg\*" where the title holds the highest weight for the search (generally if search term exists in the title, then more than likely the article is about that), followed by the extract

I ran into blockers trying to figure out which endpoint to query from Wikipedia since there were many parameters, and each of those parameters had their own arguments. I spent some time playing in their sandbox to figure out how to generate 200 random articles in one request, that the extract / content contained the main information without noisy characters etc., and that each page had an extract for search purposes.

I also ran into blockers trying to translate the JSON response into relevant objects to store in the database with annotations that I can add to improve search, indexing, and scoring.

Finally, I ran into blockers trying to get the MongoDB repository implementation to work correctly as it was throwing exceptions when trying to save.

### Considerations

- I considered how many requests I was going to make when retrieving articles and ideally not bombard their endpoint
  - The endpoint I used only allows a maximum of 20 extracts per request, so given that we want 200 random articles, I had to do 200 / 20 = 10 GET requests which was significantly under their max limit of 200 requests per second [see here](https://www.mediawiki.org/wiki/Wikimedia_REST_API#Terms_and_conditions)
- I considered how much content I want to retriev from the Wikipedia articles, and decided title and extract would be sufficient based on playing around in the sandbox
- How to rank the search responses

### Strengths and Weaknesses

#### Strengths

- Search is quite performant in terms of speed and relevancy

#### Weaknesses

- No tests
- Would be nice to include link to the article in the CLI after searching
- Relied on full-text search

### If More Time

- Remove text-search and implement own text-search using something like KMP or Rabin Karp algorithm where we modify it slightly to return partial matches and we can rank it. The better alternative solution is to use tries, and iiterate over the large body of text once
- Testing
- Improve CLI (more arguments, more granular search, prettify the printing) / implement front-end
- Dockerize solution
- See if there is a way to send one request for N articles rather than N / 20 times
