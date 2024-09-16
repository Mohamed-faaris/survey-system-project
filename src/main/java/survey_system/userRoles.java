package survey_system;

public enum userRoles {
    DEV(0,"full access"),
    ADMIN(1,"most access"),
    USER(2,"use access");

    public final int permissionLevel;
    public final String accessDescription;
    userRoles(int permissionLevel,String accessDescription){
       this.permissionLevel = permissionLevel;
       this.accessDescription = accessDescription;
    }
}