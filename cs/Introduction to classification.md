# Introduction to classification

## Examples

- Classify reviews as positive or negative
- Classify outlook emails as spam or not
- Classify whether the windows update is good or bad

## Data Explorations

### Basic

- Missing values, biased. **Verify if they are trustworthy**
- **Enough amount of data** to represents the overall population. Invest in more data might be necessary
- **Too much data**, sampling data and time window. Homogeneous sample to remove extra data

### More

- Is my class label evenly distributed.
  - Usually minority class is smaller than 10-15% of the data
  - Balance the class label by **oversampling/under sampling/SMOTE**
- Is class label binary or multi-class(Enum)
- Is the relationship of features linear or non-linear
  - Identify the best machine learning model
  - If data points can be separated by drawing a straight line, then it is linearly separable.

### Handle imbalance data

- Real world data is imbalanced

  - Good update is 94.71% and bad update is 5.29%

- Address this

  - Under-sample the majority class **Reduce some**
    - Random sampler(imblearn package)
    - Sample it down to certain percentage
  - Over-sample the minority class **Fabricate more**
    - SMOTE (Synthetic minority oversampling technique)
    - Create synthetic data points of minority class based  on existing data points.
  - This is only done on the training set. Therefore, when we are testing, it does not cause the model to be tested on fabricated data.
  - It does not means to make it 50/50. 15% verses 85% would be good enough
    - Have more data points of under represented data, helps us to dig data.

- Feature engineering

  - Add new feature
    - Leverage domain knowledge
    - Integrate additional data sources
    - Utilize current features to create new features
  - Text/Categorical features
    - Encoding this text into some numerical form is necessary for machine to learn
      - Convert the categorical features into binary features
      - 1 is yes, 0 is no. Change all those into yes and no.
      - LabelEncoder, OneHotEncoder
    - Multi-labelbinarizer
      - Convert categorical features with multiple values in a row into binary features
  - Numerical features
    - Imputation(outliers)
      - ML algorithm can not handle NAN
      - So impute values based on values in that feature, for example, use mean and median values to fill all the NaN
      - Regression models do that too
    - Normalizer(some times, a data might have higher range and thus need to be lowered)
      - For example, Cores vs Rams. Obviously, cores has 4-16 cores, but ram may have 64 GB!
      - Therefore, converting them to similar ranges is necessary
      - Min-Max normalization, mapping their range
      - Or Z-score, assuming they are in normal dist and then $z=\frac{x-\mu}{\sigma}$
  - Feature selection
    - Select features that are important and provide variance in data
    - Techniques
      - **Chi-square** test
      - **Variance threshold**. Some feature must have variance large enough will be used
      - ANOVA
      - Recursive Feature Elimination, recursively removes the least importance feature by using estimator (svm/logistic regression, etc.)

  ## Access model

  - Accuracy
    - How many total prediction did the algorithm identify correctly?
    - It might be bad, if the data is highly imbalanced
      - Consider, if there is only two bad updates, then if the algorithm does not identify them, it failed totally! 
  - Precision
    - Of the positive prediction made by your algorithm, how many were correct
    - Low precision, identify good as bad
  - Recall
    - Of the positive classes, how many did your algorithm identify
    - Low recall, failed to identify bad

  - F measure
    - $F=2*\frac{precision * recall}{precision + recall}$
    - Combined!

## Models

- Logistic regression
  - Decision boundary
  - Essentially, it make an linear regression to all the features. $f(X_i)=ax_1+bx_2+cx_3+\ldots+k$
    - The value yield by $f(X_i)$ has to be converted into some sort of probability
    - $y_t=\frac{1}{1+e^{-f(x_i)}}$, and this served as probability of good and bad, which serve as how confident we are about the update is good
  - This works only with linear data. When the data is gradiently distributed, it simply does not work.
  - **It only works when data is linearly distributed**
  - Pros
    - Simple, interpretable
  - Cons
    - Linear, very sensitive to outliners
  - Application
    - Starting point and base line
    - `linear_model.LogisticRegression(C=1e5)`, using `scikitlearn`
- Decision trees
  - A series of decision that the model is making
  - It use a series of boundary to yield prediction
    - Give a bunch of feature, and make a series of decision (if, else if, else...)
      - It determines best boundary to separate data, so limits depth is only way to prevent it from overfit.
    - Those decision is tedious to write by hand, and thus, algorithm of decision tree is used.
      - In essence, all tree based algorithm do that.
  - Pros
    - Interpretable, scalable
  - Cons
    - It can overfit with data.
  - Applications
    - Wide spreads
    - Simpler relationship and `max_depth` might need to be limited (e.g., 5)
    - `DecisionTreeClassifier(max_depth=5)`
    - `XGBoost` are layer used
- Neural networks
  - More advanced data model
  - We are creating a network. First randomly set parameters. After that, we backpropagates the results and adjusts the weights which are set randomly initially. Eventually, no more feature is learned
  - Pros
    - Understand deep and complex relationships
  - Cons
    - Complex modeling, require lots of data
  - Applications
    - Image classification, speech recognition. Almost everywhere
    - `MLPClassifier(alpha=1)`
- Make sure the data are standarized, which susceptable to all kind of weird things