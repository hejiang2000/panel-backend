package pl.memleak.panel.bll.dao;

import pl.memleak.panel.bll.dto.Group;
import pl.memleak.panel.bll.dto.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by maxmati on 11/29/16
 */
public interface ILdapDao {
    User getUser(String username);

    User getUser(String baseDn, String username);

    Group getGroup(String groupname);

    Group getGroup(String baseDn, String groupname);

    List<User> getAllUsers();

    List<User> getAllUsers(String baseDn);

    void createUser(User user, String realm);

    void createGroup(Group group);

    void deleteUser(String username);

    void editUser(User user);

    List<Group> getAllGroups();

    List<Group> getAllGroups(String baseDn);

    boolean authenticate(String username, String password);

    void deleteGroup(String groupname);

    void editGroup(Group group);

    void addToGroup(String groupname, String username);

    void removeFromGroup(String groupname, String username);

    List<Group> getUserGroups(String username);

    User getUserByDn(String dn);

    String getUserDn(String username);
}
