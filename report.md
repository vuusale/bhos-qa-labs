# Analysis of results

Out of the 3 load tests, the best performance belonged to the third one in which the "/postandref" endpoint was tested and no request was failed. The worst performance belongs to */post* endpoint - 292 requests failed.

|Statistics|t < 800ms|800ms < t < 1200ms|failed|
|---|---|---|---|
|*/post*|9476|197|292|
|*/postwithref*|9800|86|75|
|*/postandref*|9951|48|0|