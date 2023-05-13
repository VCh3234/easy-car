<h3 align="center">A passionate frontend developer from India</h3>

<h3 align="left">Connect with me:</h3>
<p align="left">
</p>


# Easy car
Pet project.
At the present time available the next endpoints:

User


GET
/users
Get inner user


PUT
/users
Update user


POST
/users
Register new user


DELETE
/users
Delete user


PUT
/users/update-password
Update password

Image


GET
/images/{adId}
Get image


PUT
/images/{adId}
Replace image


POST
/images/{adId}
Post new image


DELETE
/images/{adId}
Delete image

Advertisement


PUT
/ads/{adId}
Update advertisement


DELETE
/ads/{adId}
Delete advertisement


PUT
/ads/up/{adId}
Up advertisement


GET
/ads
Get all public advertisements


POST
/ads
Create new advertisement


POST
/ads/search
Get advertisements by search params


GET
/ads/my-ads
Get advertisements of user


GET
/ads/moderation
Get moderation of user

Admin


PUT
/admin/reject-advertisement
Reject moderation for advertisement


PUT
/admin/accept-advertisement
Accept moderation for advertisement


POST
/admin/add
Register new admin


GET
/admin/payments/{userId}
Get payments of user


GET
/admin/moderation
Get all moderation


GET
/admin/moderation/{userId}
Get moderation by user


GET
/admin/get-user/{userId}
Get inner user


GET
/admin/advertisements
Get advertisements for moderation


GET
/admin/advertisements/{userId}
Get advertisements of user


GET
/admin/advertisement/{adId}
Get inner advertisement


DELETE
/admin/user
Delete user


DELETE
/admin/image/{adId}/{imageUUID}
Delete image


DELETE
/admin/advertisement
Delete advertisement

Verify


POST
/verify/{verifyType}
Get code


GET
/verify/{code}
Send code

Payment


GET
/pays
Get payments of user


POST
/pays
Deposit ups on account


POST
/pays/get-token-for-demonstration
Get token for deposit

JWT auth


POST
/auth/login
Get JWT token for user


POST
/auth/admin/login
Get JWT token for admin
