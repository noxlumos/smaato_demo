# Smaato Demo Application

The service has one GET endpoint - **/api/smaato/accept** which accepts an integer id as a
mandatory query parameter and an optional string HTTP endpoint query parameter. 

It returns String “ok” if there were no errors processing the request and “failed” in case of any errors.

**Design Decisions**

There are 2 layers in the application, controller and service. Controller delegates functionality to service and
service interacts with the file system.

Within service there is a scheduled task which runs every minute to log the unique id counts.
This log file is being created in same directory level with the application (counterLog.txt). 

If endpoint is provided, response status code is being logged to the statusLog.txt file which is also
in same directory level with the application.

IDs are stored in a set because it only keeps unique ids with constant time modification. In scheduled task this set is cleared.

Regarding concurrent access to the endpoint, The mentioned ID set above is made concurrent and also writes to the log files are synchronized.
