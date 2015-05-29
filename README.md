# ATM Simulator 
Build Status:  <img src="https://travis-ci.org/SahilWadhwa/ATMSimulator.svg?branch=master" alt="Travis CI Status"/>    https://travis-ci.org/SahilWadhwa/ATMSimulator



**To Start ATM**

1. Run Main Function inside Operator class
2. Test data: Card Number = 1234, Pin = 123, Balance = 10000  (ref:account-details.csv)



**Scenarios covered**

1. Card number and Pin number can be authenticated

2. Card Account details are maintained and balance is updated after money is withdrawn

3. Money withdrawn will be retunred in multiple currencies.(Thousand, Five Hundred, Hundred)

4. Money can be withdrawn until ATM cash and Card Account balance goes empty



**Scalability (Scenarios that can be added with ease)**

1. New Currency can be added easily by using DispenseChain Interface

2. How many notes of a Currency can be controlled.

**Scenarios un-covered**

1. Interface is console based and lacks validating user input. Breaks in case of non integer entry
2. User accounts are loaded thru CSV file but ATM internal cash is loaded staticaly
