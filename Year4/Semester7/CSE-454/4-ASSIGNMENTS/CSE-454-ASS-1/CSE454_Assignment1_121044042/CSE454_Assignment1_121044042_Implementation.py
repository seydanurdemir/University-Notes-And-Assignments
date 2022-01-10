import numpy as np
import matplotlib.pyplot as plt

def dbscan(D, eps,MinPts):
  '''the inputs has the same name as shown in the question
  @input
  D: a data set containing n objects,
  Eps: the radius parameter, and
  MinPts: the neighborhood density threshold.'''
  # STEP 1
  clusters = [0]*len(D) # this will contains the output cluster values of each datapoint
  # we will initialize with all zeros (means  mark all objects as unvisited)
  # the value -1 represent it is a noise

  currentClusterID = 0
  # STEP 2 
  for P in range(0, len(D)):
    # STEP 3 : randomly select an unvisited object p;
    if not (clusters[P] == 0):
      continue
    # STEP : 4 :  Find all of P's neighboring points.
    neighbouringPoints = findNeighbouringPoints(D, P, eps)
    # STEP : 5 : If the Eps-neighborhood of p0 has at least MinPts points, add those points to N;
    if len(neighbouringPoints) < MinPts:
      clusters[P] = -1
    else:
      currentClusterID = currentClusterID + 1 #new cluster 
      newCluster(D, clusters, P, neighbouringPoints, currentClusterID, eps, MinPts)

  return clusters

def findNeighbouringPoints(D, P, eps):
  neighbors = []
  # for all the points find the distance
  for point in range(0, len(D)):
    # If the distance is below the threshold, add it to the neighbors list.
    if np.linalg.norm(D[P] - D[point]) < eps:
      neighbors.append(point)
  return neighbors

def newCluster(D, clusters, P, neighbouringPoints, currentClusterID, eps, MinPts):
  # Assign the cluster label to the seed point.
  clusters[P] = currentClusterID
  i = 0
  while i < len(neighbouringPoints):    
    # Get the next point from the queue.        
    Pn = neighbouringPoints[i]
    if clusters[Pn] == -1:
      clusters[Pn] = currentClusterID
    elif clusters[Pn] == 0:
      # Add Pn to cluster C (Assign cluster label C).
      clusters[Pn] = currentClusterID

      # Find all the neighbors of Pn
      PnNeighborPts = findNeighbouringPoints(D, Pn, eps)

      # If Pn has at least MinPts neighbors, it's a branch point. Add all of its neighbors to the queue
      if len(PnNeighborPts) >= MinPts:
        neighbouringPoints = neighbouringPoints + PnNeighborPts
    i += 1        
    
########################
# TO TEST THE CODE
#######################
# we use this to crease a sample datasets
from sklearn.datasets.samples_generator import make_blobs
from sklearn.preprocessing import StandardScaler

# Create three gaussian blobs to use as our clustering data.
centers = [[1, 1], [-1, -1], [1, -1]]
X, labels_true = make_blobs(n_samples=750, centers=centers, cluster_std=0.4, random_state=0) # create 750 values
X = StandardScaler().fit_transform(X)
theClustersForEachPoints = dbscan(X, eps=0.3, MinPts=10)  
# I will display the first 10 cluster values
print(theClustersForEachPoints[:10])

centers = [[1, 1], [-1, -1], [1, -1]]
X, labels_true = make_blobs(n_samples=750, centers=centers, cluster_std=0.4, random_state=0) # create 750 values
X = StandardScaler().fit_transform(X)
theClustersForEachPoints = dbscan(X, eps=0.3, MinPts=10)  

print(theClustersForEachPoints)

plt.plot(theClustersForEachPoints,'ro')
plt.xlabel('Deaths')
plt.ylabel('Confirmed')
plt.title('Clusters')
plt.grid(True)
plt.show()