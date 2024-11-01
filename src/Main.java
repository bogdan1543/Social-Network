import domain.Friendship;
import domain.User;
import domain.validator.FriendshipValidator;
import domain.validator.UserValidator;
import domain.validator.ValidationException;
import repository.FriendshipFileRepository;
import repository.InMemoryRepository;
import repository.UserFileRepository;
import service.SocialNetwork;
import ui.Console;

public class Main {
    public static void main(String[] args) {
        // Initialize repositories for Users and Friendships


        UserFileRepository repoUser = new UserFileRepository("data/users", new UserValidator());
        FriendshipFileRepository repoFriendship = new FriendshipFileRepository("data/friendships", new FriendshipValidator(repoUser));

        //InMemoryRepository<Integer, User> repoUser = new InMemoryRepository<>(new UserValidator());
        //InMemoryRepository<Integer, Friendship> repoFriendship = new InMemoryRepository<>(new FriendshipValidator(repoUser));

        // Create the social network service
        SocialNetwork socialNetwork = new SocialNetwork(repoUser, repoFriendship);
        Console ui = new Console(socialNetwork);

//        User u1 = new User("Maria", "Ionescu");
//        User u2 = new User("Cristian", "Marin");
//        User u3 = new User("Ion", "Vasilescu");
//        User u4 = new User("Ana", "Stanescu");
//        User u5 = new User("Andrei", "Popescu");
//        User u6 = new User("George", "Petrescu");
//        User u7 = new User("Elena", "Dumitru");

        // Add users to the social network
//        socialNetwork.addUser(u1);
//        socialNetwork.addUser(u2);
//        socialNetwork.addUser(u3);
//        socialNetwork.addUser(u4);
//        socialNetwork.addUser(u5);
//        socialNetwork.addUser(u6);
//        socialNetwork.addUser(u7);

        // Create friendships
//        try {
//            socialNetwork.addFriendship(new Friendship(u1.getId(), u2.getId())); // Andrei <-> Maria
//            socialNetwork.addFriendship(new Friendship(u2.getId(), u3.getId())); // Maria <-> Ion
//            socialNetwork.addFriendship(new Friendship(u3.getId(), u4.getId())); // Ion <-> Elena
//            socialNetwork.addFriendship(new Friendship(u5.getId(), u6.getId())); // George <-> Ana
//            socialNetwork.addFriendship(new Friendship(u6.getId(), u7.getId())); // Ana <-> Cristian
//            socialNetwork.addFriendship(new Friendship(u5.getId(), u7.getId())); // George <-> Cristian
//        } catch (ValidationException e) {
//            System.err.println("Error adding friendship: " + e.getMessage());
//        }

        // Run the user interface
        ui.run();
    }
}