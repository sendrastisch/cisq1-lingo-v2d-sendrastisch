Feature: Lingo Trainer
  As a player
  I want to guess 5, 6 and 7 letter words
  In order to prepare for Lingo

Scenario: Start a new game
  When I start a new game
  Then the word to guess has to have 5 letters
  And I should see the first letter
  And my score is 0

