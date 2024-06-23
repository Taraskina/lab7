package org.example.database;


import org.example.entity.Color;
import org.example.entity.Coordinates;
import org.example.entity.Location;
import org.example.entity.Person;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.exceptions.UserIsNotOwnerException;
import org.example.exceptions.WrongPasswordException;
import org.example.main.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;

public class PostgresConnection extends DatabaseConnection {
    private final PasswordManager passwordManager = new PasswordManager();

    protected PostgresConnection(String url, String login, String password) throws SQLException {
        super(url, login, password);
    }

    @Override
    public boolean authenticateUser(String login, String password) throws SQLException, WrongPasswordException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
        ps.setString(1, login);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            String password1 = resultSet.getString("password");
            if (password.equals(password1)) {
                return true;
            }
        }
        throw new WrongPasswordException("Неверный пароль!");
    }

    @Override
    public boolean addUser(String login, String password) throws SQLException {
        if (findUser(login)) {
            throw new UserAlreadyExistsException("Пользователь с таким логином уже существует!");
        }
//        MessageDigest md2 = null;
//        try {
//            md2 = MessageDigest.getInstance("MD2");
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//        byte[] hash = md2.digest(password.getBytes());
//        StringBuilder hexHash = new StringBuilder();
//        for (byte b : hash) {
//            hexHash.append(String.format("%02x", b));
//        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
        ps.setString(1, login);
        ps.setString(2, password);

        return ps.execute();
    }


    private boolean findUser(String login) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("SELECT 1 FROM users WHERE login = ?");
        ps.setString(1, login);
        ResultSet resultSet = ps.executeQuery();
        return resultSet.next();
    }

    @Override
    public int addStudyGroup(String name, Coordinates coordinates, LocalDate date, double height, Integer weight, Color color, Location location) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO collection (" +
                "name, x, y, date, height, weight, color, x, y, z)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setLong(2, coordinates.getX());
        ps.setLong(3, coordinates.getY());
        ps.setDate(4, Date.valueOf(date));
        ps.setDouble(5, height);
        ps.setInt(6, weight);
        ps.setInt(7, color.ordinal() + 1);
        ps.setLong(8, location.getX());
        ps.setLong(9, location.getY());
        ps.setFloat(10, location.getZ());

        int id = -1;
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    return generatedId;
                } else {
                    System.out.println("Id не сгенерирован");
                }
            }
        } else {
            System.out.println("Запись не была вставлена");
        }
        return id;
    }

    @Override
    public boolean updateStudyGroup(Integer id, String name, Coordinates coordinates, LocalDate date, double height, Integer weight, Color color, Location location, String ownerLogin) throws SQLException, UserIsNotOwnerException {
        if (!isStudyGroupOwner(ownerLogin, id)) {
            throw new UserIsNotOwnerException("Вы не являетесь владельцем элемента");
        }
        PreparedStatement ps = this.connection.prepareStatement("UPDATE collection "
                + "SET name = ?, x=?, y=?, date=?, height=?, weight=?, color=?, x=?, y=?, z=?"
                + " WHERE id = ?");
        ps.setString(1, name);
        ps.setLong(2, coordinates.getX());
        ps.setLong(3, coordinates.getY());
        ps.setDate(4, Date.valueOf(date));
        ps.setDouble(5, height);
        ps.setInt(6, weight);
        ps.setInt(7, color.ordinal() + 1);
        ps.setLong(8, location.getX());
        ps.setLong(9, location.getY());
        ps.setFloat(10, location.getZ());
        ps.setInt(11, id);
        return ps.executeUpdate() > 0;
    }
    private Person resultSetToStudyGroup(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Long x = resultSet.getLong("coordinate_x");
        Long y = resultSet.getLong("coordinate_y");
        Coordinates coordinates = new Coordinates( x, y);
        Color color = Color.values()[resultSet.getInt("color") - 1];
        Integer weight = resultSet.getInt("weight");
        double height = resultSet.getInt("height");
        Long locX = resultSet.getLong("loc_x");
        Long locY = resultSet.getLong("loc_y");
        Float locZ = resultSet.getFloat("loc_z");
        Location location = new Location(locX, locY, locZ);
        String ownerLogin = resultSet.getString("owner_login");
        return new Person(
                name,
                coordinates,
                height,
                weight,
                color,
                location,
                ownerLogin);
    }
    @Override
    public boolean removeById(Integer id, User user) throws SQLException {
        String login = user.getLogin();
        if (isStudyGroupOwner(login, id)) {
            PreparedStatement ps = this.connection.prepareStatement("DELETE FROM collection WHERE id = ?");
            ps.setLong(1, id);

            int count = ps.executeUpdate();
            return count == 1;
        }
        return false;
    }

    private boolean isStudyGroupOwner(String login, Integer id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("SELECT owner_login FROM collection WHERE id = ?");
        ps.setLong(1, id);
        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            String realOwner = resultSet.getString("owner_login");
            return realOwner.equals(login);
        }
        return false;
    }

    @Override
    public HashSet<Person> getAllStudyGroups() throws SQLException {
        HashSet<Person> result = new HashSet<>();
        String statement = "select * FROM collection";
        PreparedStatement ps = this.connection.prepareStatement(statement);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            Person person = resultSetToStudyGroup(resultSet);
            result.add(person);
        }
        return result;
    }



    @Override
    public int clearCollectionForUser(User user) throws SQLException {
        System.out.println("clearCollectionForUser");
        String login = user.getLogin();
        int quantity = 0;
        //PreparedStatement ps1 = this.connection.prepareStatement("SELECT COUNT(*) FROM collection WHERE owner_login = ?");
        PreparedStatement ps2 = this.connection.prepareStatement("DELETE FROM collection WHERE owner_login = ?");
        ps2.setString(1, login);
        quantity = ps2.executeUpdate();
        if (quantity > 0) {
            return quantity;
        } else {
            return 0;
        }
    }

    @Override
    public void setTables() throws SQLException {
//        Statement statement = this.connection.createStatement();
//        String sequence = "CREATE SEQUENCE IF NOT EXISTS s408228.collection_id_seq\n" +
//                "    INCREMENT 1\n" +
//                "    START 1\n" +
//                "    MINVALUE 1\n" +
//                "    MAXVALUE 9223372036854775807\n" +
//                "    CACHE 1;\n";
//        int rowsAffectedS = statement.executeUpdate(sequence);
//        if (rowsAffectedS > 0) {
//            System.out.println("Таблица с зависимостью успешно создана.");
//        } else {
//            System.out.println("Таблица с зависимостью уже существует.");
//        }
//        String createTableSQL = "CREATE TABLE IF NOT EXISTS s408228.collection\n" +
//                "(\n" +
//                "    id bigint NOT NULL DEFAULT nextval('collection_id_seq'::regclass),\n" +
//                "    name character varying COLLATE pg_catalog.\"default\",\n" +
//                "    coordinate_x double precision,\n" +
//                "    coordinate_y bigint,\n" +
//                "    date date,\n" +
//                "    student_count bigint,\n" +
//                "    expelled_students bigint,\n" +
//                "    should_be_expelled bigint,\n" +
//                "    form_of_education smallint,\n" +
//                "    name_admin character varying COLLATE pg_catalog.\"default\",\n" +
//                "    weight double precision,\n" +
//                "    eye_color smallint,\n" +
//                "    hair_color smallint,\n" +
//                "    nationality smallint,\n" +
//                "    owner_login character varying COLLATE pg_catalog.\"default\",\n" +
//                "    CONSTRAINT collection_pkey PRIMARY KEY (id)\n" +
//                ")\n" +
//                "\n" +
//                "TABLESPACE pg_default;\n" +
//                "\n" +
//                "ALTER TABLE IF EXISTS s408228.collection\n" +
//                "    OWNER to s408228;";
//        int rowsAffected = statement.executeUpdate(createTableSQL);
//        if (rowsAffected > 0) {
//            System.out.println("Таблица с коллекцией успешно создана.");
//        } else {
//            System.out.println("Таблица с коллекцией уже существует.");
//        }
//        String createUsersTable = "CREATE TABLE IF NOT EXISTS s408228.users\n" +
//                "(\n" +
//                "    login character varying COLLATE pg_catalog.\"default\" NOT NULL,\n" +
//                "    password character varying COLLATE pg_catalog.\"default\",\n" +
//                "    CONSTRAINT users_pkey PRIMARY KEY (login)\n" +
//                ")\n" +
//                "\n" +
//                "TABLESPACE pg_default;\n" +
//                "\n" +
//                "ALTER TABLE IF EXISTS s408228.users\n" +
//                "    OWNER to s408228;";
//        int rowsAffectedU = statement.executeUpdate(createUsersTable);
//        if (rowsAffectedU > 0) {
//            System.out.println("Таблица с пользователяими успешно создана.");
//        } else {
//            System.out.println("Таблица с пользователями уже существует.");
//        }
//        String part2 = "ALTER SEQUENCE s408228.collection_id_seq\n" +
//                "    OWNED BY 408228.collection.id;\n" +
//                "\n" +
//                "ALTER SEQUENCE s408228c.collection_id_seq\n" +
//                "    OWNER TO s408228;";
//        int rowsAffectedS2 = statement.executeUpdate(part2);
    }
}
