=====================================
TransactionalCausalInference
=====================================

Warning?
------

I published this experiment to serve as a concept demonstrator (And because it's kinda cool). I cannot recommend this method as a primary, or even secondary, diagnostic approach for investigating poor database performance. There are many simpler metrics you should be looking at first, such as read/write locks and index usages statistics. Database engines are understandably robust (well... mostly (grumble... SQL Server OR statements)), and this project did not find anything that could not be reasonably explained by looking at basic transaction and database properties.

Who?
------

Written by Alexander W. Marunowski.

What?
------

TransactionalCausalInference is an experiment investigating automatic detection of adverse performance interactions that occur between transactions running in a database.

When?
------

Uhhh... my free time?

Where?
------
Tested in:
```
Platform 1:
  Windows 8 Profressional (64-bit)
  SQL Server 2012 Developer (64-bit)
  Wildfly 8.0
```

```
Platform 2:
  Windows 7 Professional (64-bit)
  SQL Server 2008 R2 Developer (64-bit)
  JBoss-AS-7.1.1
```
Why?
------

(Because I'm weird like that)

The inspiration for TransactionalCausalInference came from occasional reports we received that concurrently running two seemingly isolated applications caused performance degradation in one of the applications (by an order of magnitude). 

Unfortunately, we could not find a sound method for quantitatively verifying these reports without disabling the applications and independently capturing performance metrics. As both systems involved are revenue-critical, we had no chance to disable either for an appreciable amount of time. Furthermore, our test environment did not demonstrate the same performance degradation.

Even though I successfully tracked down the source of the problems within a day, the thought of having an automatic system which would detect similar types of interference between transactions stuck with me. As it seemed like a cool idea, I eventually got around to experimenting with it.

How?
------

Patterns are detected by building and populating a search tree of event types. Once a node in the search tree has been visited a sufficient number of times, the algorithm will explore super-sequences of that node. The path taken from the root node to any descendant node represents the event seqeunce which is being analyzed. In this manner, the algorithm borrows ideas from sequence mining, pattern growth algorithms, and frequent itemset mining. For detecting transactions that take longer than expected, Welford's method for computing standard deviation on a stream of real values is used.

Once analysis has progressed reasonably, a list is generated which contains the top #N event sequences which most strongly suggest that an event of interest will occur.

How well?
------

The included demos showcase reliable extraction of sequence-based association-rules from a streaming event source. Given well suited configuration parameters, the confidence of the extracted rules is typically within one or two percent of what is expected from the sample data.

The performance of detecting transactions that interfere with specific queries can be improved by limiting the "analyzeEvent" step of the algorithm to only the events of interest. This would reduce runtime by the percent of event types that we are not interested in.

In terms of results of running this project on a large trace, TransactionalCausalInference does in fact detect sequences of transactions that clearly result in poor performance in the last transaction in the sequence. I will publish some statistics in a results section of the main project. 
