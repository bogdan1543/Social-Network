package service;

import domain.Friendship;
import domain.User;
import domain.validator.ValidationException;
import repository.InMemoryRepository;

import java.util.*;

public class SocialNetwork {
    private InMemoryRepository<Integer, User> userRepository;
    private InMemoryRepository<Integer, Friendship> friendshipRepository;

    public SocialNetwork(InMemoryRepository<Integer, User> userRepository, InMemoryRepository<Integer, Friendship> friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public Integer getNewUserId(){
        Set<Integer> existingIds = new HashSet<>();
        for (User user : userRepository.findAll()) {
            existingIds.add(user.getId());
        }

        Random random = new Random();
        Integer newId;
        do {
            newId = random.nextInt(10000);
        } while (existingIds.contains(newId));

        return newId;
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user){
        user.setId(getNewUserId());
        userRepository.save(user);
    }

    public User removeUser(Integer id){
        try {
            User u = userRepository.findOne(id).orElseThrow(() -> new ValidationException("User doesn't exist!"));
            Vector<Integer> toDelete = new Vector<>();
            getFriendships().forEach(friendship -> {
                if (friendship.getIdUser2().equals(id) || friendship.getIdUser1().equals(id)) {
                    toDelete.add(friendship.getId());
                }
            });
            toDelete.forEach(friendshipRepository::delete);
            User user = userRepository.delete(id).orElseThrow(() -> new ValidationException("User doesn't exist!"));
            u.getFriends().forEach(friend -> friend.removeFriend(u));
            return user;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid user! ");
        } catch (ValidationException v) {
            System.out.println();
        }
        return null;
    }


    public Integer getNewFriendshipId(){
        Set<Integer> existingIds = new HashSet<>();
        friendshipRepository.findAll().forEach(friendship -> {
            existingIds.add(friendship.getId());
        });

        Random random = new Random();
        Integer newId;
        do {
            newId = random.nextInt(10000);
        } while (existingIds.contains(newId));

        return newId;
    }

    public Iterable<Friendship> getFriendships() {
        return friendshipRepository.findAll();
    }
    public void addFriendship(Friendship friendship){
        User user1 = userRepository.findOne(friendship.getIdUser1()).orElseThrow(() -> new ValidationException("User doesn't exist!"));
        User user2 = userRepository.findOne(friendship.getIdUser2()).orElseThrow(() -> new ValidationException("User doesn't exist!"));

        if (getFriendships() != null) {
            getFriendships().forEach(f -> {
                if (f.getIdUser1().equals(friendship.getIdUser1()) && f.getIdUser2().equals(friendship.getIdUser2())) {
                    throw new ValidationException("The friendship already exist! ");
                }
            });
            if (userRepository.findOne(friendship.getIdUser1()).isEmpty() || userRepository.findOne(friendship.getIdUser2()).isEmpty()) {
                throw new ValidationException("User doesn't exist! ");
            }
            if (friendship.getIdUser1().equals(friendship.getIdUser2()))
                throw new ValidationException("IDs can't be the same!!! ");
        }
        friendship.setId(getNewFriendshipId());
        friendshipRepository.save(friendship);

        user1.addFriend(user2);
        user2.addFriend(user1);

    }

    public void removeFriendship(Integer id1, Integer id2){
        User user1 = userRepository.findOne(id1).orElseThrow(() -> new ValidationException("User doesn't exist!"));;
        User user2 = userRepository.findOne(id2).orElseThrow(() -> new ValidationException("User doesn't exist!"));;

        Integer frId = 0;
        for (Friendship friendship : friendshipRepository.findAll()) {
            if (Objects.equals(friendship.getIdUser1(), id1) && Objects.equals(friendship.getIdUser2(), id2)){
                frId = friendship.getId();
            }else if (Objects.equals(friendship.getIdUser1(), id2) && Objects.equals(friendship.getIdUser2(), id1)){
                frId = friendship.getId();
            }else{
                return;
            }
        }

        user1.removeFriend(user2);
        user2.removeFriend(user1);

        friendshipRepository.delete(frId).orElseThrow(() -> new ValidationException("Friendship doesn't exist!"));
    }

    public User findUser(Integer id) {
        return userRepository.findOne(id).orElseThrow(() -> new ValidationException("No user"));
    }
}
