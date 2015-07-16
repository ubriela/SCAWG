*** This repository is the implementation of the following paper: ***

---------------------- Version --------------------------
1.1
---------------------- Contributors --------------------------
Hien To
---------------------- Packages --------------------------

org.geocrowd
	common interfaces, e.g, datasets (e.g., skewed, uniform), distributions, worker/task arrival rates
	
org.geocrowd.common.crowdsource
	common crowdsource objects, e.g., generic worker, sensing task

org.geocrowd.common.entropy
	common objects, served for calculating location/region entropy or density of the workers and tasks
	
org.geocrowd.common.utils
	utility classes, e.g., for computing properties/statistics of the datasets

org.geocrowd.datasets
	generic data processor and parser
	
org.geocrowd.datasets.dtype
	common types of data points and queries
	
org.geocrowd.datasets.plot
	utility classes for plotting data points as well as drawing line, bar charts, etc.
	
org.geocrowd.datasets.synthesis
	classes for generating artificial/synthesized data from real datasets, e.g., gowalla, yelp
	
org.geocrowd:
	main package, with GeoCrowd.java
	
org.geocrowd.maxmatching:
	a package to solve weighted bipartite matching

test.datasets:
	testing functions

----------- SYNTHETIC DATASETS -----------------------------------------------



----------- Output of data synthesizer (stage 1)
WORKER FILES
latitude, longitude

TASK FILES:
latitude, longitude

----------- Output of data synthesizer (stage 2)
WORKER FILES:
******
worker_id, latitude, longitude, maxT, working_region[min_lat, min_lng, max_lat, max_lng], expertise_ids[expertise_id]
EAvzjtPx7kBk83GCWiaSDA,33.5130361,-112.08681055,4,[33.500805,-112.0995348,33.5252672,-112.0740863],[33,11,4,5,6]
******

TASK FILES:
******
latitude, longitude, time_instance, density, task_type
36.9778628999,-121.96604528228605,10,1.9609640474436814,1
******

----------- SYNTHESIS DATASETS -----------------------------------------------

---------------------- Output of data synthesizer ----------------------
Extract data from Gowalla dataset. This is an intermediate input file
******
userID	datetime	lat	lng	pointID
2	2010-10-21T00:03:50Z	34.0430230998	-118.2671570778	14637
******
				
---------------------- gowalla_entropy ----------------------
Location entropy of each location id
******
loc_id,	loc_entropy
******

---------------------- gowalla_loc_entropy ----------------------
Location entropy of each grid cell (after grid discretization)
******
row,col,loc_entropy
******
