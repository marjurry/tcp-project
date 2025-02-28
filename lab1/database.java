// сущность клиента, с зависимостями и необходимым для hibernate. Каждую сущность в отдельный интерфейс. Методы допишем в пункте 4
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ClientGroup> clientGroups;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Task> tasks;

    // Геттеры и сеттеры
}














// Сущность группы

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ClientGroup> clientGroups;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Task> tasks;

    // Геттеры и сеттеры
}











// сущность клиента в группе
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "client_groups")
public class ClientGroup {

    @EmbeddedId
    private ClientGroupId id;

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    // Геттеры и сеттеры
}

@Embeddable
class ClientGroupId implements Serializable {

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "group_id")
    private Long groupId;

    // Геттеры и сеттеры, equals, hashCode
}










// сущность задания
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {

    @EmbeddedId
    private TaskId id;

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "task_date", nullable = false)
    private Date taskDate;

    @ManyToOne
    @JoinColumn(name = "responsibility_id", nullable = false)
    private Responsibility responsibility;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Client author;

    // Геттеры и сеттеры
}

@Embeddable
class TaskId implements Serializable {

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "task_date")
    private Date taskDate;

    // Геттеры и сеттеры, equals, hashCode
}









// сущность обязанности 
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "responsibilities")
public class Responsibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "responsibility_id")
    private Long responsibilityId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "responsibility", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Task> tasks;

    // Геттеры и сеттеры
}











// это для работы с БД, понадобится в пункте 3
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        // Создание SessionFactory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        // Открытие сессии
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Создание и сохранение сущности
        Client client = new Client();
        client.setUsername("john_doe");
        client.setEmail("john@example.com");
        client.setPassword("password123");

        session.save(client);

        // Завершение транзакции
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}








// ЭТО ПУНКТ 3. Модель пользователя нашего:
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name")
    private String name;

    // Другие поля и связи
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Task> tasks;

    // Геттеры и сеттеры
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
