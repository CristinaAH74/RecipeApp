# RecipeApp
Project structure:
- Android Gradle: 7.1.2
- SDK compile version: 32
- Java development version: 1.8
- Firebase database

RecipeApp displays recipes for authenticated users by gathering data from 2 APIs.
The main API is EDAMAM Recipe Search API ( https://developer.edamam.com/edamam-recipe-api ) suitable for searches. The secondary API is TheMealDB (https://themealdb.com/) to organize and display recipes into categories and lists.

Activities:
- Login
- Register
- Home
- Category (containing fragment)
- Detail
- Profile
- Settings

Functionalities:
- verifying if there's a logged in user when the app launches
- register all data in Firebase
- verify login data exists in dataase and return error if not

Screenshots:
![image](https://user-images.githubusercontent.com/107887625/213002837-74a93253-ed3b-4c5b-b395-f3c0fdd82545.png)
![image](https://user-images.githubusercontent.com/107887625/213002948-5d58da7c-e34a-4b0a-9033-94c247637192.png)
![image](https://user-images.githubusercontent.com/107887625/213003033-434e63b0-78c4-4986-88ab-56d18850edf0.png)
![image](https://user-images.githubusercontent.com/107887625/213003292-689ff0d2-8562-49e5-b86c-4a6d1c4b03d9.png)



