Project Description: Role-Based Access Control (RBAC) System
Overview
This project implements a basic Role-Based Access Control (RBAC) system in Java. It demonstrates core concepts of user authentication, token-based authorization, and access control based on roles. The project provides functionalities for user registration, login, and access validation to specific resources based on their roles.

Key Features
User Registration:

Users can register with a username, password, and role (e.g., ADMIN, USER, MODERATOR).
A limit on the maximum number of users ensures resource constraints are managed.
User Authentication:

Registered users can log in using their credentials (username and password).
Successful login generates a JWT-like token (without signature) for session management.
JWT Token Handling:

A simulated JWT token is generated using Base64 encoding for the header and payload.
The token contains essential user details such as username, role, and expiration timestamp.
Role-Based Access Control:

Users can attempt to access resources requiring specific roles.
Access is granted only if the token is valid and the user has the required role.
Token Validation:

Validates the token's format and expiration timestamp.
Decodes the payload to extract user details for authorization checks.
Technical Stack
Language: Java
Libraries/Utilities:
Base64 for encoding and decoding JWT tokens.
Scanner for user input.
System Design: Object-oriented design with encapsulated User and Service classes.
Functional Workflow
