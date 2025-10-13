# Expass 3 

When starting this assignment, everything went fine and I managed to create and vote on my poll on http://localhost:5173/. 

But when I started step 4, things started to fall apart. When running backend and frontend in seperate terminal, things went smoothly until I tried voting in the polls. This is when the page just turned white. I was able to make the polls, but not vote at them. And after several error searches in both backend and frontend, the issue appeared that I need to make an update on my computer. Since the computer is old, and I have not very much space, the software is outdated compared to what I should have when running my code. Also, I needed to update my VS code by deleting it, and downloading it again. This took me 5 hrs. 

After doing the update and reinstallations, I managed to do the rest of the tasks. 

In my repository the files are kind of messy, due to that I am not very familiar with github and therefor tried to add my files by add the files directly. I was not allowed to add all my files, and therefor understood that I needed to push my project into github. Which I managed at the end. 

# Expass 4 

In this assignment I was kind of confused on the set up of files, but after discussing it with a classmate we found out what was the best solution based on how our files was set up from the previous assignments and integrating that with the file directories stated in expass 4. 

After following the steps in the assignment and checking that everything had its correct imports and package definitions. My biggest issue then occured when trying to run the code, even though making necessary springboot correction in my build.grade.kts, my code was bugged my the fact that springboot-code was in "controllers" etc, and had to comment out everything that had to do with springboot in order to make the code run with JPA. When all sprinboot-related code was commented out, I was able to run my code and the build was succesfull. 

Due to I was not able to run with springboot, I inspected the database tables by running JPA queries inside my test cases, since my project used Hibernate with an in-memory H2 database. The entity mappings automatically generated four tables: users, poll, vote and vote_option. 

My pending issue is that I did not manage to get Springboot fully working. Because of this, I could not use the H2 web console to visually inspect the tables and I had to rely only on thes test queries for verification. Also, that my REST controllers remain commented out and non-functional. 

# Expass 5 
The building and testing of Redis went smoothly and without complications. I installed and ran Redis locally, explored basic CLI commands and connected it to my Java project using the Jedis library. I made the "RedisExperiment.java" file and worked with different daat types to store user and poll data. I also implemented a simple cache (TTL) feature to show how fast data storage and retrievel can be using Redis. 

# Expass 6 
Since I still have issues with Spring Boot, the system was implemented as a standalone Java application to simplify the running and avoid framework dependencies. 

I chose Redis as the message broker since it is lightweight setup and has a easy to use Publish/subscribe mechanism. Each poll is linked to a redis channel with the same name, and when a new poll is created, a corresponding channel is opened automatically. I also tried publishing a message manually, and succesfully sent a "vote" that the application receives and uses to update the poll results. 

Other than the Spring Boot issues - this project went smoothly. 


