# SpringSecurityApp
Spring (MVC, Security, JSP) + Hibernate + MySQL + Maven + Bootstrap + Tomcat

Web application with model:
1. User
2. Role (There're 4 roles: unactivated, user, blocked, admin)
3. Post
4. Topic

This app consists of user authorithation and registration. 
After registration user gets role 'unactivated'. He should confirm the registration by message with activation code, which he'll get by email.
In app user with admin's role can update user's password or block another user.
Moreover, you can add new users, see the list of users and look their profiles.
Users also can create different posts with their own or common topics. If there're no such topics, it's being created a new topic with such topic name.

TODO: fix bugs, edit post, add feachures.
