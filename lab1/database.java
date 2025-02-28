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

@Entity
@Table(name = "days_of_week")
public class DayOfWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_id")
    private Long dayId;

    @Column(name = "day_name", nullable = false)
    private String dayName;

    // Геттеры и сеттеры
}

@Entity
@Table(name = "weeks")
public class Week {

    @EmbeddedId
    private WeekId id;

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    // Геттеры и сеттеры
}

@Embeddable
class WeekId implements Serializable {

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "group_id")
    private Long groupId;

    // Геттеры и сеттеры, equals, hashCode
}

@Entity
@Table(name = "years")
public class Year {

    @EmbeddedId
    private YearId id;

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "year", nullable = false)
    private Integer year;

    // Геттеры и сеттеры
}

@Embeddable
class YearId implements Serializable {

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "group_id")
    private Long groupId;

    // Геттеры и сеттеры, equals, hashCode
}

@Entity
@Table(name = "days")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_id")
    private Long dayId;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;

    // Геттеры и сеттеры
}
