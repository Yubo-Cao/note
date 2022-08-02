# Model to Production

## Idea

- By using the end-to-end iteration approach, you will also get a better understanding of how much data you really need
- Don’t start with something that nobody has tried before. You won’t know whether you made a mistake or your hit capability of deep learning

## State of Deep Learning

- deep learning be used not as an entirely automated process, but as part of a process in which the model and a human user interact closely
  - make human more productive and keep the result accurate

### CV

- Object recognition (recognize items in the image)
- Object detection (detect the position of objects, e.g., *segmentation*)
- Model almost always do poorly on images that they have not seen before.
  - Recognize unexpected image types
  - Image labeling can be slow and expensive. **data augmentation**

### NLP

- Classify text
- Generating context appropriate text
  - may be incorrect although looks compelling to a layman.
  - context appropriate
- **Translating**,  **summarizing**, search by **concept** rather than keywords. However, it could be completely incorrect too, e.g., google translate some times do weird thing too!

### Text & Image

- Create caption based on input image.
  - There is no guarantee that the captions will be correct.

### Tabular

- **Time series** and **tabular data**
  - It may not do a far better job than random forests or gradient boosting machines.
- Increased variety of columns that one may include.
  - Natural language
  - High cardinality categorical columns
- Longer time to train

#### Recommendation

- A special type of **tabular data** with a high cardinality variable representing users and another one to represents products.
  - It only give recommendations for products a user might like, not **might be helpful**

## Convert other data type

- Find a way to convert them to text, image, or tabular data to make them work.

![image-20220705191311181](/home/yubo/.config/Typora/typora-user-images/image-20220705191311181.png)

- Building the predictive models.
  - Our objective and available levers, e.g., **what is user’s main objective when typing a search query?**
  - What data we already have and what additional data we will need to collect, e.g., **links in the website**
  - Determine the models we can build.

## Gather Data

> # Objective
>
> Bear detector, which discriminate between grizzly, black, and teddy bears.

- Models reflect only data that used to train them. Since the world is full of biased data, model might end up reflecting the biases.

