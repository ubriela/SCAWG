*** This repository is the implementation of the following paper: ***
Hien To, Mohammad Asghari, Dingxiong Deng, Cyrus Shahabi. SCAWG: A Toolbox for Generating Synthetic Workload for Spatial Crowdsourcing. The First IEEE International Workshop on Benchmarks for Ubiquitous Crowdsourcing: Metrics, Methodologies, and Datasets, 2016

Related studies:

https://github.com/infolab-usc/geocrowd

https://github.com/ubriela/geocrowd-priv-dynamic

https://github.com/ubriela/geocrowd-priv

https://github.com/ubriela/geocrowd-priv-demo

https://github.com/ubriela/geocrowd-complex

---------------------- Version --------------------------
1.1
---------------------- Contributors --------------------------
Hien To
---------------------- Usage --------------------------
Two steps:

Step 1 generates spatial distributions of workers and tasks as well as their time instances (if specify). See function testGenerate2DPoints() in test.datasets.GenericProcessorTest.java. The output of workers and tasks is in the res/dataset/worker and res/dataset/task folders, respectively.

Step 2 take the output of step 1 to generates various type of worker type and task type combination. The output of workers and tasks is in the dataset/<dataset_type>/worker and res/<dataset_type>/task folders, respectively.

----------- Output Example of Workers and Tasks
WORKER FILES:
******
worker_id, latitude, longitude, maxT, activeness, working_region[min_lat, min_lng, max_lat, max_lng], expertise_ids[expertise_id]
EAvzjtPx7kBk83GCWiaSDA,33.5130361,-112.08681055,4, 0.5, [33.500805,-112.0995348,33.5252672,-112.0740863],[33,11,4,5,6]
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
---------------------- Packages --------------------------

org.geocrowd
	geocrowd options, e.g, datasets (e.g., skewed, uniform), distributions, worker/task arrival rates
	
org.geocrowd.common.crowd
	common crowdsource objects, e.g., generic worker, sensing task

org.geocrowd.common.entropy
	common objects, served for calculating location/region entropy or density of the workers and tasks
	
org.geocrowd.common.utils
	utility classes, e.g., for computing properties/statistics of the datasets

org.geocrowd.dtype
	common types of data points and queries
	
org.geocrowd.params
	constants, parameters
	
org.geocrowd.synthetic
	generic processor and parser

org.geocrowd.synthetic.grid
	scaling small datasets to large datasets using 2D histogram
	
org.geocrowd.plot
	utility classes for plotting data points as well as drawing line, bar charts, etc.
	
org.geocrowd.synthesis
	classes for generating artificial/synthesized data from real datasets, e.g., gowalla, yelp

test.datasets:
	testing functions
