# Android Final Project

## Document Directory

`android-back-end` is the back-end project，`CampusInformationPlatform` is the Android project，`android.sql` is the database file, and `Document` is the document of the project.

## Experience Address

APP download address:https://image.hihia.top/My Tongtong

API document:https://www.hihia.top/android-back-end-api/swagger-ui/index.html

Back end BaseUrl:https://www.hihia.top/android-back-end-api

## Project Introduction

The project adopts a development model that separates the front and back ends. The Android sends Http requests to the back end, the back end returns Json data, and the front and back ends agree on the corresponding API interface for development.

The back end of the project and the database are deployed on the cloud server, using Nginx as the reverse proxy web server.

![image-20201219233853353](https://image.hihia.top/Screenshot/image-20201219233853353.png)

## Project Architecture

### Front-End

Android+OkHttp, an application with Activity and Fragment as the main body

### Back-End

Springboot+Mybatis+Druid+Swagger3+log4j

Druid：https://www.hihia.top/android-back-end-api/druid/index.html

### Web Server

Nginx configuration is as follows:

<img src="https://image.hihia.top/Screenshot/image-20201220200313440.png" alt="image-20201220200313440" style="zoom:50%;" />

<img src="https://image.hihia.top/Screenshot/image-20201220200236054.png" alt="image-20201220200236054" style="zoom:50%;" />

### Database

Database construction using docker:

![image-20201220200613795](https://image.hihia.top/Screenshot/image-20201220200613795.png)
