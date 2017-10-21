Feature: Another Simple feature

  Background: I ate some cukes in the background
    Given I have 10 cukes in my belly

  Scenario: Another Simple scenario in another simple feature
    Given I have 8 cukes in my belly

  Scenario Outline: An outline with examples where we are loading <number> cukes
    Given I have <number> cukes in my belly
    Examples:
    | number |
    | 2      |
    | 3      |