# UAT report

Within the scope of User Acceptance Testing I performed on ABB mobile application for Android, various test cases were executed on the following functions:
- Registration
- OTP process
- Login
- Password change
- PIN reset
- Payment to mobile operator

"PIN (access code of application) change" was not tested in this UAT test because screen PIN is also the password used to login to the account. There is no distinction between screen password and account password.

#### Summary of results
![Test results](https://github.com/vuusale/bhos-qa-labs/blob/feature/lab18/test_results.PNG)

Out of the 14 UAT tests, 9 of them were successfully executed, with 2 failed cases. Password reset test was blocked due to missing password reset feature on the frontend application. Mobile payment tests were not finished too since requests did not receive any response from the backend server. 

#### Application specifications
- Name: ABB - Mobile
- Release: 3.16.1
