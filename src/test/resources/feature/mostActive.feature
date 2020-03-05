Feature: fetch most active owner
  Scenario: client makes call to GET /mostActive
      When the client calls /mostActive
      Then the client receives status code of 200 with owner Olivia Jayden and count 4
