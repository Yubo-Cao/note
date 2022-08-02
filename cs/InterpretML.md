- Model correctness and accuracy depends on how mode will be used.
  - This is a good model for health insurance provides
  - But needs to be repaired before using for patient treatment decision
- Few things that model learned
  - Obstructed airway/Asthma/Chest pain/Heart disease, lower risk.
  - It possible that urgent situation helps patient to get health care quicker, and thus, higher quality treatment.
  - However, if we really treat urgent patient with that, it is very bad!
    - **Therefore, see what happens with your model is important!**
  - Notice, model is rewarded by predicting those features, for it is correct.
- Improve
  - It is important for us to have more sound data set, which makes the model more accurate to some extent. **Feature engineering** and **adding more features**. 
  - The bias of model arises from our data.
    - We can not improve the quality of data. It is unethical for us to ask all the patient, regardless of their urgency, to get same medical care.
    - The data collected are only from patient who go to health care.
  - **Must keep potentially offending features in model**
    - Only this way, we can correct/delete/edit terms after seeing what model learned
    - Excluding features is actually bad.
  - Treatment sometimes actually makes our statistics looks weird
    - People at age 50/120 actually has lower risk, because of treatment
    - We realize that if we remove round number effect, i.e., treat people at 100 years old, helps save a lot of life of people.
- Pairwise interaction
  - Xor and age vs cancer 2d graph
- **Ethics** of black box machine learning model.
  - Black box prediction is statistically correct.
  - Looks at the model, and editing the model so that it will to correct.
  - **It is better to be used after human expert has looked at the model and corrected it**. 
  - Model need to be reviewed and checked

- House pricing
  - Flatten spikes and outliers
  - Look at data to deal some things
- WIkipedia
  - 160,000 edits per day, 10%-15% edits are flagged as damaging.
  - People tends to do bad editing after 1 month since registration.
    - Therefore, they log people out after 30 days. Since if people remember their password, less likely they will do bad editing.
- Create new feature
- Being able to see and understand data model helps as to do better data analysis



- GAN training
  - Features add together, add trees and residuals.
  - Round robbin boosting.
  - Bagging Bayon tree.
  - Summarize all those trees as a graph, so we can analyse each tree individually. All those trees are removed.
  - Each feature feeds into a separate DNN subnet
    - Subnets added together at output layer
    - Sigmoid function for classification. Without that, we have regression.
    - Trained at massive scale on GPUs with standard software.
    - Deep network, can not be understood. They are summarized to graph. We prefer graph, because tree can not be understood by human. There is **no benefits from looking at tree**.
    - **Order is not important for algorithm**. Learning rate is low, so the model is expensive and takes a lot of time.
    - After that, we throw away the neural net, and only graph (look up table, quick and tiny.) We literally obtained that by get a very high granular data into table, and then record all the output.
      - They does not learn jumps without overfitting
      - $ExU: h(x)=f(e^w\cdot (x-b))$ Improved activation function. Therefore the function get somehow `overfitting` easily. Then correct it with ReLU.
      - DNN do pretty well, little or no loss in accuracy. This is better with regression, but not classification.
      - **Round number** effect is always there....
  - Neural net tends to do linear regression. Tree does not go crazy, since they are just piece wise functions. Extrapolation is better with tree, also it is risky anyway. 
    - Gradient boosting tree
    - Open source package. `Interpret-ml`
- Pairwise
  - Fit main effects
  - Freeze main
  - Compute residual
  - FAST heuristic sort out pairs. Summarize all the trees that use pairs. End up with bunch of graphs that summarize the pair.



- Multi task NAM, essentially, multiple outputs is required. 
  - Each task will put an weight on neural net feature.
  - We have multiple subnets to learn from features. Task shall decide which version of net is better. Tasks will choose different version of net, which helps us to get multi-task benefits.
- Multi task NAM usually improves the accuracy, because they share data sets that are initially, impossible.
- If model is differentiable, personalize treatment of COVID



- MAR, missing at random
  - If it falls into weighted average, then it is missing at random clearly.
  - Doing statistical test can make it happen.
- MCAR, missing completely at random
  - Selection bias. For example, patient has to be 75 years or older to join that survey
  - Model/Graph editor.
  - Automatically increasing. 
  - Glass-box ML.
- MNAM, missing not at random
  - Most common case
  - **Missing assumed normal**, essentially, data is not get because it does not looks needed.
    - Example, people with heart rate 40-125, nurse does not measure their penumenuia risk rate.
    - However, model interpolate would predict that it has middle risk. 
    - Therefore, we may need to repair it by talks with doctor about bring it down to 0, or keep it as is for insurance company.
  - Missing values imputed with ean
  - Mean **Imputation**, RF imputed, KNN imputed.
    - Those imputation may cause more problem. Leave them unchanged probably better.
    - 2nd order derivative of bins.
    - Outllier detector
    - Is mean/median an outlier.
    - **Outlier**  is problem/



**Glass-box** model!