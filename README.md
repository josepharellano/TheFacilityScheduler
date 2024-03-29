# The Facility Scheduler

## Scope
Develope an Consultant scheduling program for a global consulting organization that conducts business in multipl languages and has office all over the worl.

## Business Requirements

- Software must be able to determine user's geographical location and automatically choose appropriate language.
- Software must automatically adjust appointment times based on the user's machine location and daylight savings time.
- Users must be able to add, update, and delete customer records in the database.
- Users must be able to add, update, and delete appointments.
- Appointments must link to a specific customer found in the database.
- Users must be able to view appointments via a calendar widget by month or week.
- Upon loging in a User must be alerted if an appointment is upcomming within the next 15 minutes.
- Users must have the ability to generate the following reports: consultant schedule, number of appointment types by month
- Administrators must be able to track user activity by recording timestamps in a user login file.
  
## Design Patterns

### Data Access Object Design Pattern
By using the DAO design pattern the storage layer decoupled from the other componenets of the software.  This allows the storage mechanism to be switched out easily without having to modify the other existing components.

### Singleton Pattern
-  Each service is set up as a singleton to ensure their is only one source of data being manipulated at a given moment.

### Factory Pattern
- ServiceFactory.getService returns a singleton of the requested service.
