# Introduction to NLP

## Overview

- Machine translation
- Sentiment analysis/Text classification
- Text summarization
- Question and answering
- Code writing/**Github Copilot**/OpenAI Codex

- Part of speech tagging
  - It analyse the text, which is article/noun/verb/preposition etc.
- Word embedding
  - Similarity between words
- Language model
  - A very resource consuming task
  - Given previous words and predicting what word should come next

### Microsoft Example

- Windows feedback. Use AI to route messages to correct engineering team
- Azure incident tickets, automatically determine which engineering team own the ticket
- Azure self help portal, predict the potential fixes for their issue based on the text description of their problem

### Pipe line

- Data source (text data)
- Text pre-processing
- Text featurization
- Machine learning algorithm
- And yields predictions (tags/labels)

## Text Preprocessing

- Case folding
  - Replace all the text with lower case
- Normalize negations (expand abbreviations)
  - doesn't -> does not
  - can't -> can not
- Create special tokens
  - :( -> _sad_
  - :angry: -> _angry_
- Strip punctuation
  - Remove all the punctuations
- Stop word removal
  - Remove common works, for example, `it does` -> `not`. There is an dictionary to make the data more useful and concentrated with data.

- Text tokenization
  - Punctuation
  - Spaces (Which is not available for Chinese)
- Stemming/Lemmatization
  - Rule based suffix striping may not correct, it can be overly aggressive
    - Essentially, likes/liking/liked/likely -> like
    - Programmer/Programming/Programs -> program
    - **Faster and reduce vocab lower accuracy** and lead to false positive.
  - Lemmatization uses knowledge of part of speech and the English dictionary to transform a word into its dictionary form or lemma
    - It yields better results and predictions
    - **Computationally demanding but more accurate** 
  - No stemming lead to low recall

## Text Featurization

- To make neural network read texts
- Tokenization
  - The process of taking sequence of characters and turning it into a collection of tokens that we give meaning.
  - `skye not use` -> `["skype", "not", "use"]`
    - Bags of words
  - N-Grams tokenizer
    - Unigram, which takes one words
    - Bi-grams, which takes two words
    - Tri-grams, which takes three words
    - 1 skip 2 grams, take a word, skip a word, and take a word
    - 2 skip 2 grams, take a word, skip 2 word, and take a word
    - etc.
    - **Those different tokenizer** just makes neural network to understand the connection of those words
      - See if skip grams make things better, just use training set and test set, and see if the accuracy is improved with the use of such tokenizer.
    - **Negative sampling** **TODO**
      - It is always used for word-embedding. It is computationally expensive, and therefore, negative sampling is always used.
      - If do all words at once, nothing words. 
- Dictionary
  - Remove the vocabs that has occurred in every documents
  - Those constant features simply does not help, and thus remove them
- Numeric representation
  - Vocab frequency counter
  - It gives the idea about importance of certain words in the documents
- Inverse document frequency weights
  - Leverage the information about importance of a word baseed on how frequently we see it in a set of documents
  - $idf(d,t)=\log(\frac{N}{|d\in D; t\in d|})$
    - N is number of documents
  - $td * idf$, it yields **TODO, LOOK BACK**
  - Essentially, the words that occurred more often is given lower score.

## Word Embeddings

- Con of bag of words
  - Easy to compute, no learning required to leverage information about words
  - Does a decent job in a wide variety of tasks
- Weakness of bag words
  - **It does not handle contextually similar words**
- Sole
  - Quantify the semantic meaning between words
  - Represent the representation of wwords statistically
  - allow features to be learned

- **Word2Vec**
- Process
  - Unigram, and store in list(ordered!)
  - And then look around about context.
  - Skip-gram to see the relevance
  - Word vector
  -  Softmax, **LOOKBACK**

- Cosine similarity
  - Angle between two vectors, Ms. Gray's extra

## Example

- Word2Vec
  - Predict context around a words
- Document classification
  - Take document and run them through pre-processing(and featurization) and feed into pre-trained model
  - Then after that, we can classify model
  - Similarity between documents and cosine similarity? Recommendation system would work with that!

- Recommend stuff
  - NTLK/Textblobs
  - Scikit-learn
  - CoreNLP
  - Genism
  - Spacy
  - SparkNLP, which scalable library for doing NLP, distributed
  - MLM model, predict and complete extra text. NLG, natural language generation
- Pre-trained
  - If small amount of data is available, try to use simple models and make it work. If it does not,
  - go to pre-trained model and add an layer to output layer of pre-trained model.
  - Transformer model.
- Sentence level embedding
  - Find average word embedding in the sentence, not very effective
  - Dot-cat which does document level. Not very effective
  - Pre-trained / transformer model / HuggingFace transformers, which has a lot of pre-trained model available for us to use.

