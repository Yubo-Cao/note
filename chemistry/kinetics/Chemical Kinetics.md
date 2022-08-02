[[Chemical Equilibrium]]
# Intro
- Speed of reaction to occur is important. This is defined as **reaction rate**
	- Related with temperature. Usually, lower temperature decreases reaction rate and higher temperature increases reaction rate.
- **Chemical kinetics**, the chemistry that deals with the reaction rates.
- **Reaction mechanism**, a step-by-step, molecular-level view of the path way from reactants to products, important to understand reactions.
# Factors About Reaction Rates
- **Physical state of the reactants**, as reactants must come together to react, the more readily reactant molecules collide with each other, the more rapidly that they react
	- **Homogeneous**, only gas or liquid involved
	- **Heterogeneous**, reactants in different phrases involved.
		- Area of contact limits the reaction, e.g., large surface area of solid faster the speed of reaction.
- **Reactant concentrations**, chemical reactions proceeeds more quickly if the concentration of one or more reactants is increased.
	- Experiment that Mr. Calhound has done with burn steel wool in pure $\ce{O2}$
	- Higher concentration increase frequency of molecules collide, leading increased rates.
- **Reaction temperature**, reaction rates increase as temperature is increased.
	- Higher speed of collision
	- Higher energy of collision
- **Catalyst**, increase the reaction rates without involving it self into the chemical reaction (essentially, it is not used up)
	- Change the kind of collisions that lead to the desired reaction.
- Reaction rate depend on frequency of collisisons between molecules.
	- **Greater the freqnecy of collisions, the higher the reaction rate.**
	- Collision occur only when
		- Suitable orientation for new bonds to form
		- Sufficient energy to break bond
# Reaction Rates
## Average
- Speed of a chemical reaction, the reaction rate, is the change in concentration of reactants or products per unit of time.
	- molarity per second $\frac{\Delta M}{\Delta t} = M/s$
	- using brakets around chemical formula to indicate molarity,e.g., $[\ce{H2O}]$
- By convention, **rates are always expressedas positive quantities.**
	- As if the substance decrease, the $\Delta M$ would be negative. Therefore, a negative sign is necessary for the **rate of disappearance**.
	- It is also ok to express as **rate of appearance** of product.
- ![[Pasted image 20220517211738.png]]
	- In interval $t\in [0, 20]\textrm{s}$:
		- $\textrm{Average rate of appearance of B} = \ce{\frac{0.46 M - 0.00 M}{20s - 0s} = 2.3 \times 10^{-2} M/s}$
		- $\textrm{Average rate of disappearance of A} = \ce{-\frac{0.54 M - 1.00 M}{20s - 0s} = 2.3 \times 10^{-2} M/s}$
	- In interval $t\in [20, 40]\textrm{s}$
		- $\ce{Reaction rate = -\frac{0.30 mol  - 0.54 mol}{40s - 20s} = 1.2 \times 10^{-2} M/s}$ 
## $\frac{dr}{dt}$ && $-\frac{dM}{dt}$
- **It is typical for reaction rates to decrease as a reaction proceeds**, because the concentration of reactants decreases ($\ce{v}$ frequency of collision)
- **Instantaneous rate** of a reaction, is the rate at a particular instant during the reaction, or essentially, determined from the slope of the curve from graph about molarity of reactants verses the time --	- $-\frac{d [\textrm{reactant}]}{dt}$
	- For our purpose, it will be approximated by drawing tangent line and read the intersection with grids on the graph.
	- ![[Pasted image 20220517212958.png]]
		- **Inital rate**, the instanteous rate at $t=0$
		- **Instantaneous rate** decrease as the reaction proceeds, as stated above.
## Reaction Rates and Stoichiometry
- The rate of appearance of products and rate of disappearance of reactants are equal, only when their stoichiometric relationships are one to one.
- Consider another example
	- $\ce{2HI(g) -> H2(g) + I2(g)}$ won't work, because every 2 mol of $\ce{HI}$ disappears for formation of 1 mol of $\ce{H2, I2}$
	- To eliminate the such difference, the rate must be devided by its corresponding coefficient in the formula.
- In short, for the reaction
	- $\ce{a_0 A_0 + a_1 A_1 ... a_n A_n -> b_0 B_0 + b_1 B_1 ... b_r B_r}$
	- $\ce{rate = -\frac{\Delta [A_0]}{\Delta t a_0} = -\frac{\Delta [A_1]}{\Delta t a_1} \ldots = -\frac{\Delta [A_n]}{\Delta t a_n} = \frac{\Delta [B_0]}{\Delta t b_0} = \frac{\Delta [B_1]}{\Delta t b_1} = \ldots = \frac{\Delta [B_r]}{\Delta t b_r}}$
- Whenever the reactant/product is not specified, the rate adjusted by stoichiometry ratio is referred.
# Concentration and Rate Laws
- Determine the initial rate of a reaction.
	- The rate is proportional to the exponentiation of a molarity of reactant
	- For a general reaction
		- $\ce{a A + b B -> c C + d D}$
		- $\ce{Rate = k[A]^m[B]^n}$
			- $\ce{k}$ is known as **rate constant**, which varies with temperature, and thus, determines the relationship of temperature and reaction rate.
				- This constant is not dependent on concentration of the reactants.
				- Rate constant usually does not has same units as the rate, but sometimes, if the exponential may make things weird.
			- $\ce{m, n} \in \mathbb{Z}^*$ and are usually small. (sometimes, it is possible to have $\frac{1}{2}$ and other weird stuff)
				- This is called **reaction orders**.
				- If reaction order equals 1, then it is called **first order**
				- The overall reaction order is the sum of the orders with respect to each reactants in the rate law.
					- **Second order overall** means the average reaction order is 2.
				- **For any reaction, the rate law must be determined experimentally**
					- For most of the common reactio, thosee reation orders are integers.
## Magnitude and Unit of Rate Constant
### Magnitude
- $k < 10$ usually means slow reaction
- $k > 10^{9}$ usually means fast reaction
### Unit
- Do some dimensional analysis
- $\ce{\frac{M/s}{M^2} = M^{-1}s^{-1}}$, for example, if the overall reaction order is 2
### Obtain Rate Law
- Whenever there is only one reactants is changing in two experiments, we are able to calculate the reaction orders through calculate the relative ratio between rate between different reactions.
	- $\frac{Rate 1}{Rate 2} = \frac{k[A_1]^m[B_1]^n}{k[A_2]^m[B_2]^n}$, and the same reactants will cancelled if they have same mass. Then make a log function to calculate the reaction rate, as desired.
# Change in concentration with time
- Using the rate laws provided, we are able to calculate instanteous rate of a reaction given concentration of reactants as well as rate constant.
- This would require some knowledge about calculus.
## First Order Concentrations
$$
\ce{Rate = -\frac{\Delta [A]}{\Delta t} = k[A]}
$$
- The first order reactions are those whose rates depends on concentration of a single reactant raised to its first power.
- 