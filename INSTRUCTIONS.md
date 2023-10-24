# Booking API Part 2

**Overview**: This project builds off last the assignment where you designed the GET endpoints of the Booking API. You will continue developing the API by creating endpoints to create, update, and delete Hotels, Rooms, and Users.

**Submission Instructions**:
Once you've completed the required user stories for your project, take the following steps to get your project ready to
submit.

1. Push Code to Github Repository
    - If this week's assignment is starting a new project, you will use the empty project provided. However, if the week's assignment is a continuation of the last week's project, you will NOT create a new project. Copy the project files into the new repository folder and make an initial commit.

2. Add to the a README Template
    - Every submission must be accompanied by a README.md using the readme template provided with the assignment to
      demonstrate which required and optional tasks you've completed.
    - In your project repository, add a README.md file in the root directory that contains the contents of the README
      template for that assignment.
    - If the assignment is a continuation of the same project from a previous week, add the new template to the end of
      the previous README instead of creating a new file.
    - Make sure to check off the user stories you've completed.

3. GIF Walkthrough
    - Your README.md must contain a GIF walkthrough using a recording program of your choice. It should demonstrating
      how it works with the user stories completed.
    - We recommend [Kap](https://getkap.co/) for macOS, [ScreenToGif](https://www.screentogif.com/) for Windows,
      and [peek](https://github.com/phw/peek) for Linux.
    - [Imgur](https://imgur.com/upload) is a great service for hosting the GIF walkthrough and then linking to it from
      the README.
    - When using Imgur, you can right-click on the gif and choose "Copy Image Address" to get the correct address. Make
      sure the address has a `.gif` extension. If you end up with a url that has a `.gifv` extension, removing the `v`
      and changing this to `.gif` will ensure the gif renders on GitHub.

4. Make sure you've pushed all your latest code up to GitHub
    - To check this, you can browse your repository on GitHub on your favorite browser to make sure some of your latest
      changes are present there.

 
 ### Required Stories

- Create an endpoint for each of the major classes (Hotel, Room, User) ***(9 points)***
    - Create the required models, controllers, and services
    - Each controller should have 3 methods:
        - Create a new entry
		- Update an existing entry
		- Delete an existing entry
- Document endpoints. ***(1 point)***
    - Add to the README file the URI for each endpoint and a description (see example below)
	- This should include **ALL** endpoints, including those from last week. *Note that there is an extra column*

#### Example
All URIs start with: `http://localhost:8080/api/v1`

|Action|Network|Description| 
|---|---|---| 
|`GET`|`/product`| Retrieves all products| 
|`GET`|`/product/{id}`| Retrieves a specific product based on it's ID|
|`POST`|`/product`| Creates a new product|
|`PUT`|`/product/{id}`| Updates a product based on it's ID|
|`DELETE`|`/product/{id}`| Deletes a product based on it's ID|

### Stretch Stories

1. Create an endpoint that allows the user to find all hotels with a room available on a specific date. Accept the date as a query parameter ***(3 points)***
2. Create an Reservation collection. *Note: ReservationDetail is not a collection* ***(3 points)***

|field|type|
|---|---|
|customer| User reference|
|hotel| Hotel reference|
|checkIn| Timestamp|
|checkOut|Timestamp|
|total|double|
|rooms|ArrayList<ReservationDetail>|
	
*ReservationDetail*
|field|type|
|---|---|
|room|RoomNumber|
|adults|integer|
|children|integer|
|pricePerNight|double|
	
3. Create an endpoint thhat allows the user to create a reservation. ***(2 points)***
    - Add this end point to the table if you create it.


