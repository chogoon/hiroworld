package kr.co.within.hiroworld.data.source.remote;

import com.firebase.ui.auth.ui.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kr.co.within.hiroworld.data.model.BuildingData;

public class BuildingService {
    private User user;
    private DatabaseReference databaseRef;

    public BuildingService(User user) {
        this.user = user;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getFriends() {
        return databaseRef.child("friends").child(user.getName());
    }

    // TODO
    public void getBuilding(String name) {

    }

    public void setBuilding(BuildingData data) {
        String imagePath = data.imagePath;
        if(imagePath == null) {
            data.imagePath = "NOT";
        }
//        databaseRef.child("building").child(user.getUsername()).child(friend.getUsername())
//                .setValue(friend);
    }

//    // TODO
//    public void updateFriend(Friend friend) {
//
//    }
//
//    // TODO
//    public void deleteFriend(String username) {
//
//    }
}
