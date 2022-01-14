# ping-service

This is a small utility app which calls services deployed in cloud to prevent them from sleeping/idling.

Google Cloud Platform offers 3 scheduled jobs per account each month. 
With this service, we can create one job for `/tests/ping` and that will trigger ping calls to all other services.
