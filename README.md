TransactionalCausalInference
============================

Performs association-rule mining on frequent subsequences of events.

============================

What it can do: 


This application can be used to detect detrimental performance interactions between transactions running in a database.

This is accomplished by performing basic data-mining techniques. 

Specifically, the algorithm employed is inspired by both association-rule mining, and frequent subsequence mining.


============================

Demo:


For a demo of how this project looks when it runs, simply run the main method in the following file:
"\src\oss\marunowskia\datamining\transactionalcausalinference\StartupSequence.java"

============================

Running this project without JEE:


(
For brevity... while still using Java, that is... this project is implemented as a Java EE 7 deployable web-archive. This is primarily for the convenience of using hibernate for ORM.

If  you wish to run this project as a raw Java application, you will have to modify the following files:
1) DatabaseUtility.java
2) StartupSequence.java
)

============================

Running this project with JEE:


The project itself can be imported as an Eclipse project.

The application itself should deploy on any Java EE 7 application-server, however, I have only tested it using Wildfly 8.0

============================

Analyzing database trace results:



When analyzing a SQL-Server database trace, the trace will have to be stored in a table for ease of retrieval.
(
  Disclaimer: It is inadvisable to write the results of a MSSQL trace back to the database on which the trace is running.
  Best case scenario, you'll get a non-characteristic sample of your database transactions.
  Worst case scenario, you crash your server :)
)
To analyze your trace data, you will need to specify a database connection in your standalone.xml.
This connection should point to the database where you have stored your MSSQL trace.

!!! The project expects the trace data to be located in "resesarch.dbo.full_trace", but this may be changed directly to wherever your trace data is stored. !!!


----------------------------------------------------------------------------------------------------------------------

You must define a configuration table using the following SQL script

You will have to replace "research" with the name of the database that holds your trace data

----------------------------------------------------------------------------------------------------------------------
USE [research]

GO

/****** Object:  Table [dbo].[TRANSACTIONAL_CAUSAL_INFERENCE_CONFIG]    Script Date: 8/17/2014 6:10:16 PM ******/

SET ANSI_NULLS ON

GO

SET QUOTED_IDENTIFIER ON

GO

SET ANSI_PADDING ON

GO

CREATE TABLE [dbo].[TRANSACTIONAL_CAUSAL_INFERENCE_CONFIG](

	[CONFIG_NAME] [varchar](MAX) NULL,
	
	[CONFIG_VALUE] [varchar](MAX) NULL
	
) ON [PRIMARY]


GO

SET ANSI_PADDING OFF

GO

INSERT [dbo].[TRANSACTIONAL_CAUSAL_INFERENCE_CONFIG] ([CONFIG_NAME], [CONFIG_VALUE]) VALUES (N'SUPPORT_THRESHOLD', N'500')

GO

INSERT [dbo].[TRANSACTIONAL_CAUSAL_INFERENCE_CONFIG] ([CONFIG_NAME], [CONFIG_VALUE]) VALUES (N'RULE_DEPTH_LIMIT', N'5')

GO

INSERT [dbo].[TRANSACTIONAL_CAUSAL_INFERENCE_CONFIG] ([CONFIG_NAME], [CONFIG_VALUE]) VALUES (N'HISTORY_LIMIT', N'50')

GO

----------------------------------------------------------------------------------------------------------------------

When the application deploys, it will start analyzing the trace automatically, using the values from your config table.



============================

Background:

This project sprang forth from annecdotal evidence of unexpected contention between transactions in our SQL-Server database.

I desired a tool which could provide quantitative observations on the degree to which pairs of transactions affect each other.

Although I quickly identified the root cause of the database contention, the idea for such a tool stuck.

Eventually an opportunity to implement my ideas presented itself: the final-project for a graduate-level course in datamining at Case Western Reserve University.

After the course ended, I rewrote the application using insights gained from the feedback I received.

============================
