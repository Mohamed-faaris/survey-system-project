package survey_system;

public enum UserRoles {
    DEV(0,"full access"),
    ADMIN(1,"most access"),
    USER(2,"use access");

    public final int permissionLevel;
    public final String accessDescription;
    UserRoles(int permissionLevel, String accessDescription){
       this.permissionLevel = permissionLevel;
       this.accessDescription = accessDescription;
    }

    public static UserRoles setUserRole(int permissionLevel){
        switch (permissionLevel){
            case 0:return UserRoles.DEV;
            case 1:return UserRoles.ADMIN;
            case 2:
            default:return UserRoles.USER;
        }
    }
}