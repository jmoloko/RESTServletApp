[![Build Status](https://app.travis-ci.com/jmoloko/RESTServletApp.svg?branch=main)](https://app.travis-ci.com/jmoloko/RESTServletApp)
[![CircleCI](https://circleci.com/gh/jmoloko/RESTServletApp/tree/circleci-project-setup.svg?style=svg)](https://circleci.com/gh/jmoloko/RESTServletApp/tree/circleci-project-setup)

### Simple REST application in Java Servlets

This REST API interacts with file storage and provides the ability to access files and download history.

**Entities:**
* User
* Event (File file)
* File
* User -> List <Events>

**Requirements:**
* **CRUD** operations for each entity
* **MVC** approach
* **Maven** was used to build the project
* To interact with the database - **Hibernate**
* For configuring Hibernate - annotations
* Database initialization implemented using **Flyway**
* User interaction implemented using **Postman**
* **Travis** assemblies (https://travis-ci.com/)
* The app is deployed on _heroku.com_

**Technologies:** Java, MySQL, Hibernate, HTTP, Servlets, Maven, Mockito, Flyway, Travis.

**Endpoints:**

Start url - **/api/v1**
* **GET:** _/users_ - show all users
* **GET:** _/files_ - show all files
* **GET:** _/users/{id}_ - show user by id
* **GET:** _/files/{id}_ - show file by id
* **GET:** _/users/{id}/files_ - show files by user id
* **GET:** _/users/{id}/events_ - show events by user id
* **GET:** _/users/{id}/files/{id}/download_ - download file by file id and user id
* **POST:** _/users_ - create new user by name
* **POST:** _/users/{id}/files_ - upload a new file for user
* **PUT:** _/users/{id}_ - rename user by id
* **PUT:** _/users/{id}/files/{id}_ - rename file by id in DB and File System
* **DELETE:** _/users/{id}_ - delete user by id. (all files of this user are deleted on DB and FS)
* **DELETE:** _/users/{id}/files/{id}_ - delete file by id on DB and FS
