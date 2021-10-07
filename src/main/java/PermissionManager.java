public class PermissionManager {
    private PermissionLevel mCurrentLevel = PermissionLevel.USER;

    public String getmCurrentLevel() {
        return mCurrentLevel.name().substring(0, 1).toUpperCase() + mCurrentLevel.name().substring(1).toLowerCase();
    }

    public void setmCurrentLevel(String level) {
        this.mCurrentLevel = PermissionLevel.valueOf(level.toUpperCase());
    }

    public static void main(String[] args) {
        PermissionManager permissionManager = new PermissionManager();
        permissionManager.setmCurrentLevel("Admin");
        System.out.println(permissionManager.getmCurrentLevel());
}

}

