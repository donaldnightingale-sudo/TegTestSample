Feature: TryOut

  Scenario: Basket the zero valued admit with In app delivery option
    Given I open the Ticketek event page
    Then I Click "Show menu options"
    Then I Click "Sign In / Sign up"
	Then I Update "Email*" to "Donald_Nightingale@hotmail.com"
	Then I Update "Password*" to "XXXXX"
    Then I Click "Sign In"
	Then I sleep for 10 seconds
    Then I Click "7:00 pm"
    Then I Click "Select"
	Then I sleep for 10 seconds
    Then I Click "+"
	Then I sleep for 5 seconds
    Then I Click "In app"
    Then I Click "Next"
	Then I sleep for 10 seconds
    Then I Click "Go to checkout"
    Then I Click "Submit payment"