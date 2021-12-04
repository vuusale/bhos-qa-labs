# Analysis of results

Out of the 2 load tests, the best performance belongs to the second one in which the */updatebyid* endpoint was tested and no request failed. The worst performance belongs to */updatebyname* endpoint - only ~30% of requests were processed in less than 800ms.

The reason why updating a record by *name* field exhibited poor performance while updating by ID worked flawlessly is that ID is an indexed field in database which contains keys in a structure that enables SQL server to find the row associated with the key quickly and efficiently. Since *name* field is not indexed, looking up records with this field was quite slow and failed in ~8.5% of cases. 

|Statistics|t < 800ms|800ms < t < 1200ms|t > 1200ms|failed|
|---|---|---|---|---|
|/updatebyname|3352|1849|3950|849|
|/updatebyid|10000|0|0|0|
