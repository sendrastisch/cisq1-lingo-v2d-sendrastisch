Feature: Lingo Trainer
  As a player
  I want to guess 5, 6 and 7 letter words
  In order to prepare for Lingo

Scenario: Start a new game
  When I start a new game
  Then the word to guess has to have 5 letters
  And I should see the first letter
  And my score is 0

Scenario Outline: Start a new Round
  Given I am playing a game
  And the round was won
  And the last word had "<previous length>" letters
  When I start a new round
  Then the word to guess has "<next length>" letters

  Examples:
   | previous length | next length |
   | 5               | 6           |
   | 6               | 7           |
   | 7               | 5           |


Scenario Outline: Guessing a word
  Given I am playing a game
  And the word to guess is "<word to guess>"
  When I guess "<attempt>"
  Then I get feedback "<feedback>"

  Examples:
  | word to guess | attempt | feedback                                             |
  | Faith         | Pretty  | INVALID, INVALID, INVALID, INVALID, INVALID, INVALID |
  | Faith         | Which   | ABSENT, ABSENT, CORRECT, ABSENT, CORRECT             |
  | Faith         | Elite   | ABSENT, ABSENT, CORRECT, CORRECT, ABSENT             |
  | Faith         | Earth   | ABSENT, CORRECT, ABSENT, CORRECT, CORRECT            |
  | Faith         | Faith   | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT          |


# Exception paths
Scenario: an eliminated player cannot start a new round
  Given I am playing a game
  And the round was lost
  Then I cannot start a new round

Scenario: a round cannot be started if a player is still playing
  Given I am playing a game
  Then I cannot start a new round

