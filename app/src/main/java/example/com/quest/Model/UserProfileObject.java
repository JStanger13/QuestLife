package example.com.quest.Model;

/**
 * Created by justinstanger on 3/4/18.
 */

public class UserProfileObject {
    int level, _id, userLevelUp, points;
    String name, password, image;

    public UserProfileObject(int userLevel,
                             String username,
                             String userpassword,
                             String userimage,
                             int levelUp,
                             int userPoints){
        level = userLevel;
        name = username;
        password = userpassword;
        image = userimage;
        userLevelUp = levelUp;
        points = userPoints;
    }

    public UserProfileObject(
                            String username,
                            String userpassword,
                            String userimage){
        level = 0;
        name = username;
        password = userpassword;
        image = userimage;
        userLevelUp = 1;
        points = 0;
    }

    public UserProfileObject() {
    }


    public int getUserLevel() {
        return level;
    }

    public void setUserLevel(int userLevel) {
        this.level = userLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getUserLevelUp() {
        return userLevelUp;
    }

    public void setUserLevelUp(int userLevelUp) {
        this.userLevelUp = userLevelUp;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
