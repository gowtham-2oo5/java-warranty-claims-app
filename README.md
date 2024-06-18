# Warranty Claims Application

## Prerequisites

1. Make sure you have JDK installed on your system.
2. Make sure you also have JRE installed.
3. Install Oracle Database on your system:
   - Download it for Windows from [here](https://download.oracle.com/otn-pub/otn_software/db-express/OracleXE213_Win64.zip).

4. Download Oracle SQL Developer:
   - Download it from [here](https://www.oracle.com/database/sqldeveloper/technologies/download/#license-lightbox).


## Steps to Set Up and Run the Project

### 1. Configure Your Database

- Install the Oracle Database and Oracle SQL Developer on your system following the provided links.
- Configure your database as needed.

### 2. Update Configuration Properties

- Navigate to `warranty-claims/src/main/java/configs/config.properties`.
- Edit the property variables in the `config.properties` file as necessary.

### 3. Set Up the Database Schema

- Open SQL Developer on your system.
- Run the following scripts to set up the database schema:

```sql
-- Creating warranty table
CREATE TABLE WARRANTY (
    PRODUCT_ID NUMBER(8) NOT NULL,
    SERIAL_NUMBER VARCHAR2(16) NOT NULL,
    WARRANTY_NUMBER NUMBER(8) NOT NULL,
    DATE_OPENED DATE,
    EXPIRY_DATE DATE,
    CONSTRAINT WARRANTY_PK PRIMARY KEY (PRODUCT_ID, SERIAL_NUMBER)
);

-- Inserting sample data into Warranty table
INSERT INTO WARRANTY (PRODUCT_ID, SERIAL_NUMBER, WARRANTY_NUMBER, DATE_OPENED, EXPIRY_DATE) VALUES (10000001, 'SN1000000001', 20000001, TO_DATE('01-Jan-23', 'DD-Mon-YY'), TO_DATE('01-Jan-24', 'DD-Mon-YY'));
INSERT INTO WARRANTY (PRODUCT_ID, SERIAL_NUMBER, WARRANTY_NUMBER, DATE_OPENED, EXPIRY_DATE) VALUES (10000002, 'SN1000000002', 20000002, TO_DATE('01-Feb-23', 'DD-Mon-YY'), TO_DATE('01-Feb-24', 'DD-Mon-YY'));
INSERT INTO WARRANTY (PRODUCT_ID, SERIAL_NUMBER, WARRANTY_NUMBER, DATE_OPENED, EXPIRY_DATE) VALUES (10000003, 'SN1000000003', 20000003, TO_DATE('01-Mar-23', 'DD-Mon-YY'), TO_DATE('01-Mar-24', 'DD-Mon-YY'));
INSERT INTO WARRANTY (PRODUCT_ID, SERIAL_NUMBER, WARRANTY_NUMBER, DATE_OPENED, EXPIRY_DATE) VALUES (10000004, 'SN1000000004', 20000004, TO_DATE('01-Apr-23', 'DD-Mon-YY'), TO_DATE('01-Apr-24', 'DD-Mon-YY'));
INSERT INTO WARRANTY (PRODUCT_ID, SERIAL_NUMBER, WARRANTY_NUMBER, DATE_OPENED, EXPIRY_DATE) VALUES (10000005, 'SN1000000005', 20000005, TO_DATE('01-May-23', 'DD-Mon-YY'), TO_DATE('01-May-24', 'DD-Mon-YY'));
INSERT INTO WARRANTY (PRODUCT_ID, SERIAL_NUMBER, WARRANTY_NUMBER, DATE_OPENED, EXPIRY_DATE) VALUES (10000006, 'SN1000000006', 20000006, TO_DATE('01-Jun-23', 'DD-Mon-YY'), TO_DATE('01-Jun-24', 'DD-Mon-YY'));
INSERT INTO WARRANTY (PRODUCT_ID, SERIAL_NUMBER, WARRANTY_NUMBER, DATE_OPENED, EXPIRY_DATE) VALUES (10000007, 'SN1000000007', 20000007, TO_DATE('01-Jul-23', 'DD-Mon-YY'), TO_DATE('01-Jul-24', 'DD-Mon-YY'));
INSERT INTO WARRANTY (PRODUCT_ID, SERIAL_NUMBER, WARRANTY_NUMBER, DATE_OPENED, EXPIRY_DATE) VALUES (10000008, 'SN1000000008', 20000008, TO_DATE('01-Aug-23', 'DD-Mon-YY'), TO_DATE('01-Aug-24', 'DD-Mon-YY'));
INSERT INTO WARRANTY (PRODUCT_ID, SERIAL_NUMBER, WARRANTY_NUMBER, DATE_OPENED, EXPIRY_DATE) VALUES (10000009, 'SN1000000009', 20000009, TO_DATE('01-Sep-23', 'DD-Mon-YY'), TO_DATE('01-Sep-24', 'DD-Mon-YY'));
INSERT INTO WARRANTY (PRODUCT_ID, SERIAL_NUMBER, WARRANTY_NUMBER, DATE_OPENED, EXPIRY_DATE) VALUES (10000010, 'SN1000000010', 20000010, TO_DATE('01-Oct-23', 'DD-Mon-YY'), TO_DATE('01-Oct-24', 'DD-Mon-YY'));

-- Creating Claim table
CREATE TABLE CLAIM (
    CLAIM_ID NUMBER(8,0) NOT NULL,
    CUSTOMER_ID NUMBER(8,0) NOT NULL,
    CUSTOMER_FIRSTNAME VARCHAR2(30) NOT NULL,
    CUSTOMER_LASTNAME VARCHAR2(30) NOT NULL,
    CUSTOMER_EMAIL VARCHAR2(64) NOT NULL,
    PRODUCT_ID NUMBER(8,0) NOT NULL,
    PRODUCT_NAME VARCHAR2(30) NOT NULL,
    SERIAL_NUMBER VARCHAR2(25) NOT NULL,
    WARRANTY_NUMBER NUMBER(8,0) NOT NULL,
    COUNTRY_CODE VARCHAR2(3),
    COUNTRY_REGION VARCHAR2(32),
    CLAIM_DATE DATE,
    STATUS VARCHAR2(15),
    SUBJECT VARCHAR2(64),
    SUMMARY VARCHAR2(256),
    CONSTRAINT CLAIM_PK PRIMARY KEY (CLAIM_ID)
);

-- Creating sequence for CLAIM_ID in Claim table
CREATE SEQUENCE CLAIM_SEQ
START WITH 50
INCREMENT BY 1;
```
### Program Flow
1. Read the claims data from the `inpData.csv` file.
2. Filter out claims with invalid formats and log them into the `invalidClaimFormat.log` file.
3. Validate the format of the remaining claims and check them against the existing warranty table in the database.
4. Filter out claims with invalid or expired warranties, and log them into the `invalidWarranty.log` file.
5. Retrieve country-related information for each valid claim from a REST API and retrieve warranty-related information from the database.
6. Process the valid claims, incorporating country-related and warranty-related information.
7. Insert the new claims into the database.
