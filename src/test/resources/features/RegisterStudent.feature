Feature: Register a new Student

  Scenario Outline: As a student i want to see an error if i have problems registering in the app.
    Given I can register as a student
    And The system repeats the id
    Then I should see a error message <error>
    Examples:
      | error               |
      | "Usuario duplicado" |

  Scenario Outline: As a student i want to see an error if the dni is already in use.
    Given I can register as a student
    And I register a repeated tiu
    Then I should see a error message <error>
    Examples:
      |error|
      |"El tiu ya esta en uso"|

  Scenario Outline: As a student i want to see an error if the dni is incorrect.
    Given I can register as a student
    And I register an invalid tiu
    Then  I should see a error message <error>
    Examples:
      | error              |  |
      | "El tiu no existe" |  |

