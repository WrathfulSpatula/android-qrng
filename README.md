# "True Random"* Permuter Example

I frequently find myself in need of a random number generator, on my person at any given time. Many free smartphone apps abound, for this purpose. Most options available through app stores seem to lack two features I desire:

- Random numbers which are quantum mechanical in origin
- A convenient activity that will not just let me select one of N elements, but also PERMUTE ("shuffle") N elements.

This minimal example app provides both features. Random numbers of quantum origin are served over SSL from a public service by Australia National University (ANU).

The "permute" activity selects "1 out of N," where you specify "N" by your keyboard. The activity automatically decrements its "N" by one after each element generated. (...So convenient, when I need to "shuffle" a small number of elements!) If the random selection is "1 out of 2," the number of elements is not decremented, since there is no random selection of "1 out of 1" element.

The "purify" option under the "request" button for random numbers XORs each subsequent pairs of bits, and returns the result to the cache, in half as many bits. This might not improve the quality of your random bits at all. However, theoretically, it should remove an order of direct correlation between subsequent bit generation results, if there is any.

## Attribution and Licensing

The "AnuRandom" class comes from https://github.com/pcragone/anurandom. This class gets data from a public service provided by ANU. Many thanks to both of them! I have modified the class, only to request the data from the same source via HTTPS instead of HTTP.

My contribution (Daniel Strano's) is licensed under the Apache 2.0 license.

## Warning
*These random numbers are not guaranteed to be high quality. They are only "true random" in the sense that the bit source is theoretically quantum mechanical by operation, to some indeterminate extent.