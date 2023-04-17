package by.easycar.model.user;

public class UsersMapper {

    public static UserInner getUserInnerFromUserPrivate(UserPrivate userPrivate) {
        UserInner userInner = new UserInner();
        userInner.setEmail(userPrivate.getEmail());
        userInner.setId(userPrivate.getId());
        userInner.setStatus(userPrivate.getStatus());
        userInner.setName(userPrivate.getName());
        userInner.setPhoneNumber(userPrivate.getPhoneNumber());
        return userInner;
    }

    public static UserPublic getUserPublicFromUserPrivate(UserPrivate userPrivate) {
        UserPublic userPublic = new UserPublic();
        userPublic.setEmail(userPrivate.getEmail());
        userPublic.setId(userPrivate.getId());
        userPublic.setName(userPrivate.getName());
        return userPublic;
    }
}
