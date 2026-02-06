package com.pickdate.iam;

record ProblemApiConst() {

    public static final String PROBLEMS_EXAMPLE_JSON = """
            {
              "content": [
                {
                  "id": "c1b0f7b0-1234-4a9a-8b10-aaaa1111bbbb",
                  "title": "Unexpected error",
                  "detail": "NullPointerException at PollService#create",
                  "status": 500,
                  "type": "https://example.com/probs/unexpected-error",
                  "timestamp": "2025-12-25T12:00:00Z"
                },
                {
                  "id": "d2c3a8e1-5678-4f0b-9c21-bbbb2222cccc",
                  "title": "Validation failed",
                  "detail": "Email is invalid",
                  "status": 400,
                  "type": "https://example.com/probs/validation",
                  "timestamp": "2025-12-24T08:30:00Z"
                }
              ],
              "pageable": {
                "sort": { "sorted": true, "unsorted": false, "empty": false },
                "pageNumber": 0,
                "pageSize": 20,
                "offset": 0,
                "paged": true,
                "unpaged": false
              },
              "totalElements": 42,
              "totalPages": 3,
              "last": false,
              "size": 20,
              "number": 0,
              "sort": { "sorted": true, "unsorted": false, "empty": false },
              "first": true,
              "numberOfElements": 20,
              "empty": false
            }
            """;
}
