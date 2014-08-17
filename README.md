<br>	TransactionalCausalInference
<br>	============================
<br>	
<br>	Performs association-rule mining on frequent subsequences of events.
<br>	
<br>	============================
<br>	
<br>	What it can do: 
<br>	
<br>	
<br>	This application can be used to detect detrimental performance interactions between transactions running in a database.
<br>	
<br>	This is accomplished by performing basic data-mining techniques. 
<br>	
<br>	Specifically, the algorithm employed is inspired by both association-rule mining, and frequent subsequence mining.
<br>	
<br>	
<br>	============================
<br>	
<br>	Demo:
<br>	
<br>	
<br>	For a demo of how this project looks when it runs, simply run the main method in the following file:<br>
<br>	"\src\oss\marunowskia\datamining\transactionalcausalinference\StartupSequence.java"<br>
<br>	
<br>	============================
<br>	
<br>	Running this project without JEE:
<br>	
<br>	
<br>	(
<br>	For brevity... while still using Java, that is... this project is implemented as a Java EE 7 deployable web-archive. This is primarily for the convenience of using hibernate for ORM.
<br>	
<br>	If  you wish to run this project as a raw Java application, you will have to modify the following files:
<br>	1) DatabaseUtility.java
<br>	2) StartupSequence.java
<br>	)
<br>	
<br>	============================
<br>	
<br>	Running this project with JEE:
<br>	
<br>	
<br>	The project itself can be imported as an Eclipse project.
<br>	
<br>	The application itself should deploy on any Java EE 7 application-server, however, I have only tested it using Wildfly 8.0
<br>	
<br>	============================
<br>	
<br>	Analyzing database trace results:
<br>	
<br>	
<br>	When analyzing a SQL-Server database trace, the trace will have to be stored in a table for ease of retrieval.
<br>	
<br>	(	
<br>	Disclaimer: It is inadvisable to write the results of a MSSQL trace back to the database on which the trace is running.
<br>		Best case scenario, you'll get a non-characteristic sample of your database transactions.
<br>		Worst case scenario, you crash your server :)
<br>	)
<br>	
<br>	To analyze your trace data, you will need to specify a database connection in your standalone.xml.
<br>	This connection should point to the database where you have stored your MSSQL trace.
<br>	
<br>	!!!!! The project expects the trace data to be located in "resesarch.dbo.full_trace", but this may be changed directly to wherever your trace data is stored.
<br>	
<br>	
<br>	----------------------------------------------------------------------------------------------------------------------
<br>	
<br>	You must define a configuration table using the following SQL script
<br>	
<br>	You will have to replace "research" with the name of the database that holds your trace data
<br>	
<br>	----------------------------------------------------------------------------------------------------------------------
<br>	
<br>	

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

<br>	----------------------------------------------------------------------------------------------------------------------
<br>	
<br>	When the application deploys, it will start analyzing the trace automatically, using the values from your config table.
<br>	
<br>	
<br>	
<br>	============================
<br>	
<br>	Background:
<br>	
<br>	
<br>	This project sprang forth from annecdotal evidence of unexpected contention between transactions in our SQL-Server database.
<br>	
<br>	I desired a tool which could provide quantitative observations on the degree to which pairs of transactions affect each other.
<br>	
<br>	Although I quickly identified the root cause of the database contention, the idea for such a tool stuck.
<br>	
<br>	Eventually an opportunity to implement my ideas presented itself: the final-project for a graduate-level course in datamining at Case Western Reserve University.
<br>	
<br>	After the course ended, I rewrote the application using insights gained from the feedback I received.
<br>	
<br>	============================
