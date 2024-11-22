#### Processes Involved in the system

- database query
- crud of appointments
  - logins, its types, and its details
  - forms and their validation
- exits
- searches and views based on doctors or patients
- view of appointments of specified day with the default being the current day
- generate bills
- crud of patient history
- crud of doctor history

#### IO

- Login to both the admin accounts and the regular staff accounts
  - whether the login was successful
- After login, user should be able to see the appointments for the given day, ( maybe tie the amount of unique 'doctor' logins for the day to be counted and shown on the home screen as "Doctors available" )
- staff should be able to create an appointment, (idk how the doctors will link themselves to said appointment), with details provided by the customer, and the date set for the appointment
- doctors i assume should have admin access to update details, but idk about deleting
- generation of the bill with necessary details
- appropriate imformation for all views requested
- exit the application

### Database Thoughts

| (UniqueIncID)PK | PatientID | DoctorID |  Date   | Time  |         Reason         |
| :-------------: | :-------: | :------: | :-----: | :---: | :--------------------: |
|        1        |    23     |    45    | 5/5/25  | 15:00 |        Checkup         |
|        2        |    56     |    12    | 5/7/25  | 10:00 |    Annual Physical     |
|        3        |    89     |    33    | 5/9/25  | 14:30 | Follow-up Consultation |
|        4        |    41     |    67    | 5/12/25 | 11:00 |      Vaccination       |

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
