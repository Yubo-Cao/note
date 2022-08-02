# DLFC Lesson 1

## Prerequisite

- You don't need
  - A lots of math -> Just high school math is sufficient
  - A lots of data -> With in 50 items of data could work
  - A lots of expensive computers -> Best computer is in cloud for free (don't have to buy NVIDIA)
- Python programming language experience is necessary. 
- **Top to bottom**
  - Study the whole process
  - Make this process interesting, so you master how to do it
  - Tear apart this process to understand it

## What does it do

- Not artificial general intelligence
- Natural language processing(NLP)
- Computer vision(CV)
- Medicine and Biology
- Image generation
- Recommendation systems
- Playing games and robotics
- Financial and logistical forecasting, text to speech, etc.
- **Deep learning is very useful in wide application area, sometimes, out perform human too!** 

## Neural networks history

- 1943 Warren McCulloch develop a mathematical model for neuro network
- The first AI winter, was introduced by Marvin Minsky, which shows that single layer of those complicated neuro-network devices can not learn simple functions, like XOR.
  - However, multiple layer of network is introduced
- Parallel distributed processing (PDP), released in 1986
  - Processing units + State of activation + Output function + Activation function -> Learning everything
  - ![image-20220531163433508](C:/Users/Cao20/AppData/Roaming/Typora/typora-user-images/image-20220531163433508.png)
  - **Everything we are doing today**, are just different way to implements those different molecules
- 1980, most models were built with second layer of neurons.
  - In theory, this just one extra layer was enough to approximate any mathematical model
  - **They are too big and slow to be useful**.
  - To get better performance, **more layers of neuron** is used (deep learning helps performance) as well as better **computer hardware, data availability, and algorithm design** make all those possible.
    - ==A machine capable of perceiving, recognizing, and identifying== is eventually created.

## Software Stack

- `fastai` library
  - wrapper of `pytorch` library, which contains a lot of helper methods and hide some implementation details
  - layered API so it faced a wide array of users
- `pytorch` library (used to be `tensorflow`)
  - `tensorflow` was flawed, and soon nobody use it
  - designed for developer flexibility and friendliness
- `python` interpreter and `jupyter` notebook
  - The vast majority of researcher of deep learning using it

**Concepts** are more important than low-level details.

## GPU

- Graphics processing unit, NVIDIA is necessary because no other brands of GPU is fully supported by the main deep learning libraries
- Remember to shutdown your instance of notebook, since otherwise you are paying for it.
  - `Colab` does not auto-save





