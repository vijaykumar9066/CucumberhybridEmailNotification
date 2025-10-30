Feature: Login Feature
  

Scenario Outline: Valid Login
  Given User is navigate to login page
  When User enters valid username "<username>" 
  And User enters valid password "<password>"
  And User clicks on the login button
  Then User should see profile selection page once login success 
  
  Examples:
|username|password|
|stduser |Pixfix@123|
|400104  |Pixfix@123|
|arunvn  |Pixfix@12 |
|shamanth|Pixfix@123|

Scenario: Valid username and invalid password
  Given User is navigate to login page
  When User enters valid username "stduser"
  And User enters Invalid password "Pixfix@12345"
  And User clicks on the login button
  Then User should see an invalid password error message
  
Scenario: InValid username and valid password
  Given User is navigate to login page
  When User enters Invalid username "stduser123"
  And User enters valid password "Pixfix@123"
  And User clicks on the login button
  Then User should see an invalid username error message 
  
Scenario: empty username and password fields
  Given User is navigate to login page
  When User without enters username 
  And User without enters password 
  And User clicks on the login button
  Then User should see an empty field error message 

