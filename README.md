# Database table plan

Customer:
- id - PK
- firstName
- lastName
- email
- address
- phoneNumber

CustomerBooking
- customerId - FK (Customer - id)
- bookingId- FK (Booking - id)

Booking
- id
- destination
- orderNo
- orderDate
- tripDate
- noOfDays
- distanceKm
- cabId - FK (Cab - id)
- driverId - FK (Driver - id)
- pricePerDay
- pricePerKm

User
- id - PK
- username
- password (md5)
- role (admin/customer)
- email
- createdDate

Driver
- id - PK
- firsName
- lastName
- licenseNo
- LicenseExpireDate
- status (available/na)

Cab
- id - PK
- cabTypeId - FK (CabType - id)
- model
- number
- status (available/na)

CabType
- id - PK
- name
- pricePerDay
- pricePerKm