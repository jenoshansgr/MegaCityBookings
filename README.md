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


# Database
````
# create user to access the database
# CREATE USER 'megaCityAdmin'@'localhost' IDENTIFIED BY 'mega456city';
# GRANT ALL PRIVILEGES ON megaCityBookings.* TO 'megaCityAdmin'@'localhost';
# FLUSH PRIVILEGES;
# create user to access the database

# CREATE DATABASE megaCityBookings;
# USE megaCityBookings;


CREATE TABLE CabType
(
id          INT AUTO_INCREMENT PRIMARY KEY,
name        VARCHAR(50)    NOT NULL,
pricePerDay DECIMAL(10, 2) NOT NULL,
pricePerKm  DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Cab
(
id        INT AUTO_INCREMENT PRIMARY KEY,
cabTypeId INT                      NOT NULL,
model     VARCHAR(50)              NOT NULL,
number    VARCHAR(20) UNIQUE       NOT NULL,
status    ENUM ('available', 'na') NOT NULL,
FOREIGN KEY (cabTypeId) REFERENCES CabType (id) ON DELETE CASCADE
);

CREATE TABLE Customer
(
id          INT AUTO_INCREMENT PRIMARY KEY,
firstName   VARCHAR(50)         NOT NULL,
lastName    VARCHAR(50)         NOT NULL,
email       VARCHAR(100) UNIQUE NOT NULL,
address     VARCHAR(250)        NOT NULL,
phoneNumber VARCHAR(15) UNIQUE  NOT NULL
);

CREATE TABLE Driver
(
id                INT AUTO_INCREMENT PRIMARY KEY,
firstName         VARCHAR(50)              NOT NULL,
lastName          VARCHAR(50)              NOT NULL,
licenseNo         VARCHAR(50) UNIQUE       NOT NULL,
licenseExpireDate DATE                     NOT NULL,
status            ENUM ('available', 'na') NOT NULL
);

CREATE TABLE Booking
(
id          INT AUTO_INCREMENT PRIMARY KEY,
destination VARCHAR(150)       NOT NULL,
orderNo     VARCHAR(50) UNIQUE NOT NULL,
orderDate   DATE               NOT NULL,
tripDate    DATE               NOT NULL,
noOfDays    INT                NULL,
distanceKm  DECIMAL(10, 2)     NULL,
cabId       INT                NOT NULL,
driverId    INT                NOT NULL,
pricePerDay DECIMAL(10, 2)     NULL,
pricePerKm  DECIMAL(10, 2)     NULL,
FOREIGN KEY (cabId) REFERENCES Cab (id) ON DELETE CASCADE,
FOREIGN KEY (driverId) REFERENCES Driver (id) ON DELETE CASCADE
);

CREATE TABLE CustomerBooking
(
customerId INT NOT NULL,
bookingId  INT NOT NULL,
PRIMARY KEY (customerId, bookingId),
FOREIGN KEY (customerId) REFERENCES Customer (id) ON DELETE CASCADE,
FOREIGN KEY (bookingId) REFERENCES Booking (id) ON DELETE CASCADE
);

CREATE TABLE User
(
id          INT AUTO_INCREMENT PRIMARY KEY,
username    VARCHAR(50) UNIQUE         NOT NULL,
password    VARCHAR(32)                NOT NULL, -- MD5 Hash
role        ENUM ('admin', 'customer') NOT NULL,
email       VARCHAR(100) UNIQUE        NOT NULL,
createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO CabType (name,pricePerDay,pricePerKm) VALUE
('Normal', 1500,100),('Luxury', 3000,200);
;

INSERT INTO User (username, password, role, email, createdDate) VALUES ('admin', MD5('admin'),'admin', 'admin@megacity.com', CURDATE());
````

