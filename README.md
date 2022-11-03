# Fetch Take Home

## Assignment Details

### Background

Our users have points in their accounts. Users only see a single balance in their accounts. But for reporting purposes we actually track their points per payer/partner. In our system, each transaction record contains: payer (string), points (integer), timestamp (date).
For earning points it is easy to assign a payer, we know which actions earned the points. And thus which partner should be paying for the points. When a user spends points, they don't know or care which payer the points come from. But, our accounting team does care how the points are spent.

### Objective
Write a web service that accepts HTTP requests and returns responses based on the following conditions:

- We want the oldest points to be spent first (oldest based on transaction timestamp, not the order they’re received)
- We want no payer's points to go negative.

Provide routes that:
- Add transactions for a specific payer and date.
- Spend points using the rules above and return a list of { "payer": <string>, "points": <integer> } for each call.
-  Return all payer point balances.


## Configuring the project

### 1. Download the Postman app
Link: https://www.postman.com/

This is what we will use to test the API. You will need to create an account if you do not have one.

### 2. Create workspace in postman
Open the app and press workspace in the top left. Click create a new workspace.

### 3. Clone repo locally
```
git clone https://github.com/jcksnm/fetch-take-home.git
```

### 4. Run the project
Navigate to root directory

  ```
cd /my/path/to/fetch-take-home
  ```
Run with this command
  ```
./mvnw spring-boot:run
  ```


## Testing the project
Now that the project is running, we can start testing it. Navigate back to the postman workplace you created, so we can begin making the requests.

### add route
This route allows us to add a payer transaction.
1. Open a tab in your postman workspace. In the URL bar, paste:
```
http://localhost:5000/add
```
2. To the left of the URL bar is a drop-down menu. Click it and ensure that "POST" is selected.
3. Under the URL bar, click "Body." Under that, select "Raw." Then, a drop-down menu will appear to the right that says "Text." Change this to "JSON."
4. In the text area below, paste:

```
{ "payer": "DANNON", "points": 300, "timestamp": "2022-10-31T10:00:00Z" }
```
   
6. Press send. 

The response should be:
```
{
    "payer": "DANNON",
    "points": 300,
    "timestamp": "2022-10-31T10:00:00"
}
```

### addAll Route
This route allows us to add multiple payer transactions at once. 
1. Open a new tab. In the URL bar, paste:
  ```
http://localhost:5000/addAll
  ```
2. Ensure that "POST" is selected in the drop-down menu to the left. 
2. In the text area, paste:

```
[{ "payer": "UNILEVER", "points": 200, "timestamp": "2022-10-31T11:00:00Z" }, { "payer": "DANNON", "points": -200, "timestamp": "2022-10-31T15:00:00Z" },{ "payer": "MILLER COORS", "points": 10000, "timestamp": "2022-11-01T14:00:00Z" }, { "payer": "DANNON", "points": 1000, "timestamp": "2022-11-02T14:00:00Z" }]
```
3. Press send.

The response should be:
```
[
    {
        "payer": "DANNON",
        "points": 1000,
        "timestamp": "2022-11-02T14:00:00"
    },
    {
        "payer": "MILLER COORS",
        "points": 10000,
        "timestamp": "2022-11-01T14:00:00"
    },
    {
        "payer": "DANNON",
        "points": -200,
        "timestamp": "2022-10-31T15:00:00"
    },
    {
        "payer": "UNILEVER",
        "points": 200,
        "timestamp": "2022-10-31T11:00:00"
    }
]
```

### points route
This route allows you to see all payer point balances. 
1. Open a new tab. In the URL bar, paste:

```
http://localhost:5000/points
```

2. Ensure that "GET" is selected in the drop-down menu to the left.
3. Press send.

The response should be:
```
{
    "DANNON": 100,
    "MILLER COORS": 10000,
    "UNILEVER": 200
}
```

### spend route
This route spends points according to the rules outlined in the objective.
1. Open a new tab. In the URL bar, paste:
```
http://localhost:5000/spend
```
2. Ensure that "POST" is selected.
3. In the text area, paste:
```
{ "points": 2000 }
```
4. Press send.

The response should be:
```
{
    "UNILEVER": -200,
    "MILLER COORS": -1700,
    "DANNON": -100
}
```

After you test this, you can rerun the points route to see the updated values. The response should be:
```
{
"DANNON": 0,
"MILLER COORS": 8300,
"UNILEVER": 0
}
```
