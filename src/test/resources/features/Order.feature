Feature: Product

  Scenario: : Register new Order
    When register a new order
    Then the order is registered successfully
    And must be presented


  Scenario: Find Order
      Given that a order has already been published
      When search for the message
      Then the order is displayed successfully
