Feature: fetch photos group by owner
  Scenario: client makes call to GET /photoByOwner
    When the client calls /photoByOwner
    Then the client receives status code of 200 with
    | Sophia Jacob | 3 |
    | Olivia Jayden | 4 |
    | Alexander Madison | 1 |
    | Mia Aiden | 2 |
    | Ella Matthew | 2 |