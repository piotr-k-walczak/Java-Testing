Feature: Send messages from file
  We want to send messages from file

  Scenario: File doesn't exist
    Given file does not exist
    When I want to send messages from file
    Then "NoSuchFileException" should be thrown

  Scenario: 2 correct messages are sent
    Given file contains 2 correct messages
    When I want to send messages from file
    Then 2 messages should be sent

  Scenario: File contains incorrect messages
    Given file contains incorrect messages
    When I want to send messages from file
    Then "MissingPlaceholderException" should be thrown