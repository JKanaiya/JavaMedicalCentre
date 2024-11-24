#### Processes Involved in the system
- Search
- logins
  - its types, and its details
  - forms and their validation
- ####  crud of appointments
  - Create Appiontments | ( *Access Level = Any* )
  - Update Appiontments | ( *Access Level = Any* ) 
    - Patient id, Doctor id should be immutable
  - Delete Appiontments | ( *Access Level = Any* )  
    - On Completion of Appointment, a Generate Bill Window Should pop up with the details of the appointment(immutable)
- exit
- views based on doctors or patients
- view of appointments of specified day with the default being the current day
- crud of patient history
  - appointments in the table for the specified patient 
  - changing whether appointments have been paid (Access level = all)
- crud of doctor history
  - appointments in the table for the specified doctor
- Register new Users (*Access Level = Admin*)
- Delete Users (*Access Level = Admin*)

#### IO

- Login to both the admin accounts and the regular staff accounts
  - whether the login was successful
- After login, user should be able to see the appointments for the given day 
- After login, doctor should be able to see the appointments for the given day 
- staff should be able to create an appointment, with details provided by the customer, and the date set for the appointment
- generation of the bill with necessary details
- appropriate information for all views requested
- exit the application

### Database Thoughts
#### Appointments 

| (UniqueIncID)PK | PatientID | DoctorID |  Date   | Time  |         Reason         | Paid   |
| :-------------: | :-------: | :------: | :-----: | :---: | :--------------------: |:------:|
|        1        |    23     |    45    | 5/5/25  | 15:00 |        Checkup         | false  |
|        2        |    56     |    12    | 5/7/25  | 10:00 |    Annual Physical     |  true  |
|        3        |    89     |    33    | 5/9/25  | 14:30 | Follow-up Consultation | false  |
|        4        |    41     |    67    | 5/12/25 | 11:00 |      Vaccination       |  true  |

where the views will show appointments based on whether _date == specified date_,

#### Pseudocode

##### _Today / Tomorrow / Searched View_

Begin
If search, query = search date input
_date will be searched in the form 11/06/25_
else, query = today
Try to Connect to Database
Get appointments where Date == `query`.date()
Catch
throw SQLException and ask user to login/ try again
End

#### _New Appointment_

Begin
Present a form in the format
_( userID, doctorName, date, time, summaryReason ) = required_
if ( fields full ),
call the Appointment Constructor with the form
if( DoctorAvailable( id, date, time ) ), _submit form_
else, Doctor not available, input different doctor
else, user cant submit form
End

###### DoctorAvailable()

Begin
takes doctorID, date and time
try to connect to database
if(_doctorID table does not contain the date/time_),
return false
else, return true
End
