---
date created: 2022-06-03 14:46
---

# Decision-Making

## AB Test

- Example 1 Loyalty program from Xbox
  - A/B test in Brazil and UK, incentivized or not incentivized
    - UK: +1.22 additional active days per active user
    - BR: +3.9 additional active days per active user
  - Challenge
    - Only fraction of user claim rewards.
- **Use econometrics based tools to go beyond A/B test**

## Go Beyond

- Synthetic control: country that looks like them, if they were not subject to the intervention
  - Arguably the most important innovation in the policy evaluation literature in past 5 years
- Instrumental variables (Evolved A/B test)
  - Leverage the variation induced by experimental increase in rewards to learn about the effect of claiming points
  - Effect of claiming reward on user level active days over first 60 days of experiment
  - **Substantially better effects**

## Pricing

- Simple experimentation would be difficult
	- Often, we can not charge different customers different prices
	- Cost of null-effect experiments or bad design, can be huge
- **Economic theory** provides guidance for designing experiments and modeling customers' price sensitivity, as to avoid bad consequences.
- Better way to do it
	- Promotion evaluation.
		- Set promos effectively and efficiently
		- Currently
			- **Rely on surveys, rules of thumb, may not match with real market data**
		- **Data driven approach**
			- estimate the causal uplift of historical promos using actual sales from NPD (third party that calculate those stats) and internal store data.
			- MSRP, manufacture suggested retail price
	- Azure Sot (VM) pricing
		- Sell excess Azure VM capacity at a discount using preemptive VMs (kick customer off when necessary)
		- What is the price that should be set, so that there won't be too much customer, nor too less.
		- Change the price of the VM experimentally, add some random variations
			- **Consequences**
				- Kick off customers is a bad idea
				- Let customers not buy it is also a bad idea (which may happen if adjusted too high..)
			- **Development** of new pricing algorithm
				- Monthly price updates allow us to shape demand
				- Elasticizes and cannibalization, insights
- **Work with economists** 
	- Frame those problems into quantifiable and actionable questions
	- Model developments
		- Quantifying the influence of a feature
	- Larger strategic influences

