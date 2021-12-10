# Analysis of results

Load testing results demonstrated that binary search algorithm performs way better than linear search algorithm. 

The first load testing showed that linear search algorithm found the first bad version (8794) among the 10000 versions in 147 ms. However, binary search algorithm finished in 86 ms, almost two times faster. 

The reason for this difference in performance is that linear search algorithm iterates over all input data until finding the correct answer. Its time complexity is O(n). On the other hand, binary search algorithm halves the search space each time and time complexity is O(logn).